package com.book.services.application.member;

import com.book.interfaces.member.exception.AlreadyExistsMemberException;
import com.book.interfaces.member.exception.UnMatchedPasswordException;
import com.book.interfaces.member.model.MemberRequestForm;
import com.book.repository.entity.Member;
import com.book.repository.MemberRepository;
import com.book.repository.value.RoleType;
import com.book.services.domain.security.SecurityMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
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

	public Member findMember(String email) {
		return memberRepository.findByEmail(email);
	}

	@Transactional
	public void initTestUser() {
		String password = "ald";
		Member member = Member.create("joyee@coupang.com", passwordEncoder.encode(password), "MINJI,LIM", RoleType.ROLE_ACTIVE_MEMBER);
		Member save = memberRepository.save(member);
		log.info("save test user : " + save.toString());
	}

	public Member getMemberProfile() {
		String email = SecurityMember.getUserDetailsOptional().map(UserDetails::getUsername).orElseThrow(
			() -> new RuntimeException("invalid member."));
		return memberRepository.findByEmail(email);
	}

	@Transactional
	public String tmpPasswordPublish(Member member) {
		String uuid = UUID.randomUUID().toString();
		String encode = passwordEncoder.encode(uuid);

		member.updatePassword(encode);
		memberRepository.save(member);

		return uuid;
	}
}
