package ar.edu.utn.frc.tup.p4.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public abstract class SoftDeletableDTO<ID extends Serializable, IDU extends Serializable> extends BaseAuditDTO<ID, IDU> {

    @JsonProperty("is_active")
    private Boolean isActive;

}
