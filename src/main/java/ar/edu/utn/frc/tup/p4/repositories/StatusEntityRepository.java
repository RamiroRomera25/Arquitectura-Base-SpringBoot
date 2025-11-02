package ar.edu.utn.frc.tup.p4.repositories;

import ar.edu.utn.frc.tup.p4.entities.BaseStatusEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface StatusEntityRepository<T extends BaseStatusEntity<ID, ?, ?>, ID extends Number, S extends Enum<S>> extends SoftDeletableRepository<T, ID> {
    List<T> findByStatus(S status);
}
