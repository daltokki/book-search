package com.book;

import com.book.services.application.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookSearchApplication implements CommandLineRunner {
	@Autowired
	private CategoryService categoryService;

	public static void main(String[] args) {
		SpringApplication.run(BookSearchApplication.class, args);
	}

	@Override
	public void run(String... args) {
		categoryService.initCategory();
	}
}
