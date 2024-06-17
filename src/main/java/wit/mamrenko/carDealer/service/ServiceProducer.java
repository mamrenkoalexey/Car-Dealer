package wit.mamrenko.carDealer.service;

import wit.mamrenko.carDealer.entity.Producer;

import java.util.List;

public interface ServiceProducer {
    List<Producer> getAllProducers();


    Exception saveProducer(Producer producer);

    Producer getProducerById(Long id);

    Exception deleteProducerById(Long id);

}
