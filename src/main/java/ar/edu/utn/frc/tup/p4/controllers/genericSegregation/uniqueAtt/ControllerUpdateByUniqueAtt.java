package ar.edu.utn.frc.tup.p4.controllers.genericSegregation.uniqueAtt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.uniqueAtt.ServiceUpdateByUniqueAtt;

public interface ControllerUpdateByUniqueAtt<E, I, M, DTOPUT, SERVICE extends ServiceUpdateByUniqueAtt<E, I, M, DTOPUT>> {

    SERVICE getService();

    @PutMapping("/unique/{value}")
    default ResponseEntity<M> updateByCompositeUniqueAtt(@RequestParam(required = false, defaultValue = "id") String field,
                                                         @PathVariable Object value,
                                                         @RequestBody DTOPUT dtoPut) {
        return ResponseEntity.ok(getService().update(dtoPut, field, value));
    }
}
