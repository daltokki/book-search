package com.book.services.application.book;

import com.book.interfaces.api.BookSearchApiWrapper;
import com.book.interfaces.book.model.BookSearchForm;
import com.book.services.application.book.model.BookSearchResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookSearchService {
	@Autowired
	private BookSearchApiWrapper bookSearchApiWrapper;
	@Autowired
	private BookSearchHistoryService bookSearchHistoryService;

	public BookSearchResultDto search(BookSearchForm bookSearchForm) {
		Long saveHistoryId = bookSearchHistoryService.saveHistory(bookSearchForm);
		log.info("Save save search history. id={}", saveHistoryId);

		PageRequest pageRequest = PageRequest.of(bookSearchForm.getPage(), bookSearchForm.getSize());
		return bookSearchApiWrapper.getSearchResult(bookSearchForm, pageRequest);
	}
}
