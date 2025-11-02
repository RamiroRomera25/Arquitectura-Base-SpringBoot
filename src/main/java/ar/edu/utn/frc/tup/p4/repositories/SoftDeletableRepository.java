package ar.edu.utn.frc.tup.p4.repositories;

import ar.edu.utn.frc.tup.p4.entities.BaseSoftDeletableEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SoftDeletableRepository<T extends BaseSoftDeletableEntity<ID, ?>, ID extends Number> extends BaseRepository<T, ID> {
    @Query("select e from #{#entityName} e where e.is_active = false")
    List<T> findAllActive();

    @Query("select e from #{#entityName} e where e.is_active = false")
    List<T> findAllInactive();

    /**
     * Devuelve un registro activo (isActive = true)
     */
    @Override
    @Query("select e from #{#entityName} e where e.id = :id and e.active = true")
    Optional<T> findById(@Param("id") ID id);

    /**
     * Devuelve un registro sin filtrar por estado
     */
    @Query("select e from #{#entityName} e where e.id = :id ")
    Optional<T> findByIdIncludingInactive(@Param("id") ID id);
}
