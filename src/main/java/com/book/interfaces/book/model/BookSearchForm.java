package com.book.interfaces.book.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchForm {
	private int page = 1;
	private int size = 10;
	private String query;
	private String target;
	private String sort;
	private Integer category;
}
