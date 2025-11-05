package ar.edu.utn.frc.tup.p4.dtos.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class StatusDTO<ID extends Serializable, S extends Enum<S>, IDU extends Serializable>
        extends SoftDeletableDTO<ID, IDU> {

    private S status;

}
