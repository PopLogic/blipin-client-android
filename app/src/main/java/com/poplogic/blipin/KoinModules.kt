package com.poplogic.blipin

import CryptoManager
import CryptoManagerImpl
import RateLimiterImpl
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.domerrors.NetworkError
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.poplogic.blipin.api.auth.AuthRemoteDataSource
import com.poplogic.blipin.api.auth.apis.AuthApi
import com.poplogic.blipin.api.common.exception.NetworkExceptionHandler
import com.poplogic.blipin.api.token.TokenRemoteDataSource
import com.poplogic.blipin.api.token.apis.TokenApi
import com.poplogic.blipin.api.user.UserRemoteDataSource
import com.poplogic.blipin.api.user.apis.UserApi
import com.poplogic.blipin.common.base.environment.Environment
import com.poplogic.blipin.common.base.ratelimit.RateLimiter
import com.poplogic.blipin.data.auth.AuthRepositoryImpl
import com.poplogic.blipin.data.token.TokenRepositoryImpl
import com.poplogic.blipin.data.token.data_source.TokenMapper
import com.poplogic.blipin.data.token.data_source.local.LocalTokenDataSource
import com.poplogic.blipin.data.token.data_source.local.TokenPrefsSerializer
import com.poplogic.blipin.data.user.UserMapper
import com.poplogic.blipin.data.user.UserRepositoryImpl
import com.poplogic.blipin.domain.auth.AuthManager
import com.poplogic.blipin.domain.auth.AuthManagerImpl
import com.poplogic.blipin.domain.auth.repository.AuthRepository
import com.poplogic.blipin.domain.auth.repository.TokenRepository
import com.poplogic.blipin.domain.auth.usecase.AppleSignInUseCase
import com.poplogic.blipin.domain.auth.usecase.AppleSignInUseCaseImpl
import com.poplogic.blipin.domain.auth.usecase.EmailSignInUseCase
import com.poplogic.blipin.domain.auth.usecase.EmailSignInUseCaseImpl
import com.poplogic.blipin.domain.auth.usecase.EmailVerificationResendRateLimitStatusUseCase
import com.poplogic.blipin.domain.auth.usecase.EmailVerificationResendRateLimitStatusUseCaseImpl
import com.poplogic.blipin.domain.auth.usecase.EmailVerificationUseCase
import com.poplogic.blipin.domain.auth.usecase.EmailVerificationUseCaseImpl
import com.poplogic.blipin.domain.auth.usecase.GoogleSignInUseCase
import com.poplogic.blipin.domain.auth.usecase.GoogleSignInUseCaseImpl
import com.poplogic.blipin.domain.auth.usecase.LogOutUseCase
import com.poplogic.blipin.domain.auth.usecase.LogOutUseCaseImpl
import com.poplogic.blipin.domain.auth.usecase.UserAuthenticationStateUseCase
import com.poplogic.blipin.domain.auth.usecase.UserAuthenticationStateUseCaseImpl
import com.poplogic.blipin.domain.connectivity.ConnectivityFlowBasedUseCase
import com.poplogic.blipin.domain.connectivity.ConnectivityFlowBasedUseCaseImpl
import com.poplogic.blipin.domain.location.location_service_status.CanLocationServiceBeenUsedUseCase
import com.poplogic.blipin.domain.location.location_service_status.CanLocationServiceBeenUsedUseCaseImpl
import com.poplogic.blipin.domain.location.location_updates.GetFineLocationUpdatesFlowBasedUseCaseImpl
import com.poplogic.blipin.domain.location.location_updates.GetLocationUpdatesFlowBasedUseCase
import com.poplogic.blipin.domain.location.location_updates.GetLocationUpdatesFlowBasedUseCaseProvider
import com.poplogic.blipin.domain.location.location_updates.GetLocationUpdatesFlowBasedUseCaseProviderImpl
import com.poplogic.blipin.domain.user.repository.UserRepository
import com.poplogic.blipin.domain.user.usecase.GetUserProfileUseCase
import com.poplogic.blipin.domain.user.usecase.GetUserProfileUseCaseImpl
import com.poplogic.blipin.domain.user.usecase.UpdateUserProfileUseCase
import com.poplogic.blipin.domain.user.usecase.UpdateUserProfileUseCaseImpl
import com.poplogic.blipin.presentation.authenticate.presentation.AuthenticationViewModel
import com.poplogic.blipin.presentation.authenticate.presentation.emailverification.EmailVerificationViewModel
import com.poplogic.blipin.presentation.authenticate.presentation.updateprofile.UpdateUserProfileViewModel
import com.poplogic.blipin.presentation.explore.presentation.ExploreViewModel
import com.poplogic.blipin.presentation.favorites.presentation.FavoritesViewModel
import com.poplogic.blipin.presentation.map.presentation.MapViewModel
import com.poplogic.blipin.presentation.onboard.presentation.viewmodel.OnboardViewModel
import com.poplogic.blipin.presentation.profile.presentation.ProfileViewModel
import com.poplogic.blipin.presentation.profile.presentation.account.AccountScreenViewModel
import com.poplogic.blipin.presentation.profile.presentation.comment.CommentScreenViewModel
import com.poplogic.blipin.presentation.profile.presentation.developer.DeveloperScreenViewModel
import com.poplogic.blipin.presentation.profile.presentation.notification.NotificationScreenViewModel
import com.poplogic.blipin.presentation.profile.presentation.place.PlaceScreenViewModel
import com.poplogic.blipin.presentation.profile.presentation.support.SupportScreenViewModel
import com.poplogic.blipin.presentation.profile.presentation.user.ProfileEditViewModel
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val OPEN_API_CONFIG = "openApiConfig"

