package wit.mamrenko.carDealer.service;

import wit.mamrenko.carDealer.entity.Offer;

import java.util.List;

public interface ServiceOffer {

    List<Offer> getAllOffers();

    Exception saveOffers(Offer offer);

    Offer getOfferById(Long id);

    Exception deleteOffersById(Long id);

}
