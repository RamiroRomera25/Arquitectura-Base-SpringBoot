package ar.edu.utn.frc.tup.p4.services.genericSegregation.compositeUniqueAtt;

import org.modelmapper.ModelMapper;
import ar.edu.utn.frc.tup.p4.repositories.GenericRepository;

import java.util.Map;

public interface ServiceUpdateByCompositeUniqueAtt<E, I, M, DTOPUT> extends ServiceGetByCompositeUniqueAtt<E, I, M> {

    ModelMapper getMapper();

    GenericRepository<E, I> getRepository();

    Class<M> modelClass();

    // TODO: Pensar como pasar los atributos para no hacer logica en el controller y hacer el mapeo aca.
    default M update(DTOPUT dtoPut, Map<String, Object> uniqueFiels) {
        E entity = this.getByCompositeUniqueFields(uniqueFiels);
        getMapper().map(dtoPut, entity);
        return getMapper().map(getRepository().save(entity), modelClass());
    }
}
