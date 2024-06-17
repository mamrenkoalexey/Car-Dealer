package wit.mamrenko.carDealer.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wit.mamrenko.carDealer.entity.Producer;
import wit.mamrenko.carDealer.helper.HelperApplication;
import wit.mamrenko.carDealer.helper.HelperProducer;
import wit.mamrenko.carDealer.service.ServiceProducer;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/producers")
public class ControllerProducer {

    private static final String PATH = "/producers";
    private final ServiceProducer serviceProducer;

    @Autowired
    private HelperProducer helperProducer;

    @Autowired
    private HelperApplication helperApplication;

    public ControllerProducer(ServiceProducer serviceProducer) {
        this.serviceProducer = serviceProducer;
    }

    @GetMapping
    public String index(Model model) {
        List<Producer> producers = serviceProducer.getAllProducers();
        model.addAttribute("producers", producers);
        return "producers/index";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id) {
        Exception exception = serviceProducer.deleteProducerById(id);
        return "redirect:" + PATH;
    }

    @GetMapping("/new")
    public String create(Model model) {
        Producer producer = new Producer();
        model.addAttribute("producer", producer);
        model.addAttribute("error", "");
        return "producers/new";
    }

    @PostMapping
    public String save(@ModelAttribute("producer") Producer producer, Model model) {
        Exception exception = serviceProducer.saveProducer(producer);
        if (exception == null) {
            return "redirect:" + PATH;
        } else {
            model.addAttribute("error", "Something wrong");
            return "producers/new";
        }

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Producer producer = serviceProducer.getProducerById(id);
        model.addAttribute("producer", producer);
        return "producers/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("producer") @Valid Producer producer, Model model) {
        Producer existingProducer = serviceProducer.getProducerById(id);
        existingProducer.setName(producer.getName());

        Exception exception = serviceProducer.saveProducer(existingProducer);
        if (exception == null) {
            return "redirect:" + PATH;
        } else {
            model.addAttribute("error", "Something wrong");
            return "producers/edit";
        }
    }


    @RequestMapping(value = "/export")
    public ResponseEntity<Resource> export() throws IOException {
        String values = helperProducer.producersJSON();
        String fileName = "producers.json";

        return helperApplication.exportData(values, fileName);
    }

    @GetMapping("/import")
    public String importForm() {
        return "producers/import";
    }


    @PostMapping("/import")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        Exception exception = helperProducer.upload(file);
        if (exception == null) {
            return "redirect:" + PATH;
        } else {
            model.addAttribute("error", "File is invalid");
            return "producers/import";
        }
    }


}