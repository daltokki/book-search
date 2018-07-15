package com.book.interfaces.book.model;

import com.book.services.application.book.value.DateSearchType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateAndPageSearchForm {
	private int page = 1;
	private int size = 10;
	private DateSearchType dateSearchType = DateSearchType.WEEK;
}
