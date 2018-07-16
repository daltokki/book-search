package com.book.interfaces.api;

import com.book.interfaces.api.model.ApiResultDTO;
import com.book.interfaces.api.model.BookSearchResultDTO;
import com.book.services.application.book.model.BookSearchDocumentsDto;
import com.book.services.application.book.model.BookSearchResultDto;
import com.book.services.util.ApiClientUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookSearchApiWrapper {
	@Autowired
	private ApiClientUtils apiClient;

	@Value("${book.api.server}")
	private String apiServer;

	@Value("${book.api.auth.key}")
	private String apiAuthKey;

	public ApiResultDTO getSearchResult(Object requestData, Pageable pageable) {
		try {
			String url = apiServer + "?" + ApiClientUtils.toUrl(requestData);
			Map<String, Object> buildValueMap = ApiClientUtils.buildValueMap(requestData);

			URI uri = UriComponentsBuilder.fromHttpUrl(url).buildAndExpand(buildValueMap).encode().toUri();
			BookSearchResultDTO resultDTO = apiClient.sendGet(uri, apiAuthKey, BookSearchResultDTO.class);

			Integer totalCount = (Integer) resultDTO.getMeta().get("total_count");
			Integer totalPages = Double.valueOf(Math.ceil(Double.parseDouble(totalCount.toString()) / pageable.getPageSize())).intValue();
			List<BookSearchDocumentsDto> result = resultDTO.getDocuments().stream().map(BookSearchDocumentsDto::new).collect(Collectors.toList());

			BookSearchResultDto build = BookSearchResultDto.builder().totalCount(totalCount).totalPages(totalPages).result(result).build();
			return ApiResultDTO.succeed(build);
		} catch(Exception e) {
			log.error("BookSearchApiWrapper getSearchResult Error. cause:{}, {}", e.getMessage(), e);
			return ApiResultDTO.fail("서적 검색에 실패하였습니다.");
		}
	}
}