private val coreModule =
    module {
        single<HttpClientEngine> { CIO.create() }
        single<(HttpClientConfig<*>) -> Unit>(named(OPEN_API_CONFIG)) {
            { config ->
                config.install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                            encodeDefaults = true
                        },
                    )
                }
                config.install(Auth) {
                    bearer {
                        loadTokens {
                            // Koin's get<T>() works here perfectly
                            val authManager = get<AuthManager>()
                            val tokenEntity = authManager.getTokenEntity()
                            tokenEntity?.let {
                                BearerTokens(tokenEntity.accessToken, tokenEntity.refreshToken)
                            }
                        }

                        refreshTokens {
                            val authManager = get<AuthManager>()
                            val tokenEntity = authManager.refresh()
                            BearerTokens(tokenEntity.accessToken, tokenEntity.refreshToken)
                        }
                    }
                }
                config.install(HttpTimeout) {
                    requestTimeoutMillis = 10000
                    connectTimeoutMillis = 5000
                    socketTimeoutMillis = 15000
                }
                config.install(HttpRequestRetry) {
                    maxRetries = 3
                    retryIf { _, response -> !response.status.isSuccess() && response.status.value != 401 }
                    retryOnExceptionIf { _, cause -> cause is NetworkError }
                    delayMillis { retry -> retry * 3000L }
                    modifyRequest { it.headers.append("X_RETRY_COUNT", retryCount.toString()) }
                }
                config.install(Logging) {
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                Log.d("KtorHttpClient", message)
                            }
                        }
                    // 設定日誌級別：
                    // ALL -> 記錄所有請求/回應的 Headers 與 Body (除錯最實用)
                    // INFO -> 僅記錄請求方法、URL 與狀態碼
                    // HEADERS / BODY -> 僅紀錄特定區塊
                    level = LogLevel.ALL

                    // 選填：如果你的 Log 訊息太長被部分 Android Logcat 截斷，可以啟用這個過濾
//                    sanitizeHeader { header -> header == HttpHeaders.Authorization } // 隱藏敏感 Token
                }
            }
        }
        single<HttpClient> {
            HttpClient(get()) {
                // Apply the exact same config block registered above
                get<(HttpClientConfig<*>) -> Unit>(named(OPEN_API_CONFIG)).invoke(this)
            }
        }

        single<SnackbarHostState> { SnackbarHostState() }
        single<Environment> { Environment.fromBuildConfig() }
        single<ConnectivityManager> {
            androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
        single<FusedLocationProviderClient> {
            LocationServices.getFusedLocationProviderClient(androidContext())
        }
        single<CryptoManager> {
            CryptoManagerImpl()
        }
        single<AuthManager> {
            AuthManagerImpl(get(), get())
        }
        single<TokenPrefsSerializer> {
            TokenPrefsSerializer(get())
        }
        single<CredentialManager> {
            CredentialManager.create(androidContext())
        }
        factory<RateLimiter> {
            RateLimiterImpl()
        }
    }

