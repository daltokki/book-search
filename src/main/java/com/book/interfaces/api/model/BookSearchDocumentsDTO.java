package com.book.interfaces.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookSearchDocumentsDTO {
	private String title;
	private String contents;
	private String url;
	private String isbn;
	private Date datetime;
	private String[] authors;
	private String publisher;
	private String[] translators;
	private Integer price;
	private Integer sale_price;
	private String sale_yn;
	private String category;
	private String thumbnail;
	private String barcode;
	private String ebook_barcode;
	private String status;
}