package com.petgrooming.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petgrooming.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	@EntityGraph(attributePaths = {"imageList", "m_num"})
	@Query("select v from Review v join fetch v.m_num where v.v_num = :v_num")
	Optional<Review> selectOne(@Param("v_num") Long v_num);
	
	@Modifying
	@Query("update Review v set v.v_delFlag = :flag where v.v_num = :v_num")
	void updateToDelete(@Param("v_num") Long pno, @Param("flag") boolean flag);

	@Query("select v from Review v  where v.v_delFlag = false ")
	Page<Review> selectList(Pageable pageable);
	
	@Query("SELECT v FROM Review v WHERE v.v_delFlag = false AND v.v_title LIKE %:searchTitle%")
	Page<Review> searchTitleList(Pageable pageable, @Param("searchTitle") String searchTitle);
	
	@Query("SELECT v FROM Review v WHERE v.v_delFlag = false AND v.v_content LIKE %:searchContent%")
	Page<Review> searchContentList(Pageable pageable, @Param("searchContent") String searchContent);
}