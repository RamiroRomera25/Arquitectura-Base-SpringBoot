package ar.edu.utn.frc.tup.p4.services.genericSegregation.compositeUniqueAtt;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import ar.edu.utn.frc.tup.p4.repositories.GenericRepository;
import ar.edu.utn.frc.tup.p4.repositories.specs.SpecificationBuilder;

import java.util.Map;

public interface ServiceGetByCompositeUniqueAtt<E, I, M> {
    ModelMapper getMapper();

    GenericRepository<E, I> getRepository();

    Class<E> entityClass();

    Class<M> modelClass();

    SpecificationBuilder<E> specificationBuilder();

    default E getByCompositeUniqueFields(Map<String, Object> uniqueFields) {
        return getRepository().findOne(specificationBuilder().compositeUniqueValues(uniqueFields).build())
                .orElseThrow(() -> new EntityNotFoundException(buildErrorMessage(uniqueFields)));
    }

    default M getModelByCompositeUniqueFields(Map<String, Object> uniqueFields) {
        return getRepository().findOne(specificationBuilder().compositeUniqueValues(uniqueFields).build())
                .map((entity) -> getMapper().map(entity, modelClass()))
                .orElseThrow(() -> new EntityNotFoundException(buildErrorMessage(uniqueFields)));
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
