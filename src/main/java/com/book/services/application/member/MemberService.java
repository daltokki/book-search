package com.book.services.application.member;

import com.book.interfaces.member.exception.AlreadyExistsMemberException;
import com.book.interfaces.member.exception.UnMatchedPasswordException;
import com.book.interfaces.member.model.MemberRequestForm;
import com.book.repository.entity.Member;
import com.book.repository.MemberRepository;
import com.book.repository.value.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Service
@Transactional(readOnly = true)
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Member> getUserAll() {
		return memberRepository.findAll();
	}

	@Transactional
	public void create(MemberRequestForm memberRequestForm) {
		memberCreateValidation(memberRequestForm);

		Member member = Member.create(memberRequestForm.getEmail(), passwordEncoder.encode(memberRequestForm.getPassword()),
			new StringJoiner(",").add(memberRequestForm.getFirstName()).add(memberRequestForm.getLastName()).toString(),
			RoleType.ROLE_ACTIVE_MEMBER);

		memberRepository.save(member);
	}

	private void memberCreateValidation(MemberRequestForm memberRequestForm) {
		boolean isExistMember = memberRepository.existsByEmailEquals(memberRequestForm.getEmail());
		if (isExistMember) {
			throw new AlreadyExistsMemberException("Already exists email address.");
		}
		if (!memberRequestForm.getPassword().equals(memberRequestForm.getConfirmPassword())) {
			throw new UnMatchedPasswordException("The password doesn't match confirm password.");
		}
	}
}
