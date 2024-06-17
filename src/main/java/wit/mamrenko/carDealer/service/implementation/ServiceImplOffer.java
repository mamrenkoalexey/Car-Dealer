package wit.mamrenko.carDealer.service.implementation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wit.mamrenko.carDealer.entity.Offer;
import wit.mamrenko.carDealer.repository.RepositoryOffer;
import wit.mamrenko.carDealer.service.ServiceOffer;

import java.util.List;

@Service
public class ServiceImplOffer implements ServiceOffer {

    private final RepositoryOffer repositoryOffer;

    public ServiceImplOffer(RepositoryOffer repositoryOffer) {
        this.repositoryOffer = repositoryOffer;
    }

    @Override
    public List<Offer> getAllOffers() {
        List<Offer> offers = (List<Offer>) repositoryOffer.findAll(Sort.by(Sort.Direction.DESC, "year"));

        for (Offer offer : offers) {
            offer.setCarModelName(offer.car_model.getName());
            offer.setCarModelId(offer.car_model.getId());
        }

        return offers;
    }


    @Override
    public Exception saveOffers(Offer offer) {
        try {
            repositoryOffer.save(offer);
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            return ex;
        }
        return null;
    }

    @Override
    public Offer getOfferById(Long id) {
        return repositoryOffer.findById(id).get();
    }

    @Override
    public Exception deleteOffersById(Long id) {
        try {
            repositoryOffer.deleteById(id);
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            return ex;
        }
        return null;
    }
}
