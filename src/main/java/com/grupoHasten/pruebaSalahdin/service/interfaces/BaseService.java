package com.grupoHasten.pruebaSalahdin.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class BaseService {
    protected <T, R> R map(T t, Class<R> r) {
        try {
            R dto = r.getDeclaredConstructor().newInstance();
            for (Field entityField : t.getClass().getDeclaredFields()) {
                entityField.setAccessible(true);
                Field dtoField;
                try {
                    dtoField = r.getDeclaredField(entityField.getName());
                } catch (NoSuchFieldException e) {
                    continue;
                }
                dtoField.setAccessible(true);
                dtoField.set(dto, entityField.get(t));
            }
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map t to DTO", e);
        }
    }

    protected <T, R> Page<R> mapPage(Page<T> entities, Class<R> dtoClass, Pageable pageable) {
        List<R> dtos = entities.getContent().stream()
                .map(entity -> map(entity, dtoClass))
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }


}
