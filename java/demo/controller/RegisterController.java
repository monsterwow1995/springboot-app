package demo.controller;

import com.google.common.hash.Hashing;
import demo.constraint.ValidationStep;
import demo.entity.Poslodavac;
import demo.entity.Radnik;
import demo.repository.PoslodavacRepository;
import demo.repository.RadnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RadnikRepository radnikRepository;
    @Autowired
    private PoslodavacRepository poslodavacRepository;

    @RequestMapping("/menu")
    public String display() {
        return "registracija/registracija";
    }

    @GetMapping("/poslodavac")
    public String addPoslodavac(Model model) {

        model.addAttribute("poslodavac", new Poslodavac());

        return "registracija/registracija-poslodavac";
    }

    @PostMapping("/poslodavac")
    public String addPoslodavac(@Validated({ValidationStep.ValidationPoslodavacStepOne.class, ValidationStep.ValidationPoslodavacStepTwo.class}) @ModelAttribute("poslodavac") Poslodavac poslodavac, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "registracija/registracija-poslodavac";


        poslodavac.setPassword(Hashing.sha256()
                .hashString(poslodavac.getPassword(), StandardCharsets.UTF_8)
                .toString());

        poslodavacRepository.save(poslodavac);

        return "redirect:/login/menu";
    }

    @GetMapping("/radnik")
    public String addRadnik(Model model) {

        model.addAttribute("radnik", new Radnik());

        return "registracija/registracija-radnik";
    }

    @PostMapping("/radnik")
    public String addRadnik(@Valid @ModelAttribute("radnik") Radnik radnik, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "registracija/registracija-radnik";

        radnik.setPassword(Hashing.sha256().hashString(radnik.getPassword(), StandardCharsets.UTF_8).toString());

        radnikRepository.save(radnik);

        return "redirect:/index";
    }
}
