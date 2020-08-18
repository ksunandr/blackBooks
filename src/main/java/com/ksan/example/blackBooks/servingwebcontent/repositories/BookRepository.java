package com.ksan.example.blackBooks.servingwebcontent.repositories;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository  extends CrudRepository<Book, Integer> {

    Iterable<Book> findByName(String name);
    //Iterable<Book> findByNameAndAuthors_AndPublicationYearInAndInStock(String name, String author);

   // Iterable<Book> findByNameAndAuthors_namein;
    List<Book> findByNameStartingWith(String name);


    @Query("FROM Book b join b.authors a2b where  " +
            " (:name is null or b.name = :name) " +
            "and  (:authorId is null or a2b.id = :authorId) " +
            "and ((:status = true and b.inStock > 0) " +
            "or (:status = false and b.inStock <= 0)" +
            "or (:status is null))"
            )
   List<Book> findForFilter(@Param("name") String name,
                            @Param("status") Boolean status,
                            @Param("authorId") Integer authorId);





}
