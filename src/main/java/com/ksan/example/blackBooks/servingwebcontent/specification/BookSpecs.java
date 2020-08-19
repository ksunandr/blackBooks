package com.ksan.example.blackBooks.servingwebcontent.specification;

import com.ksan.example.blackBooks.servingwebcontent.entities.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public final class BookSpecs {

    public static Specification<Book> nameIs(final String name) {
        return (Specification<Book>) (root, query, builder) -> name == null ? builder.conjunction() : builder.equal(root.get("name"), name);
    }

    public static Specification<Book> authorNameIs(final String authorName) {
        return (Specification<Book>) (root, query, builder) -> authorName == null ?  builder.conjunction() : builder.equal(root.join("authors", JoinType.INNER).get("surname"), authorName);
    }

    public static Specification<Book> afterYear(final Integer year) {
        return (Specification<Book>) (root, query, builder) -> year == null ? builder.conjunction() : builder.ge(root.get("publicationYear"), year);
    }

    public static Specification<Book> rightYear(final Integer year) {
        return (Specification<Book>) (root, query, builder) -> year == null ? builder.conjunction() : builder.le(root.get("publicationYear"), year);
    }

    public static Specification<Book> inStock(final Boolean inStock) {
        return (Specification<Book>) (root, query, builder) -> inStock == null ?
                builder.conjunction() :
                (inStock ?
                        builder.gt(root.get("inStock"), 0) :
                        builder.le(root.get("inStock"), 0));
    }
}
