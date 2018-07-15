package com.book.repository.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class SearchHistory {
	@Id
	@GeneratedValue
	@Column
	private Long searchHistoryId;

	@ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "memberId", nullable = false)
	private Member member;

	@Column
	private String bookSearchType;

	@Column
	private String bookSortType;

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

	private SearchHistory(Member member, String bookSearchType, String bookSortType, String content, String mainCategory, String subCategory) {
		this.member = member;
		this.bookSearchType = bookSearchType;
		this.bookSortType = bookSortType;
		this.content = content;
		this.mainCategory = mainCategory;
		this.subCategory = subCategory;
	}

	public static SearchHistory create(Member member, String bookSearchType, String bookSortType, String content, String mainCategory,
		String subCategory) {
		return new SearchHistory(member, bookSearchType, bookSortType, content, mainCategory, subCategory);
	}
}
