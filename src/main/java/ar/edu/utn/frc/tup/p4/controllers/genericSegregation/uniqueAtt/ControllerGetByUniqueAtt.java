package ar.edu.utn.frc.tup.p4.controllers.genericSegregation.uniqueAtt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.uniqueAtt.ServiceGetByUniqueAtt;

public interface ControllerGetByUniqueAtt<E, I, M, SERVICE extends ServiceGetByUniqueAtt<E, I, M>> {

    SERVICE getService();

    @PostMapping("/unique/{value}")
    default ResponseEntity<M> getByCompositeUniqueAtt(@RequestParam(required = false, defaultValue = "id") String field,
                                                      @PathVariable Object value) {
        return ResponseEntity.ok(getService().getModelByUniqueField(field, value));
    }
}
