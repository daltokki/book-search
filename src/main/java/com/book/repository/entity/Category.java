package com.book.repository.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Category {
	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "main_category")
	private String mainCategory;

	@Column(name = "sub_category")
	private String subCategory;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "modified_at")
	private Date modifiedAt;
}
