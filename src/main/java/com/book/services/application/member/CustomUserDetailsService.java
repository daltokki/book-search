package com.book.services.application.member;

import com.book.repository.entity.Member;
import com.book.services.domain.security.SecurityMember;
import com.book.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Autowired
	public CustomUserDetailsService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) {
		Member member = Optional.ofNullable(memberRepository.findByEmail(username)).orElseThrow(
			() -> new UsernameNotFoundException("Login fail. email not found."));
		return new SecurityMember(member);
	}
}
