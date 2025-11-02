package ar.edu.utn.frc.tup.p4.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseStatusEntity<ID extends Number, S extends Enum<S>, IDU extends Serializable>
        extends BaseSoftDeletableEntity<ID, IDU> {

    @Column(nullable = false)
    private String status;

    public void setStatusEnum(S statusEnum) {
        this.status = statusEnum.name();
    }
}
