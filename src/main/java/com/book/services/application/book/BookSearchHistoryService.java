package com.book.services.application.book;

import com.book.interfaces.book.model.BookSearchForm;
import com.book.interfaces.book.model.DateAndPageSearchForm;
import com.book.repository.SearchHistoryRepository;
import com.book.repository.entity.Category;
import com.book.repository.entity.Member;
import com.book.repository.entity.SearchHistory;
import com.book.services.application.book.value.BookSortType;
import com.book.services.application.category.CategoryService;
import com.book.services.application.member.MemberService;
import com.book.services.domain.security.SecurityMember;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookSearchHistoryService {
	@Autowired
	private SearchHistoryRepository searchHistoryRepository;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MemberService memberService;

	@Transactional
	public Long saveHistory(BookSearchForm bookSearchForm) {
		String email = SecurityMember.getUserDetailsOptional().map(UserDetails::getUsername).orElseThrow(() -> new RuntimeException("unValid User."));
		Member member = memberService.findMember(email);

		Optional<Category> category = Optional.ofNullable(categoryService.findCategory(bookSearchForm.getCategory()));

		SearchHistory searchHistory = SearchHistory.create(member, bookSearchForm.getTarget(),
			bookSearchForm.getSort().isEmpty() ? BookSortType.ACCURACY.name() : bookSearchForm.getSort(),
			bookSearchForm.getQuery(), category.map(Category::getMainCategory).orElse(Strings.EMPTY),
			category.map(Category::getSubCategory).orElse(Strings.EMPTY));

		SearchHistory save = searchHistoryRepository.save(searchHistory);
		return save.getSearchHistoryId();
	}

	@Transactional(readOnly = true)
	public Page<SearchHistory> getHistoryList(DateAndPageSearchForm searchForm) {
		PageRequest pageRequest = PageRequest.of(searchForm.getPage() -1, searchForm.getSize());
		return searchHistoryRepository.findByCreatedAtAfter(searchForm.getDateSearchType().getBaseDate(), pageRequest);
	}
}
