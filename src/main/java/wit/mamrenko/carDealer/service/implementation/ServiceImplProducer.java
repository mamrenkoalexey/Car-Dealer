package wit.mamrenko.carDealer.service.implementation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wit.mamrenko.carDealer.entity.Producer;
import wit.mamrenko.carDealer.repository.RepositoryProducer;
import wit.mamrenko.carDealer.service.ServiceProducer;

import java.util.ArrayList;

@Service
public class ServiceImplProducer implements ServiceProducer {

    private final RepositoryProducer repositoryProducer;

    public ServiceImplProducer(RepositoryProducer repositoryProducer) {
        this.repositoryProducer = repositoryProducer;
    }

    @Override
    public ArrayList<Producer> getAllProducers() {
        return (ArrayList<Producer>) repositoryProducer.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Exception saveProducer(Producer producer) {
        try {
            repositoryProducer.save(producer);
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            return ex;
        }
        return null;
    }

    @Override
    public Producer getProducerById(Long id) {
        return repositoryProducer.findById(id).get();
    }

    @Override
    public Exception deleteProducerById(Long id) {
        try {
            repositoryProducer.deleteById(id);
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            return ex;
        }
        return null;

    }
}
