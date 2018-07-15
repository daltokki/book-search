package com.book.services.application.book.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookSearchResultDto {
	private int totalCount;
	private int totalPages;
	private List<BookSearchDocumentsDto> result;
}
