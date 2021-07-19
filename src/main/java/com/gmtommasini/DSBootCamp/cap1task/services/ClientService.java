package com.gmtommasini.DSBootCamp.cap1task.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmtommasini.DSBootCamp.cap1task.dto.ClientDTO;
import com.gmtommasini.DSBootCamp.cap1task.entities.Client;
import com.gmtommasini.DSBootCamp.cap1task.repositories.ClientRepository;
import com.gmtommasini.DSBootCamp.cap1task.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Client obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client '" + id + "' not found"));
		return new ClientDTO(obj);
	}

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> page = repository.findAll(pageRequest);
		return page.map(obj -> new ClientDTO(obj));
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client obj = new Client();
		copyDTOtoEntity(dto, obj);
		obj = repository.save(obj);
		return new ClientDTO(obj);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client obj = repository.getOne(id);
			copyDTOtoEntity(dto, obj);
			obj = repository.save(obj);
			return new ClientDTO(obj);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Client '" + id + "' not found");
		}
	}

	// No @Transactional - we need to capture DB exception
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Client '" + id + "' not found");
		}

	}

	private void copyDTOtoEntity(ClientDTO dto, Client obj) {
		obj.setName(dto.getName());
		obj.setBirthDate(dto.getBirthDate());
		obj.setChildren(dto.getChildren());
		obj.setCpf(dto.getCpf());
		obj.setIncome(dto.getIncome());
	}
}
