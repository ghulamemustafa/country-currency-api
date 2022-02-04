package com.gm.kitalulus.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class IntegrationClientHttpInterceptor implements ClientHttpRequestInterceptor {
	
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)	throws IOException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		logRequest(username, request, body);
		ClientHttpResponse response = execution.execute(request, body);
		response = logResponse(username, response);
		return response;
	}

	private void logRequest(String username, HttpRequest request, byte[] body) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("\n============================request begin===========================================")
				.append("\nUsername    : { " + username + "}")
				.append("\nURI         : { " + request.getURI() + "}")
				.append("\nMethod      : { " + request.getMethod() + "}")
				.append("\nHeaders     : { " + request.getHeaders() + "}")
				.append("\nBody        : [ " + new String(body, "UTF-8") + "]")
				.append("\n============================request end=============================================");
		log.info(stringBuilder.toString());
	}

	private ClientHttpResponse logResponse(String username, ClientHttpResponse response) throws IOException {
		final ClientHttpResponse responseCopy = new BufferingClientHttpResponseWrapper(response);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("\n============================response begin==========================================")
				.append("\nUsername     : { " + username + "}")
				.append("\nStatus code  : { " + responseCopy.getStatusCode() + "}")
				.append("\nStatus text  : { " + responseCopy.getStatusText() + "}")
				.append("\nHeaders      : { " + responseCopy.getHeaders() + "}")
				.append("\nBody         : [ " + IOUtils.toString(responseCopy.getBody(), StandardCharsets.UTF_8) + "]")
				.append("\n============================response end=============================================");
		log.info(stringBuilder.toString());
		return responseCopy;
	}
}