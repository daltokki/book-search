package com.book.interfaces.book;

import com.book.interfaces.book.model.DateAndPageSearchForm;
import com.book.interfaces.common.AjaxResult;
import com.book.services.application.book.BookmarkService;
import com.book.services.application.book.model.BookSearchDocumentsDto;
import com.book.services.application.book.value.DateSearchType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class BookmarkController {
	@Autowired
	private BookmarkService bookmarkService;

	@GetMapping("/bookmark")
	public ModelAndView main() {
		ModelAndView view = new ModelAndView("/bookmark/mark-main");
		view.addObject("dateSearchType", DateSearchType.values());
		return view;
	}

	@GetMapping("/bookmark/get")
	public ModelAndView getMark(DateAndPageSearchForm searchForm) {
		ModelAndView view = new ModelAndView("/bookmark/mark-table");
		view.addObject("condition", searchForm);
		view.addObject("page", bookmarkService.getBookmarkList(searchForm));
		return view;
	}

	@PostMapping("/bookmark/add")
	@ResponseBody
	public AjaxResult addBookmark(@RequestBody BookSearchDocumentsDto bookInfo) {
		try {
			Long bookmarkId = bookmarkService.addBookmark(bookInfo);
			log.info("Bookmark add complete. id={}", bookmarkId);
			return AjaxResult.builder().success(true).message("북마크 추가 완료.").build();
		} catch (Exception e) {
			log.warn("bookSearchService.addBookmark error. cause:", e.getMessage());
			return AjaxResult.builder().success(false).message("북마크 추가 실패.").build();
		}
	}

	@PostMapping("/bookmark/remove")
	@ResponseBody
	public AjaxResult removeBookmark(@RequestParam Long bookmarkId) {
		try {
			bookmarkService.deleteBookmark(bookmarkId);
			log.info("Bookmark remove complete. id={}", bookmarkId);
			return AjaxResult.builder().success(true).message("북마크 삭제 완료.").build();
		} catch (Exception e) {
			log.warn("bookSearchService.removeBookmark error. cause:", e.getMessage());
			return AjaxResult.builder().success(false).message("북마크 삭제 실패.").build();
		}
	}
}
