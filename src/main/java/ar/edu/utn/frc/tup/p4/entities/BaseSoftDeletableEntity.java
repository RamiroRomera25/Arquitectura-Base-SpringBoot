package ar.edu.utn.frc.tup.p4.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseSoftDeletableEntity<ID extends Number, IDU extends Serializable> extends BaseAuditEntity<ID, IDU> {

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
