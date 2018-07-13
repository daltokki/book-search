package com.book.interfaces.login;

import com.book.interfaces.common.AjaxResult;
import com.book.interfaces.member.exception.AlreadyExistsMemberException;
import com.book.interfaces.member.exception.UnMatchedPasswordException;
import com.book.interfaces.member.model.MemberRequestForm;
import com.book.services.application.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class loginController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/login")
	public String login(Model model, boolean error, boolean logout, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		request.getSession().setAttribute("prevPage", referer);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		return "login";
	}

	@PostMapping("/register")
	@ResponseBody
	public AjaxResult create(@RequestBody MemberRequestForm memberRequestForm) {
		try {
			memberService.create(memberRequestForm);
			return AjaxResult.builder().success(true).message("Register Account Complete.").build();
		} catch (AlreadyExistsMemberException | UnMatchedPasswordException e) {
			return AjaxResult.builder().success(false).message(e.getMessage()).build();
		} catch (Exception e) {
			return AjaxResult.builder().success(false).message("Sorry register Account failed. Try again.").build();
		}
	}
}
