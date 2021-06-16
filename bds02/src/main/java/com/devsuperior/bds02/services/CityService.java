package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;


@Service
public class CityService {

	
	@Autowired
	private CityRepository repository;
	
	@Transactional
	public List<CityDTO> findAll() {
		
		List<City> list = repository.findAll(Sort.by("name"));
		
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
		
	}
	
	public void delete(Long id) {
		
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City Not Found", e);
		}
		catch(DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Integrity violation", e);
		}
		
	}
		
	
}
