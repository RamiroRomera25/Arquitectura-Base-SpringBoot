package ar.edu.utn.frc.tup.p4.dtos.dummy;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DummyDto {
    private Long id;
    private String dummy;
    private Boolean isActive;
}
