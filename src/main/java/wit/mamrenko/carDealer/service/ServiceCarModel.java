package wit.mamrenko.carDealer.service;

import wit.mamrenko.carDealer.entity.CarModel;

import java.util.List;

public interface ServiceCarModel {
    List<CarModel> getAllCarModels();
    Exception saveCarModel(CarModel carModel);
    CarModel getCarModelById(Long id);
    Exception deleteCarModelById(Long id);
}
