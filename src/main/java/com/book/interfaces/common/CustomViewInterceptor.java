package com.book.interfaces.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CustomViewInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		if (modelAndView != null) {
			Optional<UserDetails> userOptional = getUserDetailsOptional();
			modelAndView.addObject("needLogin", !userOptional.isPresent());
			modelAndView.addObject("username", userOptional.map(UserDetails::getUsername).orElse("guest"));
		}
	}

	private Optional<UserDetails> getUserDetailsOptional() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return Optional.of(userDetails);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}
