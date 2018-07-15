package com.book.services.application.book.model;

import com.book.interfaces.api.model.BookSearchDocumentsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class BookSearchDocumentsDto {
	private String title;
	private String contents;
	private String url;
	private String[] isbn;
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

	public BookSearchDocumentsDto(BookSearchDocumentsDTO documentsDTO) {
		this.title = documentsDTO.getTitle();
		this.contents = documentsDTO.getContents();
		this.url = documentsDTO.getUrl();
		this.isbn = documentsDTO.getIsbn().split(" ");
		this.datetime = documentsDTO.getDatetime();
		this.authors = documentsDTO.getAuthors();
		this.publisher = documentsDTO.getPublisher();
		this.translators = documentsDTO.getTranslators();
		this.price = documentsDTO.getPrice();
		this.sale_price = documentsDTO.getSale_price();
		this.sale_yn = documentsDTO.getSale_yn();
		this.category = documentsDTO.getCategory();
		this.thumbnail = documentsDTO.getThumbnail();
		this.barcode = documentsDTO.getBarcode();
		this.ebook_barcode = documentsDTO.getEbook_barcode();
		this.status = documentsDTO.getStatus();
	}
}
