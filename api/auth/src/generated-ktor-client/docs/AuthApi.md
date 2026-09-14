# AuthApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**authApplePost**](AuthApi.md#authApplePost) | **POST** /auth/apple | Apple login |
| [**authEmailLoginPost**](AuthApi.md#authEmailLoginPost) | **POST** /auth/email/login | Login with email and return whether the user is new |
| [**authEmailVerifyPost**](AuthApi.md#authEmailVerifyPost) | **POST** /auth/email/verify | Verify email OTP and activate account |
| [**authGooglePost**](AuthApi.md#authGooglePost) | **POST** /auth/google | Google OAuth login |


<a id="authApplePost"></a>
# **authApplePost**
> AuthApplePost200Response authApplePost()

Apple login

This endpoint is currently a stub and returns a success message without real Apple authentication. It is intentionally included here as a draft/TODO endpoint to match the current implementation. 

### Example
```kotlin
// Import classes:
//import com.poplogic.blipin.api.auth.infrastructure.*
//import com.poplogic.blipin.api.auth.models.*

val apiInstance = AuthApi()
try {
    val result : AuthApplePost200Response = apiInstance.authApplePost()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#authApplePost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#authApplePost")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AuthApplePost200Response**](AuthApplePost200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="authEmailLoginPost"></a>
# **authEmailLoginPost**
> EmailLoginResponse authEmailLoginPost(emailLoginRequest)

Login with email and return whether the user is new

### Example
```kotlin
// Import classes:
//import com.poplogic.blipin.api.auth.infrastructure.*
//import com.poplogic.blipin.api.auth.models.*

val apiInstance = AuthApi()
val emailLoginRequest : EmailLoginRequest =  // EmailLoginRequest | 
try {
    val result : EmailLoginResponse = apiInstance.authEmailLoginPost(emailLoginRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#authEmailLoginPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#authEmailLoginPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **emailLoginRequest** | [**EmailLoginRequest**](EmailLoginRequest.md)|  | |

### Return type

[**EmailLoginResponse**](EmailLoginResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="authEmailVerifyPost"></a>
# **authEmailVerifyPost**
> VerifyEmailResponse authEmailVerifyPost(verifyEmailRequest)

Verify email OTP and activate account

### Example
```kotlin
// Import classes:
//import com.poplogic.blipin.api.auth.infrastructure.*
//import com.poplogic.blipin.api.auth.models.*

val apiInstance = AuthApi()
val verifyEmailRequest : VerifyEmailRequest =  // VerifyEmailRequest | 
try {
    val result : VerifyEmailResponse = apiInstance.authEmailVerifyPost(verifyEmailRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#authEmailVerifyPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#authEmailVerifyPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **verifyEmailRequest** | [**VerifyEmailRequest**](VerifyEmailRequest.md)|  | |

### Return type

[**VerifyEmailResponse**](VerifyEmailResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="authGooglePost"></a>
# **authGooglePost**
> GoogleLoginResponse authGooglePost(googleLoginRequest)

Google OAuth login

Validates a Google ID token and creates or reuses the user identity.

### Example
```kotlin
// Import classes:
//import com.poplogic.blipin.api.auth.infrastructure.*
//import com.poplogic.blipin.api.auth.models.*

val apiInstance = AuthApi()
val googleLoginRequest : GoogleLoginRequest =  // GoogleLoginRequest | 
try {
    val result : GoogleLoginResponse = apiInstance.authGooglePost(googleLoginRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthApi#authGooglePost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthApi#authGooglePost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **googleLoginRequest** | [**GoogleLoginRequest**](GoogleLoginRequest.md)|  | |

### Return type

[**GoogleLoginResponse**](GoogleLoginResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

