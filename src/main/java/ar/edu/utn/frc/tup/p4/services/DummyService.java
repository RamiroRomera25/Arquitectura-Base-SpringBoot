package ar.edu.utn.frc.tup.p4.services;

import org.springframework.stereotype.Service;
import ar.edu.utn.frc.tup.p4.dtos.dummy.DummyDtoFilter;
import ar.edu.utn.frc.tup.p4.dtos.dummy.DummyDtoPost;
import ar.edu.utn.frc.tup.p4.dtos.dummy.DummyDtoPut;
import rami.generic.entities.DummyEntity;
import ar.edu.utn.frc.tup.p4.models.DummyModel;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceCreate;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceCreateList;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceGetAllList;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceGetAllPage;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceGetById;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceSoftDelete;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceSoftDeleteList;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceUpdate;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.filters.ServiceGetAllListFilter;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.filters.ServiceGetAllPageFilter;

import java.util.List;

@Service
public interface DummyService
extends ServiceGetAllPage<DummyEntity, Long, DummyModel>,
        ServiceGetAllList<DummyEntity, Long, DummyModel>,
        ServiceGetById<DummyEntity, Long, DummyModel>,
        ServiceCreate<DummyEntity, Long, DummyModel, DummyDtoPost>,
        ServiceUpdate<DummyEntity, Long, DummyModel, DummyDtoPut>,
        ServiceSoftDelete<DummyEntity, Long, DummyModel>,
        ServiceGetAllListFilter<DummyEntity, Long, DummyModel, DummyDtoFilter>,
        ServiceGetAllPageFilter<DummyEntity, Long, DummyModel, DummyDtoFilter>,
        ServiceCreateList<DummyEntity, Long, DummyModel, DummyDtoPost>,
        ServiceSoftDeleteList<DummyEntity, Long, DummyModel>
        {
    List<DummyModel> dummyLike(DummyDtoFilter filter);
}
