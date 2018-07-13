package com.book.repository.entity;

import com.book.repository.value.RoleType;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Entity
@ToString
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column(name = "email")
	private String email;

	@Column
	private String password;

	@Column
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private RoleType role;

	@CreationTimestamp
	@Column(name = "created_at")
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "modified_at")
	private Timestamp modifiedAt;

	public Member() {}

	public Member(String email, String password, String name, RoleType role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	public static Member create(String email, String password, String name, RoleType role) {
		return new Member(email, password, name, role);
	}
}
