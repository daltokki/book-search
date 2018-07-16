package com.book.services.application.member.exception;

public class PolicyViolationPasswordException extends RuntimeException {
	public PolicyViolationPasswordException(String message) {
		super(message);
	}
}
