package wit.mamrenko.carDealer.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import wit.mamrenko.carDealer.entity.CarModel;

public interface RepositoryCarModel extends CrudRepository<CarModel, Long> {
    Object findAll(Sort name);
}
