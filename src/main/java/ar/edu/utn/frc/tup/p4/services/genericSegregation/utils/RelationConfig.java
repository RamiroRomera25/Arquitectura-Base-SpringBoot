package ar.edu.utn.frc.tup.p4.services.genericSegregation.utils;

import lombok.Getter;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceGetById;

import java.lang.reflect.Field;

public class RelationConfig<E, I> {
    @Getter
    private final String fieldName;
    private final ServiceGetById<E, I, ?> service;
    private final I id;

    public RelationConfig(String fieldName, ServiceGetById<E, I, ?> service, I id) {
        this.fieldName = fieldName;
        this.service = service;
        this.id = id;
    }

    public void configure(Object entity) throws NoSuchFieldException, IllegalAccessException {
        E relatedEntity = service.getById(id);

        Field field = entity.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(entity, relatedEntity);
    }
}
