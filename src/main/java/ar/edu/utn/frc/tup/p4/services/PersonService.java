package ar.edu.utn.frc.tup.p4.services;

import org.springframework.stereotype.Service;
import ar.edu.utn.frc.tup.p4.dtos.person.PersonDtoPost;
import rami.generic.entities.PersonEntity;
import ar.edu.utn.frc.tup.p4.models.PersonModel;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceCreate;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceGetAllList;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceGetById;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD.ServiceGetByIdList;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.compositeUniqueAtt.ServiceGetByCompositeUniqueAtt;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.compositeUniqueAtt.ServiceUpdateByCompositeUniqueAtt;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.uniqueAtt.ServiceGetByUniqueAtt;

@Service
public interface PersonService
extends ServiceGetAllList<PersonEntity, Long, PersonModel>,
        ServiceGetById<PersonEntity, Long, PersonModel>,
        ServiceGetByIdList<PersonEntity, Long, PersonModel>,
        ServiceCreate<PersonEntity, Long, PersonModel, PersonDtoPost>,
        ServiceGetByUniqueAtt<PersonEntity, Long, PersonModel>,
        ServiceGetByCompositeUniqueAtt<PersonEntity, Long, PersonModel>,
        ServiceUpdateByCompositeUniqueAtt<PersonEntity, Long, PersonModel, PersonDtoPost>
{
}
