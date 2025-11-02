package ar.edu.utn.frc.tup.p4.services.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ar.edu.utn.frc.tup.p4.dtos.dummy.DummyDtoFilter;
import ar.edu.utn.frc.tup.p4.dtos.dummy.DummyDtoPost;
import rami.generic.entities.DummyEntity;
import ar.edu.utn.frc.tup.p4.models.DummyModel;
import ar.edu.utn.frc.tup.p4.repositories.DummyRepository;
import ar.edu.utn.frc.tup.p4.repositories.GenericRepository;
import ar.edu.utn.frc.tup.p4.repositories.specs.GenericSpecification;
import ar.edu.utn.frc.tup.p4.repositories.specs.SpecificationBuilder;
import ar.edu.utn.frc.tup.p4.services.DummyService;
import ar.edu.utn.frc.tup.p4.services.PersonService;
import ar.edu.utn.frc.tup.p4.services.genericSegregation.utils.RelationConfig;

import java.util.List;

@Service
@Profile({"dev", "mysql", "postgre"})
public class DummyServiceImpl implements DummyService {

    //#region Autowired
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DummyRepository dummyRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private GenericSpecification<DummyEntity> dummySpecification;

    @Autowired
    private SpecificationBuilder<DummyEntity> specificationBuilder;
    //#endregion

    //#region Override for Generic
    @Override
    public ModelMapper getMapper() {
        return modelMapper;
    }

    @Override
    public GenericRepository<DummyEntity, Long> getRepository() {
        return dummyRepository;
    }

    @Override
    public Class<DummyEntity> entityClass() {
        return DummyEntity.class;
    }

    @Override
    public Class<DummyModel> modelClass() {
        return DummyModel.class;
    }

    @Override
    public SpecificationBuilder<DummyEntity> specificationBuilder() {
        return specificationBuilder;
    }
    //#endregion

    @Override
    public DummyModel create(DummyDtoPost dtoPost) {
        return DummyService.super.createWithRelations(dtoPost,
                new RelationConfig<>("person", personService, dtoPost.getPersonId())
        );
    }

    @Override
    public List<DummyModel> dummyLike(DummyDtoFilter filter) {
        List<DummyEntity> entityList = getRepository().findAll(specificationBuilder
                                                    .withDynamicFilterLike(this.getFilterMap(filter))
                                                    .build());
        if (!entityList.isEmpty()) {
            return getMapper().map(entityList, new TypeToken<List<DummyModel>>() {}.getType());
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No content retrieved.");
        }
    }
}
