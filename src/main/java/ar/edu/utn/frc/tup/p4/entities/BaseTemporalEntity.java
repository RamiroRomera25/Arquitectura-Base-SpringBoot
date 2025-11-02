package ar.edu.utn.frc.tup.p4.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseTemporalEntity<ID extends Number, IDU extends Serializable>
        extends BaseAuditEntity<ID, IDU> {

    private LocalDate validFrom;
    private LocalDate validTo;

    public boolean isCurrentlyValid() {
        LocalDate now = LocalDate.now();
        return (validFrom == null || !now.isBefore(validFrom)) &&
                (validTo == null || !now.isAfter(validTo));
    }
}
