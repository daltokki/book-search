package com.book.services.application.book;

import com.book.interfaces.book.model.DateAndPageSearchForm;
import com.book.repository.BookmarkRepository;
import com.book.repository.entity.Bookmark;
import com.book.repository.entity.Member;
import com.book.services.application.book.model.BookSearchDocumentsDto;
import com.book.services.application.member.MemberService;
import com.book.services.domain.security.SecurityMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookmarkService {
	@Autowired
	private BookmarkRepository bookmarkRepository;
	@Autowired
	private MemberService memberService;

	@Transactional
	public Long addBookmark(BookSearchDocumentsDto bookInfo) {
		String email = SecurityMember.getUserDetailsOptional().map(UserDetails::getUsername).orElseThrow(
			() -> new UsernameNotFoundException("unValid User."));
		Member member = memberService.findMember(email);

		Bookmark bookmark = Bookmark.create(member, bookInfo.getTitle(), bookInfo.getPublisher(), bookInfo.getDatetime(),
			bookInfo.getUrl(), bookInfo.getSale_yn(), bookInfo.getCategory());

		Bookmark save = bookmarkRepository.save(bookmark);
		return save.getBookmarkId();
	}

	@Transactional
	public void deleteBookmark(Long bookmarkId) {
		Optional<Bookmark> bookmarkOptional = bookmarkRepository.findById(bookmarkId);
		if (bookmarkOptional.isPresent()) {
			Bookmark bookmark = bookmarkOptional.get();
			bookmark.remove();
			bookmarkRepository.save(bookmark);
		} else {
			throw new RuntimeException("Bookmark does not exists.");
		}
	}

	@Transactional(readOnly = true)
	public Page<Bookmark> getBookmarkList(DateAndPageSearchForm searchForm) {
		PageRequest pageRequest = PageRequest.of(searchForm.getPage() - 1, searchForm.getSize());
		return bookmarkRepository.findByCreatedAtAfter(searchForm.getDateSearchType().getBaseDate(), pageRequest);
	}
}
