package com.alloymobile.product.service;


import com.alloymobile.product.persistence.model.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ProductBinding implements QuerydslBinderCustomizer<QProduct> {
    @Override
    public void customize(QuerydslBindings querydslBindings, QProduct qProduct) {
        // Make case-insensitive 'like' filter for all string properties
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    public static Predicate createSearchQuery(String search) {
        BooleanBuilder where = new BooleanBuilder();
        QProduct qProduct = QProduct.product;

        BooleanBuilder whereId = new BooleanBuilder();
        whereId.and(qProduct.name.containsIgnoreCase(search));
        where.or(qProduct.name.containsIgnoreCase(search));

        whereId = new BooleanBuilder();
        whereId.and(qProduct.description.containsIgnoreCase(search));
        where.or(whereId);

        whereId = new BooleanBuilder();
        whereId.and(qProduct.imageUrl.containsIgnoreCase(search));
        where.or(whereId);

        whereId = new BooleanBuilder();
        whereId.and(qProduct.allergies.containsIgnoreCase(search));
        where.or(whereId);

        whereId = new BooleanBuilder();
        whereId.and(qProduct.ingredients.containsIgnoreCase(search));
        where.or(whereId);

//        whereId = new BooleanBuilder();
//        whereId.and(qProduct.expiryDate.gt(ZonedDateTime.parse(search)));
//        where.or(whereId);

        return where;
    }
}