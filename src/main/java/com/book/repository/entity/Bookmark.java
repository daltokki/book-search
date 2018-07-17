package com.book.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Where(clause = "deleted = '0'")
@ToString(exclude = "member")
public class Bookmark {
	@Id
	@GeneratedValue
	@Column
	private Long bookmarkId;

	@ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "memberId", nullable = false)
	private Member member;

	@Column
	private String title;

	@Column
	private String publisher;

	@Column
	private Date datetime;

	@Column
	private String url;

	@Column
	private String sale_yn;

	@Column
	private String category;

	@Column
	private Boolean deleted = false;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "modified_at")
	private Date modifiedAt;

	public Bookmark() {}

	private Bookmark(Member member, String title, String publisher, Date datetime, String url, String sale_yn, String category) {
		this.member = member;
		this.title = title;
		this.publisher = publisher;
		this.datetime = datetime;
		this.url = url;
		this.sale_yn = sale_yn;
		this.category = category;
	}

	public static Bookmark create(Member member, String title, String publisher, Date datetime, String url, String sale_yn, String category) {
		return new Bookmark(member, title, publisher, datetime, url, sale_yn, category);
	}

	public void remove() {
		this.deleted = true;
	}
}
