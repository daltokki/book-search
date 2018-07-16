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
			return AjaxResult.builder().success(true).message("회원가입 완료!").build();
		} catch (AlreadyExistsMemberException | UnMatchedPasswordException | UnMatchedEmailException | PolicyViolationPasswordException e) {
			return AjaxResult.builder().success(false).message(e.getMessage()).build();
		} catch (Exception e) {
			return AjaxResult.builder().success(false).message("죄송합니다 회원가입에 실패하였습니다. 다시 시도해주세요.").build();
		}
	}

	@GetMapping("/forgot-password")
	@ResponseBody
	public AjaxResult forgetPassword(String email) {
		try {
			Member member = memberService.findMember(email);
			if (member == null) {
				throw new UsernameNotFoundException("등록되지 않은 사용자입니다.");
			}
			String tmpPassword = memberService.tmpPasswordPublish(member);

			MailSenderUtils.MailTemplate forgotPasswordTemplate = MailSenderUtils.MailTemplate.FORGOT_PASSWORD;
			String contents = new StringJoiner("\n").add(forgotPasswordTemplate.getContext()).add(tmpPassword).toString();

			mailSender.sendMail(email, forgotPasswordTemplate.getSubject(), contents);
			return AjaxResult.builder().success(true).message("임시 비밀번호가 전송되었습니다. 확인해 주세요.").build();
		} catch (UsernameNotFoundException | UnMatchedEmailException e) {
			return AjaxResult.builder().success(false).message(e.getMessage()).build();
		} catch (Exception e) {
			return AjaxResult.builder().success(false).message("이메일 전송에 실패하였습니다. 다시 시도해 주세요.").build();
		}
	}
}
