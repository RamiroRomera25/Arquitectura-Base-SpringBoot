package ar.edu.utn.frc.tup.p4.translators;

import java.util.List;
import java.util.stream.Collectors;

public abstract  class BaseTranslator<E, D> {

    /**
     * Convierte una entidad en su DTO correspondiente.
     *
     * @param entity entidad a convertir
     * @return DTO resultante
     */
    public abstract D toDto(E entity);

    /**
     * Convierte un DTO en su entidad correspondiente.
     *
     * @param dto dto a convertir
     * @return entidad resultante
     */
    public abstract E toEntity(D dto);

    /**
     * Convierte una lista de entidades en una lista de DTOs.
     *
     * @param entities lista de entidades
     * @return lista de DTOs
     */
    public List<D> toDtoList(List<E> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de DTOs en una lista de entidades.
     *
     * @param dtos lista de DTOs
     * @return lista de entidades
     */
    public List<E> toEntityList(List<D> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}