package com.book.interfaces.book;

import com.book.interfaces.book.model.BookSearchForm;
import com.book.interfaces.book.model.DateAndPageSearchForm;
import com.book.services.application.book.BookSearchHistoryService;
import com.book.services.application.book.BookSearchService;
import com.book.services.application.book.model.BookSearchResultDto;
import com.book.services.application.book.value.BookSortType;
import com.book.services.application.book.value.DateSearchType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class BookSearchController {
	@Autowired
	private BookSearchService bookSearchService;
	@Autowired
	private BookSearchHistoryService bookSearchHistoryService;

	@GetMapping("/book-search")
	public ModelAndView search(@ModelAttribute BookSearchForm bookSearchForm) {
		ModelAndView view = new ModelAndView("/book/search-table");
		BookSearchResultDto search;
		try {
			search = bookSearchService.search(bookSearchForm);
			view.addObject("page", search);
		} catch (Exception e) {
			log.error("Search Error" + e.getMessage());
		}
		view.addObject("bookSortType", BookSortType.values());
		view.addObject("condition", bookSearchForm);
		return view;
	}

	@GetMapping("/history")
	public ModelAndView historyMain() {
		ModelAndView view = new ModelAndView("/history/history-main");
		view.addObject("dateSearchType", DateSearchType.values());
		return view;
	}

	@GetMapping("/book-search/history")
	public ModelAndView getSearchHistory(DateAndPageSearchForm searchForm) {
		ModelAndView view = new ModelAndView("/history/history-table");
		view.addObject("dateSearchType", DateSearchType.values());
		view.addObject("condition", searchForm);
		view.addObject("page", bookSearchHistoryService.getHistoryList(searchForm));
		return view;
	}
}
