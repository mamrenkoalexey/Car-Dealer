package wit.mamrenko.carDealer.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import wit.mamrenko.carDealer.entity.CarModel;
import wit.mamrenko.carDealer.entity.Producer;
import wit.mamrenko.carDealer.service.ServiceCarModel;
import wit.mamrenko.carDealer.service.ServiceProducer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class HelperCarModel {

    private final ServiceCarModel serviceCarModel;
    private final ServiceProducer serviceProducer;

    public HelperCarModel(ServiceCarModel serviceCarModel, ServiceProducer serviceProducer) {
        this.serviceCarModel = serviceCarModel;
        this.serviceProducer = serviceProducer;
    }


    public String carModelsJSON() {
        List<CarModel> carModels = serviceCarModel.getAllCarModels();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.toJson(carModels);
    }


    public void addProducersToModel(Model model) {
        List<Producer> producers = serviceProducer.getAllProducers();
        model.addAttribute("producers", producers);
    }

    public Exception upload(MultipartFile file) throws IOException {
        try {
            String jsonArray = new String(file.getBytes());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<CarModel>>() {
            }.getType();

            List<CarModel> carModelList = gson.fromJson(jsonArray, listType);

            for (CarModel carModel : carModelList) {
                System.out.println(carModel);
                try {
                    carModel.producer = serviceProducer.getProducerById(carModel.getProducerID());
                } catch (Exception e) {
                    return e;
                }
                serviceCarModel.saveCarModel(carModel);
            }

        } catch (Exception e) {
            return e;
        } finally {
            Files.deleteIfExists(Path.of(file.getOriginalFilename()));
        }
        return null;
    }
}
