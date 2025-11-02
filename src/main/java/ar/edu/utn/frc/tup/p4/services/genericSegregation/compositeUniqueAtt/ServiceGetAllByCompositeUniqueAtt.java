package ar.edu.utn.frc.tup.p4.services.genericSegregation.compositeUniqueAtt;

import org.modelmapper.ModelMapper;
import ar.edu.utn.frc.tup.p4.repositories.GenericRepository;
import ar.edu.utn.frc.tup.p4.repositories.specs.SpecificationBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ServiceGetAllByCompositeUniqueAtt<E, I, M> {
    ModelMapper getMapper();

    GenericRepository<E, I> getRepository();

    Class<E> entityClass();

    Class<M> modelClass();

    SpecificationBuilder<E> specificationBuilder();

    default List<E> getAllByCompositeUniqueFields(Map<String, Object> uniqueFields) {
        return getRepository().findAll(specificationBuilder().compositeUniqueValues(uniqueFields).build());
    }

    default List<M> getAllModelByCompositeUniqueFields(Map<String, Object> uniqueFields) {
        return getRepository().findAll(specificationBuilder().compositeUniqueValues(uniqueFields).build()).stream()
                .map((entity) -> getMapper().map(entity, modelClass()))
                .collect(Collectors.toList());
    }

    private String buildErrorMessage(Map<String, Object> uniqueFields) {
        String fields = uniqueFields.entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .reduce((field1, field2) -> field1 + ", " + field2)
                .orElse("Unknown fields");

        return String.format("%s entity not found for %s.",
                entityClass().getSimpleName(), fields);
    }
}
