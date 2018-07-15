package com.book.services.util;

import com.book.services.util.exception.RequestObjectBuilderException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.book.interfaces.api.exception.ApiException;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

@Component
public class ApiClientUtils {
	private final RestTemplate restTemplate;

	@Autowired
	public ApiClientUtils(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private HttpEntity<String> getHttpEntity(String authKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authKey);
		return new HttpEntity<>(headers);
	}

	public <T> T sendGet(URI uri, String authKey, Class<T> responseCls) {
		ResponseEntity<T> exchange = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(authKey), responseCls);
		return exchange.getBody();
	}

	public <T> T sendGet(String baseUrl, int currentPage, int rowsPerPage, Class<T> responseCls) {
		Map<String, Object> params = new HashMap<>();
		params.put("page", currentPage);
		params.put("size", rowsPerPage);
		return restTemplate.getForObject(baseUrl, responseCls, params);
	}

	public <T> T sendPost(String reqUrl, int insertId, String content, Class<T> responseCls) {
		String body;
		HttpHeaders headers;
		HttpEntity entity;

		Map<String, Object> params = new HashMap<>();
		params.put("insertId", insertId);
		params.put("content", content);

		try {
			body = new ObjectMapper().writeValueAsString(params);
			headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			entity = new HttpEntity<>(body, headers);

			return restTemplate.postForObject(reqUrl, entity, responseCls, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(e.getMessage());
		}
	}

	public static String toUrl(Object object) {
		StringJoiner joiner = new StringJoiner("&");
		try {
			final Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);

				Optional<Object> optional = Optional.ofNullable(field.get(object));
				optional.ifPresent(
					val -> {
						String fieldName = field.getName();
						String param = fieldName + "={" + fieldName + "}";
						joiner.add(param);
					});
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RequestObjectBuilderException("toUrl error.");
		}
		return joiner.toString();
	}

	public static Map<String, Object> buildValueMap(Object object) {
		HashMap<String, Object> valueMap = Maps.newHashMap();
		try {
			final Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);

				Optional<Object> optional = Optional.ofNullable(field.get(object));
				optional.ifPresent(
					val -> {
						String fieldName = field.getName();
						valueMap.put(fieldName, val);
					});
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RequestObjectBuilderException("buildValueMap error.");
		}
		return valueMap;
	}
}
