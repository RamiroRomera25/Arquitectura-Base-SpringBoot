package ar.edu.utn.frc.tup.p4.dtos.common;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public abstract class BaseDTO<ID extends Serializable> {

    private ID oid;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseDTO<?> other = (BaseDTO<?>) obj;
        if (getOid() == null) {
            return other.getOid() == null;
        } else {
            return getOid().equals(other.getOid());
        }
    }
}
