package ar.edu.utn.frc.tup.p4.dtos.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public abstract class BaseNamedDTO<ID extends Serializable, IDU extends Serializable> extends SoftDeletableDTO<ID, IDU> {

    private String name;

    private String description;

}
