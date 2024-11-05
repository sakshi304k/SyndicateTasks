package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
    lambdaName = "hello_world",
	roleName = "hello_world-role",
	isPublishVersion = true,
	aliasName = "${lambdas_alias_name}",
	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
public class HelloWorld implements RequestHandler<Object, Map<String, Object>> {

	public Map<String, Object> handleRequest(Object request, Context context) {

			// Cast the request to Map<String, Object>
			Map<String, Object> requestMap = (Map<String, Object>) request;

			// Extract path and method from the request map
			String path = (String) requestMap.getOrDefault("rawPath", "/");
			String method = (String) requestMap.getOrDefault("httpMethod", "GET");

			// Create a result map to hold the response
			Map<String, Object> resultMap = new HashMap<>();

			if ("/hello".equals(path) && "GET".equalsIgnoreCase(method)) {
				// If the path is /hello and the method is GET, return a 200 response
				resultMap.put("statusCode", 200);
				resultMap.put("message", "Hello from Lambda");
			} else {
				// For any other path or method, return a 400 error response
				resultMap.put("statusCode", 400);
				resultMap.put("message", String.format(
						"Bad request syntax or unsupported method. Request path: %s. HTTP method: %s", path, method
				));
			}
		return resultMap;
	}

}
