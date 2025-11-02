package ar.edu.utn.frc.tup.p4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerCreate;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerCreateList;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerGetAllList;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerGetAllPage;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerGetById;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerSoftDelete;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerSoftDeleteList;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerUpdate;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.filters.ControllerGetAllListFilter;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.filters.ControllerGetAllPageFilter;
import ar.edu.utn.frc.tup.p4.dtos.dummy.DummyDtoFilter;
import ar.edu.utn.frc.tup.p4.dtos.dummy.DummyDtoPost;
import ar.edu.utn.frc.tup.p4.dtos.dummy.DummyDtoPut;
import rami.generic.entities.DummyEntity;
import ar.edu.utn.frc.tup.p4.models.DummyModel;
import ar.edu.utn.frc.tup.p4.services.DummyService;

import java.util.List;

@RestController
@RequestMapping("/v5/dummy")
public class DummyController
implements ControllerGetById<DummyEntity, Long, DummyModel, DummyService>,
           ControllerGetAllList<DummyEntity, Long, DummyModel, DummyService>,
           ControllerGetAllPage<DummyEntity, Long, DummyModel, DummyService>,
           ControllerGetAllPageFilter<DummyEntity, Long, DummyModel, DummyDtoFilter, DummyService>,
           ControllerGetAllListFilter<DummyEntity, Long, DummyModel, DummyDtoFilter, DummyService>,
           ControllerSoftDelete<DummyEntity, Long, DummyModel, DummyService>,
           ControllerUpdate<DummyEntity, Long, DummyModel, DummyDtoPut, DummyService>,
           ControllerCreate<DummyEntity, Long, DummyModel, DummyDtoPost, DummyService>,
           ControllerCreateList<DummyEntity, Long, DummyModel, DummyDtoPost, DummyService>,
           ControllerSoftDeleteList<DummyEntity, Long, DummyModel, DummyService>
{

    @Autowired
    private DummyService dummyService;

    @GetMapping("/like")
    public ResponseEntity<List<DummyModel>> getDummiesLike(DummyDtoFilter filter) {
        return ResponseEntity.ok(dummyService.dummyLike(filter));
    }

    @Override
    public DummyService getService() {
        return dummyService;
    }
}
