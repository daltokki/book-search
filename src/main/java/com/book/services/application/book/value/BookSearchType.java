package com.book.services.application.book.value;

import lombok.Getter;

@Getter
public enum BookSearchType {
	TITLE("제목"),
	ISBN("ISBN"),
	KEYWORD("주제어"),
	CONTENTS("목차"),
	OVERVIEW("책소개"),
	PUBLISHER("출판사"),
	ALL("전체");

	private String desc;

	BookSearchType(String desc) {
		this.desc = desc;
	}
}
