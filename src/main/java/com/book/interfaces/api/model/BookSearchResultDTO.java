package com.book.interfaces.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BookSearchResultDTO {
	private Map<String, Object> meta;
	private List<BookSearchDocumentsDTO> documents;
}