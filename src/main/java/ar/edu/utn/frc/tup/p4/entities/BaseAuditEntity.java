package ar.edu.utn.frc.tup.p4.entities;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 *
 * @param <ID> id for superclass with annotation {@link jakarta.persistence.Id}
 * @param <IDU> id for trazability of ID type for users.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseAuditEntity<ID extends Number, IDU extends Serializable> extends BaseVersionedEntity<ID> {

    @CreatedBy
    @Column(updatable = false)
    private IDU createdBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    private IDU updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
