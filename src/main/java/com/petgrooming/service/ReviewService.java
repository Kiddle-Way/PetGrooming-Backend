package com.petgrooming.service;

import org.springframework.transaction.annotation.Transactional;

import com.petgrooming.dto.PageRequestDTO;
import com.petgrooming.dto.PageResponseDTO;
import com.petgrooming.dto.ReviewDTO;

@Transactional
public interface ReviewService {
	PageResponseDTO<ReviewDTO> getList(PageRequestDTO pageRequestDTO);

	PageResponseDTO<ReviewDTO> getSearchTitleList(PageRequestDTO pageRequestDTO, String searchTitle);

	PageResponseDTO<ReviewDTO> getSearchContentList(PageRequestDTO pageRequestDTO, String searchContent);

	Long register(ReviewDTO reviewDTO);

	ReviewDTO get(Long v_num);

	void modify(ReviewDTO reviewDTO);

	void remove(Long v_num);

}
