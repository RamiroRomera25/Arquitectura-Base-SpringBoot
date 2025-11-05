package ar.edu.utn.frc.tup.p4.dtos.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseAuditDTO<ID extends Serializable, IDU extends Serializable> extends BaseDTO<ID> {

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("created_by")
    private IDU createdBy;

    @JsonProperty("updated_by")
    private IDU updatedBy;

}
