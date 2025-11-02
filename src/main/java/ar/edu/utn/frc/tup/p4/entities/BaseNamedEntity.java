package ar.edu.utn.frc.tup.p4.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * This class is for auxiliar classes.
 *
 * @param <ID>
 * @param <UDI>
 */
@MappedSuperclass
@Setter
@Getter
public abstract class BaseNamedEntity<ID extends Number, UDI extends Serializable>
        extends BaseSoftDeletableEntity<ID, UDI> {

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(length = 500)
    private String description;
}
