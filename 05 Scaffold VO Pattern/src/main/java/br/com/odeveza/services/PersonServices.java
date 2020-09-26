package br.com.odeveza.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odeveza.converter.DozerConverter;
import br.com.odeveza.data.model.Person;
import br.com.odeveza.data.vo.PersonVO;
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

	public List<PersonVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public PersonVO update(PersonVO p) {
		var entity = repository.findById(p.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		entity.setFirstName(p.getFirstName());
		entity.setLastName(p.getLastName());
		entity.setAddress(p.getAddress());
		entity.setGender(p.getGender());
		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}

	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}

}