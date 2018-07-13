package com.book.interfaces.member.exception;

public class AlreadyExistsMemberException extends RuntimeException {
	public AlreadyExistsMemberException(String message) {
		super(message);
	}
}
