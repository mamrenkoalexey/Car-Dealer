package wit.mamrenko.carDealer.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import wit.mamrenko.carDealer.entity.Offer;

public interface RepositoryOffer extends CrudRepository<Offer, Long> {

    Object findAll(Sort name);
}
