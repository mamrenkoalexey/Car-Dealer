package wit.mamrenko.carDealer.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wit.mamrenko.carDealer.entity.CarModel;
import wit.mamrenko.carDealer.helper.HelperApplication;
import wit.mamrenko.carDealer.helper.HelperCarModel;
import wit.mamrenko.carDealer.service.ServiceCarModel;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/carModels")
public class ControllerCarModel {

    private static final String PATH = "/carModels";

    private final ServiceCarModel serviceCarModel;

    @Autowired
    private HelperCarModel helperCarModel;

    @Autowired
    private HelperApplication helperApplication;

    public ControllerCarModel(ServiceCarModel serviceCarModel) {
        this.serviceCarModel = serviceCarModel;
    }

    @GetMapping
    public String index(Model model) {
        List<CarModel> carModels = serviceCarModel.getAllCarModels();

        model.addAttribute("carModels", carModels);
        return "carModels/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        helperCarModel.addProducersToModel(model);

        CarModel carModel = new CarModel();
        model.addAttribute("carModel", carModel);
        return "carModels/new";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Exception exception = serviceCarModel.deleteCarModelById(id);
        if (exception != null) {
            model.addAttribute("error", "Something wrong");
            List<CarModel> carModels = serviceCarModel.getAllCarModels();
            model.addAttribute("carModels", carModels);
            return "carModels/index";
        }

        return "redirect:" + PATH;
    }

    @PostMapping
    public String save(@ModelAttribute("carModel") CarModel carModel, Model model) {
        Exception exception = serviceCarModel.saveCarModel(carModel);
        if (exception == null) {
            return "redirect:" + PATH;
        } else {
            helperCarModel.addProducersToModel(model);

            model.addAttribute("error", "Something wrong");
            return "carModels/new";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        helperCarModel.addProducersToModel(model);

        CarModel carModel = serviceCarModel.getCarModelById(id);
        model.addAttribute("carModel", carModel);
        return "carModels/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, Model model, @ModelAttribute("carModel") @Valid CarModel carModel) {
        CarModel existingCarModel = serviceCarModel.getCarModelById(id);
        existingCarModel.setName(carModel.getName());
        existingCarModel.setVariant(carModel.getVariant());
        existingCarModel.setProducer(carModel.getProducer());

        Exception exception = serviceCarModel.saveCarModel(existingCarModel);
        if (exception == null) {
            return "redirect:" + PATH;
        } else {

            model.addAttribute("error", "Something wrong");
            return "carModels/edit";
        }
    }

    @RequestMapping(value = "/export")
    public ResponseEntity<Resource> export() throws IOException {
        String values = helperCarModel.carModelsJSON();
        String fileName = "carModels.json";

        return helperApplication.exportData(values, fileName);
    }

    @GetMapping("/import")
    public String importForm() {
        return "carModels/import";
    }

    @PostMapping("/import")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        Exception exception = helperCarModel.upload(file);
        if (exception == null) {
            return "redirect:" + PATH;
        } else {
            model.addAttribute("error", "File is invalid");
            return "carModels/import";
        }
    }
}
