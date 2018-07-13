package com.book.services.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.book.interfaces.api.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

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

	/**
	 * Simple RestTemplate Example(Get)
	 *
	 * @param baseUrl      도메인
	 * @param currentPage  요청 페이지
	 * @param rowsPerPage  게시물 수
	 */
	public <T> T sendGet(String baseUrl, int currentPage, int rowsPerPage, Class<T> responseCls) {
		Map<String, Object> params = new HashMap<>();
		params.put("page", currentPage);
		params.put("size", rowsPerPage);
		return restTemplate.getForObject(baseUrl, responseCls, params);
	}

	public <T> T sendGet(URI uri, String authKey, Class<T> responseCls) {
		ResponseEntity<T> exchange = restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(authKey), responseCls);
		return exchange.getBody();
	}

	/**
	 * Simple RestTemplate Example(Post) + Headers + Body
	 *
	 * @param reqUrl   요청 URL(POST)
	 * @param insertId 입력값(ID)
	 * @param content  입력값(내용)
	 */
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
}
