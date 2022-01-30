package com.alloymobile.product.application.utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

public class BindingHelper {

    public static void bindMultiId(QuerydslBindings querydslBindings, NumberPath<Long> qPath,String alias){
        QuerydslBindings.AliasingPathBinder<NumberPath<Long>,Long> binding = querydslBindings.bind(qPath);
        if(!StringUtils.isEmpty(alias)){
            binding = binding.as((alias));
        }
        binding.all((path,values)->{
            final BooleanBuilder predicate = new BooleanBuilder();
            for(Long id : values){
                if(Objects.isNull(id) || id.equals(0L)){
                    predicate.or(path.isNull());
                }else{
                    predicate.or(path.eq(id));
                }
            }
            return Optional.of(predicate);
        });
    }
}
