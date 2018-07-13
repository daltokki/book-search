package com.book.services.domain.security;

import com.book.repository.entity.Member;
import com.book.repository.value.RoleType;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
@Setter
public class SecurityMember extends User {
	public SecurityMember(Member member) {
		super(member.getEmail(), member.getPassword(), makeGrantedAuthority(member.getRole()));
	}

	private static List<GrantedAuthority> makeGrantedAuthority(RoleType role){
		return Lists.newArrayList(new SimpleGrantedAuthority(role.name()));
	}
}
