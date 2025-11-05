package ar.edu.utn.frc.tup.p4.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import ar.edu.utn.frc.tup.p4.entities.interfaces.BaseIdEntity;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity<ID extends Number> implements BaseIdEntity<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @Override
    public ID getId() {
        return this.id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }
}
