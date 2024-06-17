package wit.mamrenko.carDealer.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import wit.mamrenko.carDealer.entity.Producer;
import wit.mamrenko.carDealer.service.ServiceProducer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component()
public class HelperProducer {

    private final ServiceProducer serviceProducer;

    public HelperProducer(ServiceProducer serviceProducer) {
        this.serviceProducer = serviceProducer;
    }


    public String producersJSON() {
        List<Producer> producers = serviceProducer.getAllProducers();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.toJson(producers);
    }

    public Exception upload(MultipartFile file) throws IOException {
        try {
            String jsonArray = new String(file.getBytes());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Producer>>() {
            }.getType();

            List<Producer> producersList = gson.fromJson(jsonArray, listType);

            for (Producer producer : producersList) {
                serviceProducer.saveProducer(producer);
            }
        } catch (Exception e) {
            return e;
        }
        finally {
            Files.deleteIfExists(Path.of(file.getOriginalFilename()));
        }
        return null;


    }

}