private val dataModule =
    module {
        single<TokenMapper> { TokenMapper() }
        single<NetworkExceptionHandler> { NetworkExceptionHandler() }
        single<LocalTokenDataSource> { LocalTokenDataSource(androidContext()) }
        single<TokenRemoteDataSource> { TokenRemoteDataSource(get(), get()) }
        single<AuthRemoteDataSource> { AuthRemoteDataSource(get(), get()) }
        single<TokenRepository> { TokenRepositoryImpl(androidContext(), get(), get(), get()) }
        single<UserRepository> { UserRepositoryImpl(get(), get()) }
        single<UserMapper> { UserMapper() }
        single<UserRemoteDataSource> { UserRemoteDataSource(get(), get()) }
        single<AuthApi> {
            AuthApi(
                get<Environment>().backendHost,
                httpClientEngine = get(),
                httpClientConfig = get(named(OPEN_API_CONFIG)),
            )
        }
        single<TokenApi> {
            TokenApi(
                get<Environment>().backendHost,
                httpClientEngine = get(),
                httpClientConfig = get(named(OPEN_API_CONFIG)),
            )
        }
        single<UserApi> {
            UserApi(
                get<Environment>().backendHost,
                httpClientEngine = get(),
                httpClientConfig = get(named(OPEN_API_CONFIG)),
            )
        }
    }

private val domainModule =
    module {
        single<AuthRepository> {
            AuthRepositoryImpl(
                get(),
            )
        }
    }

private val useCaseModule =
    module {
        factory<ConnectivityFlowBasedUseCase> { ConnectivityFlowBasedUseCaseImpl(get()) }
        factory<CanLocationServiceBeenUsedUseCase> {
            CanLocationServiceBeenUsedUseCaseImpl(
                get(),
            )
        }
        factory<GetLocationUpdatesFlowBasedUseCase> {
            GetFineLocationUpdatesFlowBasedUseCaseImpl(
                get(),
                get(),
            )
        }
        factory<GoogleSignInUseCase> {
            GoogleSignInUseCaseImpl(
                get(),
            )
        }
        factory<LogOutUseCase> {
            LogOutUseCaseImpl(
                get(),
            )
        }
        factory<AppleSignInUseCase> {
            AppleSignInUseCaseImpl(
                get(),
            )
        }
        factory<GetUserProfileUseCase> {
            GetUserProfileUseCaseImpl(
                get(),
            )
        }
        factory<UserAuthenticationStateUseCase> {
            UserAuthenticationStateUseCaseImpl(
                get(),
            )
        }
        factory<GetLocationUpdatesFlowBasedUseCaseProvider> {
            GetLocationUpdatesFlowBasedUseCaseProviderImpl(
                get(),
                get(),
            )
        }

        factory<EmailSignInUseCase> {
            EmailSignInUseCaseImpl(
                get(),
            )
        }

        factory<UpdateUserProfileUseCase> {
            UpdateUserProfileUseCaseImpl(
                get(),
            )
        }

        factory<EmailVerificationUseCase> {
            EmailVerificationUseCaseImpl(
                get(),
            )
        }
        factory<EmailVerificationResendRateLimitStatusUseCase> {
            EmailVerificationResendRateLimitStatusUseCaseImpl(
                get(),
            )
        }
    }

private val viewModelModule =
    module {
        viewModel { FavoritesViewModel(get()) }
        viewModel { ProfileViewModel(get(), get(), get()) }
        viewModel { OnboardViewModel() }
        viewModel { MapViewModel(get(), get()) }
        viewModel { ExploreViewModel(get(), get(), get()) }
        viewModel { AuthenticationViewModel(get(), get(), get(), get(), get()) }
        viewModel { params ->
            UpdateUserProfileViewModel(params.get(), get())
        }
        viewModel { params ->
            ProfileEditViewModel(params.get())
        }
        viewModel { params ->
            EmailVerificationViewModel(get(), get(), get(), params.get())
        }
        viewModel { AccountScreenViewModel() }
        viewModel { CommentScreenViewModel() }
        viewModel { PlaceScreenViewModel() }
        viewModel { NotificationScreenViewModel() }
        viewModel { DeveloperScreenViewModel() }
        viewModel { SupportScreenViewModel() }
    }

val appModules = listOf(coreModule, useCaseModule, viewModelModule, dataModule, domainModule)
