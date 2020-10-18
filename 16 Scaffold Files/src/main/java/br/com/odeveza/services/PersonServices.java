package br.com.odeveza.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.odeveza.converter.DozerConverter;
import br.com.odeveza.data.model.Person;
import br.com.odeveza.data.vo.v1.PersonVO;
import br.com.odeveza.exception.ResourceNotFoundException;
import br.com.odeveza.repository.PersonRepository;

@Service
public class PersonServices {

	@Autowired
	PersonRepository repository;

	public PersonVO create(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}

	public Page<PersonVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		return page.map(this::convertToPersonVO);
	}
	
	public Page<PersonVO> findPersonByName(Pageable pageable, String firstName) {
		var page = repository.findPersonByName(firstName, pageable);
		return page.map(this::convertToPersonVO);
		
	}

	private PersonVO convertToPersonVO(Person entity) {
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public PersonVO update(PersonVO p) {
		var entity = repository.findById(p.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		entity.setFirstName(p.getFirstName());
		entity.setLastName(p.getLastName());
		entity.setAddress(p.getAddress());
		entity.setGender(p.getGender());
		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}
	
	@Transactional
	public PersonVO desabledPerson(Long id) {
		repository.disablePerson(id);
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}

}