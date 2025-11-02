package ar.edu.utn.frc.tup.p4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerCreate;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerGetAllList;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.basicCRUD.ControllerGetById;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.compositeUniqueAtt.ControllerGetByCompositeUniqueAtt;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.compositeUniqueAtt.ControllerUpdateByCompositeUniqueAtt;
import ar.edu.utn.frc.tup.p4.controllers.genericSegregation.uniqueAtt.ControllerGetByUniqueAtt;
import ar.edu.utn.frc.tup.p4.dtos.person.PersonDtoPost;
import rami.generic.entities.PersonEntity;
import ar.edu.utn.frc.tup.p4.models.PersonModel;
import ar.edu.utn.frc.tup.p4.services.PersonService;

@RestController
@RequestMapping("/v5/person")
public class PersonController
implements ControllerGetById<PersonEntity, Long, PersonModel, PersonService>,
           ControllerGetAllList<PersonEntity, Long, PersonModel, PersonService>,
           ControllerCreate<PersonEntity, Long, PersonModel, PersonDtoPost, PersonService>,
           ControllerGetByCompositeUniqueAtt<PersonEntity, Long, PersonModel, PersonService>,
           ControllerGetByUniqueAtt<PersonEntity, Long, PersonModel, PersonService>,
           ControllerUpdateByCompositeUniqueAtt<PersonEntity, Long, PersonModel, PersonDtoPost, PersonService>
{
    @Autowired
    private PersonService personService;

    @Override
    public PersonService getService() {
        return personService;
    }
}
