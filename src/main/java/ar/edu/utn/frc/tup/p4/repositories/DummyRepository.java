package ar.edu.utn.frc.tup.p4.repositories;

import org.springframework.stereotype.Repository;
import rami.generic.entities.DummyEntity;

@Repository
public interface DummyRepository extends GenericRepository<DummyEntity, Long> {
}
