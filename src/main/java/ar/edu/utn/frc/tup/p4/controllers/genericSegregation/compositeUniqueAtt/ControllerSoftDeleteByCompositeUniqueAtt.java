package ar.edu.utn.frc.tup.p4.controllers.genericSegregation.compositeUniqueAtt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rami.generic.entities.base.BaseEntity;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.compositeUniqueAtt.ServiceSoftDeleteByCompositeUniqueAtt;

import java.util.Map;

public interface ControllerSoftDeleteByCompositeUniqueAtt<E extends BaseEntity, I, M, SERVICE extends ServiceSoftDeleteByCompositeUniqueAtt<E, I, M>> {

    SERVICE getService();

    @DeleteMapping("/unique")
    default ResponseEntity<M> deleteByCompositeUniqueAtt(@RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(getService().delete(fields));
    }

    @PatchMapping("/unique/reactivate")
    default ResponseEntity<M> reactivateByCompositeUniqueAtt(@RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(getService().reactivate(fields));
    }
}
