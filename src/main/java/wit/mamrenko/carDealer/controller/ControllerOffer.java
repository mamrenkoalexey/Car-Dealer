package wit.mamrenko.carDealer.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wit.mamrenko.carDealer.entity.Offer;
import wit.mamrenko.carDealer.helper.HelperApplication;
import wit.mamrenko.carDealer.helper.HelperOffer;
import wit.mamrenko.carDealer.service.ServiceOffer;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/offers")
public class ControllerOffer {

    private static final String PATH = "/offers";

    private final ServiceOffer serviceOffer;

    @Autowired
    private HelperOffer helperOffer;
    @Autowired
    private HelperApplication helperApplication;

    public ControllerOffer(ServiceOffer serviceOffer) {
        this.serviceOffer = serviceOffer;
    }

    @GetMapping
    public String index(Model model) {
        List<Offer> offers = serviceOffer.getAllOffers();
        model.addAttribute("offers", offers);
        return "offers/index";
    }

    @PostMapping
    public String save(@ModelAttribute("offer") Offer offer, Model model) {
        Exception exception = serviceOffer.saveOffers(offer);
        if (exception == null) {
            return "redirect:" + PATH;
        } else {
            helperOffer.addCarModelsToOffer(model);
            model.addAttribute("error", "Something wrong");
            return "offers/new";
        }
    }


    @GetMapping("/new")
    public String create(Model model) {
        helperOffer.addCarModelsToOffer(model);

        Offer offer = new Offer();
        model.addAttribute("offer", offer);
        return "offers/new";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        helperOffer.addCarModelsToOffer(model);

        Offer offer = serviceOffer.getOfferById(id);
        model.addAttribute("offer", offer);
        return "offers/edit";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Exception exception = serviceOffer.deleteOffersById(id);
        if (exception != null) {
            model.addAttribute("error", "Something wrong");
            List<Offer> offers = serviceOffer.getAllOffers();
            model.addAttribute("offers", offers);
            return "offers/index";
        }

        return "redirect:" + PATH;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, Model model, @ModelAttribute("offer") @Valid Offer offer) {
        Offer existingOffer = serviceOffer.getOfferById(id);

        existingOffer.setCar_model(offer.getCar_model());
        existingOffer.setVin(offer.getVin());
        existingOffer.setPrice(offer.getPrice());
        existingOffer.setYear(offer.getYear());
        existingOffer.setColor(offer.getColor());

        Exception exception = serviceOffer.saveOffers(existingOffer);
        if (exception == null) {
            return "redirect:" + PATH;
        } else {
            model.addAttribute("error", "Something wrong");
            return "offers/edit";
        }
    }

    @RequestMapping(value = "/export")
    public ResponseEntity<Resource> export() throws IOException {
        String values = helperOffer.offerJSON();
        String fileName = "offers.json";

        return helperApplication.exportData(values, fileName);
    }

    @GetMapping("/import")
    public String importForm() {
        return "offers/import";
    }


    @PostMapping("/import")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        Exception exception = helperOffer.upload(file);
        if (exception == null) {
            return "redirect:" + PATH;
        } else {
            model.addAttribute("error", "File is invalid");
            return "offers/import";
        }
    }
}
