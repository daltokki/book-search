package com.book.services.application.book.value;

import lombok.Getter;

@Getter
public enum BookSortType {
	ACCURACY("정확도순"), RECENCY("최신순"), SALES("판매량순");

	BookSortType(String desc) {
		this.desc = desc;
	}

	private String desc;
}
