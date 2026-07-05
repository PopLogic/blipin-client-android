package com.poplogic.blipin.usecase.connectivity

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory

@Factory(binds = [ConnectivityUseCase::class])
class ConnectivityUseCaseImpl(
    private val connectivityManager: ConnectivityManager,
) : ConnectivityUseCase {
    override fun invoke(param: Unit): Flow<ConnectivityState> =
        callbackFlow {
            val networkCallback =
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        trySend(ConnectivityState.CONNECTED)
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        trySend(ConnectivityState.DISCONNECTED)
                    }
                }

            fun initialState() {
                trySend(
                    if (connectivityManager.activeNetwork != null) {
                        ConnectivityState.CONNECTED
                    } else {
                        ConnectivityState.DISCONNECTED
                    },
                )
            }

            launch {
                connectivityManager.registerDefaultNetworkCallback(networkCallback)
                initialState()
            }

            awaitClose {
                connectivityManager.unregisterNetworkCallback(networkCallback)
            }
        }
}
