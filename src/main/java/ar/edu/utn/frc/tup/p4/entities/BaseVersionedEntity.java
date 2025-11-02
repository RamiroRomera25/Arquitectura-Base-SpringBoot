package ar.edu.utn.frc.tup.p4.entities;


import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseVersionedEntity<ID extends Number> extends BaseEntity<ID> {

    @Version
    private Long version;
}
