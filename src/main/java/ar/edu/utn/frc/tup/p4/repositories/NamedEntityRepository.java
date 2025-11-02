package ar.edu.utn.frc.tup.p4.repositories;

import ar.edu.utn.frc.tup.p4.entities.BaseNamedEntity;

import java.util.Optional;

public interface NamedEntityRepository<T extends BaseNamedEntity<ID, ?>, ID extends Number> extends SoftDeletableRepository<T, ID> {
    Optional<T> findByName(String name);
}
