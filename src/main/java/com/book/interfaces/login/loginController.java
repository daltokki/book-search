package com.book.interfaces.login;

import com.book.interfaces.common.AjaxResult;
import com.book.services.application.member.exception.AlreadyExistsMemberException;
import com.book.services.application.member.exception.PolicyViolationPasswordException;
import com.book.services.application.member.exception.UnMatchedEmailException;
import com.book.services.application.member.exception.UnMatchedPasswordException;
import com.book.interfaces.member.model.MemberRequestForm;
import com.book.repository.entity.Member;
import com.book.services.application.member.MemberService;
import com.book.services.util.MailSenderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.StringJoiner;

@Controller
public class loginController {
	@Autowired
	private MemberService memberService;

	@Autowired
	private MailSenderUtils mailSender;

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
		} catch (AlreadyExistsMemberException | UnMatchedPasswordException | UnMatchedEmailException | PolicyViolationPasswordException e) {
			return AjaxResult.builder().success(false).message(e.getMessage()).build();
		} catch (Exception e) {
			return AjaxResult.builder().success(false).message("Sorry register Account failed. Try again.").build();
		}
	}

	@GetMapping("/forgot-password")
	@ResponseBody
	public AjaxResult forgetPassword(String email) {
		try {
			Member member = memberService.findMember(email);
			if (member == null) {
				throw new UsernameNotFoundException("unknown member.");
			}
			String tmpPassword = memberService.tmpPasswordPublish(member);

			MailSenderUtils.MailTemplate forgotPasswordTemplate = MailSenderUtils.MailTemplate.FORGOT_PASSWORD;
			String contents = new StringJoiner("\n").add(forgotPasswordTemplate.getContext()).add(tmpPassword).toString();

			mailSender.sendMail(email, forgotPasswordTemplate.getSubject(), contents);
			return AjaxResult.builder().success(true).message("Temp password mail send. check please").build();
		} catch (UsernameNotFoundException e) {
			return AjaxResult.builder().success(false).message(e.getMessage()).build();
		} catch (Exception e) {
			return AjaxResult.builder().success(false).message("Sorry send mail failed. Try again.").build();
		}
	}
}
