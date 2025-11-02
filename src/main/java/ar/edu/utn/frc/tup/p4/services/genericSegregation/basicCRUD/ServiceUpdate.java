package ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD;

import org.modelmapper.ModelMapper;
import ar.edu.utn.frc.tup.p4.repositories.GenericRepository;

public interface ServiceUpdate<E, I, M, DTOPUT> extends ServiceGetById<E, I, M> {

    ModelMapper getMapper();

    GenericRepository<E, I> getRepository();

    Class<M> modelClass();

    default M update(DTOPUT dtoPut, I id) {
        E entity = this.getById(id);
        getMapper().map(dtoPut, entity);
        return getMapper().map(getRepository().save(entity), modelClass());
    }
}
