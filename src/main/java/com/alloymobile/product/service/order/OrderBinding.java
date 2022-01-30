package com.alloymobile.product.service.order;

import com.alloymobile.product.persistence.model.QOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public class OrderBinding  implements QuerydslBinderCustomizer<QOrder> {
    @Override
    public void customize(QuerydslBindings querydslBindings, QOrder qOrder) {
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

    public static Predicate createSearchQuery(String search){
        BooleanBuilder where = new BooleanBuilder();
        QOrder qOrder = QOrder.order;

//        BooleanBuilder whereId = new BooleanBuilder();
//        whereId.and(qOrder.clientId.equalsIgnoreCase(search));
//        where.or(whereId);

        return where;
    }
}
