package wit.mamrenko.carDealer.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import wit.mamrenko.carDealer.entity.Producer;

public interface RepositoryProducer extends CrudRepository<Producer, Long> {
    Object findAll(Sort orders);
}
