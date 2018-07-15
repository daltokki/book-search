package com.book.services.application.book.value;

import lombok.Getter;

@Getter
public enum BookSortType {
	ACCURACY("ACCURACY", "정확도순"), RECENCY("RECENCY", "최신순"), SALES("SALES", "판매량순");

	BookSortType(String text, String desc) {
		this.text = text;
		this.desc = desc;
	}

	private String text;
	private String desc;
}
