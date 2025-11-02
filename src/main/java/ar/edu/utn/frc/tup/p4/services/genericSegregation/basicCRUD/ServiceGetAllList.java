package ar.edu.utn.frc.tup.p4.services.genericSegregation.basicCRUD;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ar.edu.utn.frc.tup.p4.repositories.GenericRepository;

import java.util.List;

public interface ServiceGetAllList<E, I, M> {
    ModelMapper getMapper();

    GenericRepository<E, I> getRepository();

    default List<M> getAll(Sort sort) {
        List<E> entityList = getRepository().findAll(sort);
        if (!entityList.isEmpty()) {
            return getMapper().map(entityList, new TypeToken<List<M>>() {}.getType());
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No content retrieved.");
        }
    }
}
