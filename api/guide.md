1. Place the OpenAPI specification file (e.g., `openapi.yaml`) in a directory of your choice.
2. cd into the directory where your OpenAPI specification file (e.g., `openapi.yaml`) is located.
3. openapi-generator generate -i ./openapi/userService.yaml -g kotlin --library jvm-ktor -o
   ./src/generated-ktor-client --package
   -name com.poplogic.blipin.api.user -c openapi/config.yaml  