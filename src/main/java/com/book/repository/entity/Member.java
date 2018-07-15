package com.book.repository.entity;

import com.book.repository.value.RoleType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Member {
	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long memberId;

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
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "modified_at")
	private Date modifiedAt;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<Bookmark> bookmarkList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<SearchHistory> searchHistoryList;

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

	public void updatePassword(String password) {
		this.password = password;
	}
}
