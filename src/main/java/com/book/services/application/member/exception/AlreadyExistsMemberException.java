package com.book.services.application.member.exception;

public class AlreadyExistsMemberException extends RuntimeException {
	public AlreadyExistsMemberException(String message) {
		super(message);
	}
}
