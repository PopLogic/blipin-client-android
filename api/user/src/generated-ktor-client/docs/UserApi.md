# UserApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**userGet**](UserApi.md#userGet) | **GET** /user | Get current user profile |
| [**userPatch**](UserApi.md#userPatch) | **PATCH** /user | Update current user profile |


<a id="userGet"></a>
# **userGet**
> GetUserProfileResponse userGet()

Get current user profile

Fetches the authenticated user&#39;s profile based on the JWT payload.

### Example
```kotlin
// Import classes:
//import com.poplogic.blipin.api.user.infrastructure.*
//import com.poplogic.blipin.api.user.models.*

val apiInstance = UserApi()
try {
    val result : GetUserProfileResponse = apiInstance.userGet()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userGet")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GetUserProfileResponse**](GetUserProfileResponse.md)

### Authorization


Configure BearerAuth statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="userPatch"></a>
# **userPatch**
> UpdateUserProfileResponse userPatch(updateUserProfileRequest)

Update current user profile

Applies a JSON Merge Patch to the authenticated user&#39;s profile. Include only fields you want to change. Set a field to null to clear it. Supported fields are display_name, birthdate, and gender. Birthdate accepts YYYY-MM-DD or RFC3339 and gender must be male, female, or other. 

### Example
```kotlin
// Import classes:
//import com.poplogic.blipin.api.user.infrastructure.*
//import com.poplogic.blipin.api.user.models.*

val apiInstance = UserApi()
val updateUserProfileRequest : UpdateUserProfileRequest =  // UpdateUserProfileRequest | 
try {
    val result : UpdateUserProfileResponse = apiInstance.userPatch(updateUserProfileRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#userPatch")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#userPatch")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **updateUserProfileRequest** | [**UpdateUserProfileRequest**](UpdateUserProfileRequest.md)|  | |

### Return type

[**UpdateUserProfileResponse**](UpdateUserProfileResponse.md)

### Authorization


Configure BearerAuth statically:
```kotlin
ApiClient.accessToken = ""
```

### HTTP request headers

 - **Content-Type**: application/merge-patch+json
 - **Accept**: application/json

