package com.petgrooming.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petgrooming.domain.AvailableTimeslot;
import com.petgrooming.domain.Designer;
import com.petgrooming.dto.AvailableTimeslotDTO;
import com.petgrooming.repository.AvailableTimeslotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class AvailableTimeslotServiceImpl implements AvailableTimeslotService {
	private final ModelMapper modelMapper;
	private final AvailableTimeslotRepository availableTimeslotRepository;

	// 이용가능시간 리스트 불러오기
	@Override
	public List<AvailableTimeslotDTO> listAvailableTime(LocalDate date, Designer d_num) {
		List<AvailableTimeslot> availableTimeslots = availableTimeslotRepository.findAvailableTimeslotsByDate(date,
				d_num);
		return availableTimeslots.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	// 이용시간 사용가능 하게 만듬
	@Override
	public void makeAvailable(Long a_t_num) {
		AvailableTimeslot timeslot = availableTimeslotRepository.findById(a_t_num)
				.orElseThrow(() -> new RuntimeException("Timeslot not found with id: " + a_t_num));
		timeslot.setAvailable(true);
		availableTimeslotRepository.save(timeslot);
	}

	// 이용시간 사용불가능 하게 만듬
	@Override
	public void makeUnavailable(Long a_t_num) {
		AvailableTimeslot timeslot = availableTimeslotRepository.findById(a_t_num)
				.orElseThrow(() -> new RuntimeException("Timeslot not found with id: " + a_t_num));
		timeslot.setAvailable(false);
		availableTimeslotRepository.save(timeslot);
	}

	private AvailableTimeslotDTO convertToDTO(AvailableTimeslot availableTimeslot) {
		return modelMapper.map(availableTimeslot, AvailableTimeslotDTO.class);
	}
}
