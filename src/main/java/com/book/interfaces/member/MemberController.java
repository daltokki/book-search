package com.book.interfaces.member;

import com.book.services.application.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/member/profile")
	public ModelAndView getUserAll() {
		ModelAndView view = new ModelAndView("/member/profile");
		view.addObject("member", memberService.getMemberProfile());
		return view;
	}
}
