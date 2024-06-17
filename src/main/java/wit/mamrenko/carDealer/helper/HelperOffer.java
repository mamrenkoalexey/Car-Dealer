package wit.mamrenko.carDealer.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import wit.mamrenko.carDealer.entity.CarModel;
import wit.mamrenko.carDealer.entity.Offer;
import wit.mamrenko.carDealer.service.ServiceCarModel;
import wit.mamrenko.carDealer.service.ServiceOffer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
@Component
public class HelperOffer {

    private final ServiceCarModel serviceCarModel;
    private final ServiceOffer serviceOffer;

    public HelperOffer(ServiceCarModel serviceCarModel, ServiceOffer serviceOffer) {
        this.serviceCarModel = serviceCarModel;
        this.serviceOffer = serviceOffer;
    }

    public void addCarModelsToOffer(Model model){
        List<CarModel> carModels = serviceCarModel.getAllCarModels();
        model.addAttribute("car_models", carModels);
    }

    public String offerJSON() {
        List<Offer> offers = serviceOffer.getAllOffers();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.toJson(offers);
    }

    public Exception upload(MultipartFile file) throws IOException {
        try {
            String jsonArray = new String(file.getBytes());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Offer>>() {
            }.getType();

            List<Offer> offerList = gson.fromJson(jsonArray, listType);

            for (Offer offer : offerList) {
                System.out.println(offer);
                try {
                    offer.car_model = serviceCarModel.getCarModelById(offer.getCarModelId());
                } catch (Exception e) {
                    return e;
                }
                serviceOffer.saveOffers(offer);
            }

        } catch (Exception e) {
            return e;
        }finally {
            Files.deleteIfExists(Path.of(file.getOriginalFilename()));
        }
        return null;
    }

}
