package com.book.interfaces.api.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResultDTO<T> {
	private boolean successful;
	private String message;
	private T body;

	public static <T> ApiResultDTO<T> succeed(T body) {
		return new ApiResultDTO(true, "SUCCESS", body);
	}

	public static <T> ApiResultDTO<T> succeed(String message, T body) {
		return new ApiResultDTO(true, message, body);
	}

	public static <T> ApiResultDTO<T> fail() {
		return new ApiResultDTO(false, "FAIL", null);
	}

	public static <T> ApiResultDTO<T> fail(String message) {
		return new ApiResultDTO(false, message, null);
	}

	public static <T> ApiResultDTO<T> fail(String message, T body) {
		return new ApiResultDTO(false, message, body);
	}
}
