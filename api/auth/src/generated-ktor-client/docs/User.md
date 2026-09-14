
# User

## Properties
| Name | Type | Description | Notes |
| ------------ | ------------- | ------------- | ------------- |
| **id** | **kotlin.Long** |  |  |
| **displayName** | **kotlin.String** |  |  |
| **avatarUrl** | **kotlin.String** |  |  |
| **birthdate** | [**kotlinx.datetime.LocalDate**](kotlinx.datetime.LocalDate.md) | String date in YYYY-MM-DD or RFC3339, stored as a date field. |  |
| **gender** | [**inline**](#Gender) | Nullable gender value. When unset, the API returns null. |  |
| **status** | [**inline**](#Status) |  |  |
| **createdAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **updatedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |
| **deletedAt** | [**kotlin.time.Instant**](kotlin.time.Instant.md) |  |  |


<a id="Gender"></a>
## Enum: gender
| Name | Value |
| ---- | ----- |
| gender | male, female, other |


<a id="Status"></a>
## Enum: status
| Name | Value |
| ---- | ----- |
| status | active, inactive, suspended, deleted |



