package com.ksan.example.blackBooks.servingwebcontent.repositories;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public final class BookSpecs {


    public static Specification<Book> byName(final String name) {
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return name == null ? null : builder.equal(root.get("name"), name);
            }
        };
    }

    public static Specification<Book> byAuthorName(final String authorName) {
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return authorName == null ? null: builder.equal(root.join("authors", JoinType.INNER).get("surname"), authorName);
            }
        };
    }
}
