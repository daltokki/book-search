package com.book.services.application.book.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@AllArgsConstructor
public enum DateSearchType {
	M_SIX("6개월", Date.from(LocalDateTime.now().minusMonths(6).atZone(ZoneId.systemDefault()).toInstant())),
	M_THREE("3개월", Date.from(LocalDateTime.now().minusMonths(3).atZone(ZoneId.systemDefault()).toInstant())),
	M_ONE("한 달", Date.from(LocalDateTime.now().minusMonths(1).atZone(ZoneId.systemDefault()).toInstant())),
	WEEK("최근 일주일", Date.from(LocalDateTime.now().minusWeeks(1).atZone(ZoneId.systemDefault()).toInstant()));

	private String desc;
	private Date baseDate;
}
