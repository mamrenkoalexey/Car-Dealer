package wit.mamrenko.carDealer.service.implementation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wit.mamrenko.carDealer.entity.CarModel;
import wit.mamrenko.carDealer.repository.RepositoryCarModel;
import wit.mamrenko.carDealer.service.ServiceCarModel;

import java.util.List;

@Service
public class ServiceImplCarModel implements ServiceCarModel {
    private final RepositoryCarModel repositoryCarModel;

    public ServiceImplCarModel(RepositoryCarModel repositoryCarModel) {
        this.repositoryCarModel = repositoryCarModel;
    }

    @Override
    public List<CarModel> getAllCarModels() {
        List<CarModel> carModels = (List<CarModel>) repositoryCarModel.findAll(Sort.by(Sort.Direction.ASC, "name"));

        for (CarModel carModel : carModels) {
            carModel.setProducerName(carModel.producer.getName());
            carModel.setProducerID(carModel.producer.getId());
        }
        return carModels;
    }

    @Override
    public Exception saveCarModel(CarModel carModel) {
        try {
            repositoryCarModel.save(carModel);
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            return ex;
        }
        return null;
    }

    @Override
    public CarModel getCarModelById(Long id) {
        return repositoryCarModel.findById(id).get();
    }

    @Override
    public Exception deleteCarModelById(Long id) {
        try {
            repositoryCarModel.deleteById(id);
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            return ex;
        }
        return null;
    }
}
