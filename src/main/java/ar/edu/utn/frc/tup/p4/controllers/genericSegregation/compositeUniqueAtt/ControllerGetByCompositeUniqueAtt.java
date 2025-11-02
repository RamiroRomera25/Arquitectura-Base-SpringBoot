package ar.edu.utn.frc.tup.p4.controllers.genericSegregation.compositeUniqueAtt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.compositeUniqueAtt.ServiceGetByCompositeUniqueAtt;

import java.util.Map;

public interface ControllerGetByCompositeUniqueAtt<E, I, M, SERVICE extends ServiceGetByCompositeUniqueAtt<E, I, M>> {

    SERVICE getService();

    @PostMapping("/unique")
    default ResponseEntity<M> getByCompositeUniqueAtt(@RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(getService().getModelByCompositeUniqueFields(fields));
    }
}
