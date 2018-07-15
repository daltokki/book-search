package com.book.repository;

import com.book.repository.entity.SearchHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SearchHistoryRepository extends PagingAndSortingRepository<SearchHistory, Long> {
	Page<SearchHistory> findByCreatedAtAfter(Date createdAt, Pageable pageable);
}
