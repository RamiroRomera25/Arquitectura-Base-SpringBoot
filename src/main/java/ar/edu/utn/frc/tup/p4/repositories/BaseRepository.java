package ar.edu.utn.frc.tup.p4.repositories;

import ar.edu.utn.frc.tup.p4.entities.interfaces.BaseIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseIdEntity<ID>, ID extends Number> extends JpaRepository<T, ID>, JpaSpecificationExecutor<E> {
}
