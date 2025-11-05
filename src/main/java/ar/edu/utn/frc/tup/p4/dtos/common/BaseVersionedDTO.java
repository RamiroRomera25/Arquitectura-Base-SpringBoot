package ar.edu.utn.frc.tup.p4.dtos.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class BaseVersionedDTO<ID extends Serializable> extends BaseDTO<ID> {

    private Long version;

}
