package br.com.odeveza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.odeveza.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
