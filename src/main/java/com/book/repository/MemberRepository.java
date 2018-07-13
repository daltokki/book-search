package com.book.repository;

import com.book.repository.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Member findByEmail(String email);
	boolean existsByEmailEquals(String email);
}
