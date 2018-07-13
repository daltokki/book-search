package com.book.interfaces.member.exception;

public class UnMatchedPasswordException extends RuntimeException {
	public UnMatchedPasswordException(String message) {
		super(message);
	}
}
