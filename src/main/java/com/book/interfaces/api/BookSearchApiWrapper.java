package com.book.interfaces.api;

import com.book.services.util.ApiClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class BookSearchApiWrapper {
	private final static String BOOK_SEARCH_URL = "https://dapi.kakao.com/v2/search/book";
	@Autowired
	private ApiClientUtils apiClient;

	@Value("${kakao.api.auth.key}")
	private String apiAuthKey;

	@GetMapping("/test")
	public String getApiTest() {
		String url = BOOK_SEARCH_URL + "?target=title&query={query}";
		URI uri = UriComponentsBuilder.fromHttpUrl(url).buildAndExpand("미움").encode().toUri();
		return apiClient.sendGet(uri, apiAuthKey, String.class);
	}
}
