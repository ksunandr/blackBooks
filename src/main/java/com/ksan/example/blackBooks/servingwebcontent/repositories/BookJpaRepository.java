package com.ksan.example.blackBooks.servingwebcontent.repositories;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookJpaRepository extends PagingAndSortingRepository<Book, Integer>, JpaSpecificationExecutor<Book> { //JpaRepository<Book, Integer> {
}
