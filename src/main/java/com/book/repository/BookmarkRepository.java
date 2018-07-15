package com.book.repository;

import com.book.repository.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BookmarkRepository extends PagingAndSortingRepository<Bookmark, Long> {
	Page<Bookmark> findByCreatedAtAfter(Date createdAt, Pageable pageable);
}
