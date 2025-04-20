package com.wcc.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wcc.model.entity.PostcodeMapper;
import com.wcc.model.entity.Postcodelatlng;
import com.wcc.model.entity.PostcodelatlngDTO;
import com.wcc.model.repository.IPostRepository;

@Service
public class PostService {

	@Autowired
	private IPostRepository repository;
	
	@Autowired
	private PostcodeMapper postcodeMapper;

	@Transactional(readOnly = true)
	public List<Postcodelatlng> findAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public Postcodelatlng findByPostcode(String postcodePrefix) {
		return repository.findByPostcode(postcodePrefix);
	}
	
	@Transactional
	public PostcodelatlngDTO save(PostcodelatlngDTO postcodelatlng) {
		Postcodelatlng savedEntity = repository.save(postcodeMapper.toEntity(postcodelatlng));
		return postcodeMapper.toDto(savedEntity);
	}
}
