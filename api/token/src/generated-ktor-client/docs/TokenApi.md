# TokenApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**tokenRenewAccessTokenPost**](TokenApi.md#tokenRenewAccessTokenPost) | **POST** /token/renew_access_token | Renew access token using refresh token |
| [**tokenRevokePost**](TokenApi.md#tokenRevokePost) | **POST** /token/revoke | Revoke the current session token |


<a id="tokenRenewAccessTokenPost"></a>
# **tokenRenewAccessTokenPost**
> RenewAccessTokenResponse tokenRenewAccessTokenPost(renewAccessTokenRequest)

Renew access token using refresh token

Verifies a refresh token, validates the persisted session, and rotates the refresh token before issuing a new access token. 

### Example
```kotlin
// Import classes:
//import com.poplogic.blipin.api.token.infrastructure.*
//import com.poplogic.blipin.api.token.models.*

val apiInstance = TokenApi()
val renewAccessTokenRequest : RenewAccessTokenRequest =  // RenewAccessTokenRequest | 
try {
    val result : RenewAccessTokenResponse = apiInstance.tokenRenewAccessTokenPost(renewAccessTokenRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TokenApi#tokenRenewAccessTokenPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TokenApi#tokenRenewAccessTokenPost")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **renewAccessTokenRequest** | [**RenewAccessTokenRequest**](RenewAccessTokenRequest.md)|  | |

### Return type

[**RenewAccessTokenResponse**](RenewAccessTokenResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="tokenRevokePost"></a>
# **tokenRevokePost**
> RevokeTokenResponse tokenRevokePost()

Revoke the current session token

Deletes the session associated with the authenticated token payload to invalidate the refresh-token session. 

### Example
```kotlin
// Import classes:
//import com.poplogic.blipin.api.token.infrastructure.*
//import com.poplogic.blipin.api.token.models.*

val apiInstance = TokenApi()
try {
    val result : RevokeTokenResponse = apiInstance.tokenRevokePost()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TokenApi#tokenRevokePost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TokenApi#tokenRevokePost")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**RevokeTokenResponse**](RevokeTokenResponse.md)

### Authorization


Configure bearerAuth statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

