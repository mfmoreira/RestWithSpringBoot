package br.com.odeveza.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.odeveza.converter.DozerConverter;
import br.com.odeveza.data.model.Book;
import br.com.odeveza.data.vo.v1.BookVO;
import br.com.odeveza.exception.ResourceNotFoundException;
import br.com.odeveza.repository.BookRepository;

@Service
public class BookServices {

	@Autowired
	BookRepository repository;

	public BookVO create(BookVO book) {
		var entity = DozerConverter.parseObject(book, Book.class);
		return DozerConverter.parseObject(repository.save(entity), BookVO.class);
	}

	public List<BookVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
	}

	public BookVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public BookVO update(BookVO b) {
		var entity = repository.findById(b.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		entity.setAuthor(b.getAuthor());
		entity.setLaunchDate(b.getLaunchDate());
		entity.setPrice(b.getPrice());
		entity.setTitle(b.getTitle());
		return DozerConverter.parseObject(repository.save(entity), BookVO.class);
	}

	public void delete(Long id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}

}