package com.book.repository.entity;

import com.book.services.application.book.value.BookSearchType;
import com.book.services.application.book.value.BookSortType;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@ToString(exclude = "member")
public class SearchHistory {
	@Id
	@GeneratedValue
	@Column
	private Long searchHistoryId;

	@ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "memberId", nullable = false)
	private Member member;

	@Enumerated(EnumType.STRING)
	@Column
	private BookSearchType bookSearchType;

	@Enumerated(EnumType.STRING)
	@Column
	private BookSortType bookSortType;

	@Column
	private String content;

	@Column
	private String mainCategory;

	@Column
	private String subCategory;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "modified_at")
	private Date modifiedAt;

	public SearchHistory() {}

	private SearchHistory(Member member, BookSearchType bookSearchType, BookSortType bookSortType, String content, String mainCategory, String subCategory) {
		this.member = member;
		this.bookSearchType = bookSearchType;
		this.bookSortType = bookSortType;
		this.content = content;
		this.mainCategory = mainCategory;
		this.subCategory = subCategory;
	}

	public static SearchHistory create(Member member, BookSearchType bookSearchType, BookSortType bookSortType, String content, String mainCategory,
		String subCategory) {
		return new SearchHistory(member, bookSearchType, bookSortType, content, mainCategory, subCategory);
	}
}
