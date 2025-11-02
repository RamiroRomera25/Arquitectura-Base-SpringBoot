package ar.edu.utn.frc.tup.p4.entities.interfaces;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public interface BaseIdEntity<ID extends Serializable> {

    ID getId();

    void setId(ID id);
}
