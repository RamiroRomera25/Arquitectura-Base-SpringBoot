package ar.edu.utn.frc.tup.p4.repositories;

import org.springframework.stereotype.Repository;
import rami.generic.entities.PersonEntity;

@Repository
public interface PersonRepository extends GenericRepository<PersonEntity, Long> {
}
