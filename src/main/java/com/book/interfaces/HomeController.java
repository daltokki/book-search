package com.book.interfaces;

import com.book.services.application.book.value.BookSearchType;
import com.book.services.application.book.value.BookSortType;
import com.book.services.application.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping({"/", "/home"})
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("/book/search-main");
		modelAndView.addObject("bookSearchType", BookSearchType.values());
		modelAndView.addObject("bookSortType", BookSortType.values());
		modelAndView.addObject("bookCategoryMap", categoryService.getCategoryMap());
		return modelAndView;
	}
}
