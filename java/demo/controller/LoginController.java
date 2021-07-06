package demo.controller;


import com.google.common.hash.Hashing;
import demo.entity.Poslodavac;
import demo.entity.Radnik;
import demo.repository.PoslodavacRepository;
import demo.repository.RadnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private PoslodavacRepository poslodavacRepository;
    @Autowired
    private RadnikRepository radnikRepository;

    @RequestMapping("/menu")
    public String display(Model model) {

        model.addAttribute("valid", true);

        return "prijava/prijava";
    }

    @PostMapping("/process")
    public String processLogin(Model model, HttpServletRequest request) {

        String email = request.getParameter("email");
        String password = Hashing.sha256().hashString(request.getParameter("password"), StandardCharsets.UTF_8).toString();

        for (Poslodavac poslodavac : poslodavacRepository.findAll()) {
            if (poslodavac.getEmail().equals(email) && poslodavac.getPassword().equals(password)) {
                request.getSession().setAttribute("user", poslodavac);
                request.getSession().setAttribute("role", "POSLODAVAC");
            }
        }

        for (Radnik radnik : radnikRepository.findAll()) {
            if (radnik.getEmail().equals(email) && radnik.getPassword().equals(password)) {
                request.getSession().setAttribute("user", radnik);
                request.getSession().setAttribute("role", "RADNIK");
            }
        }


        if (request.getSession().getAttribute("role") == null) {
            model.addAttribute("valid", false);
            return "prijava/prijava";
        }


        return "redirect:/index";
    }

    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request) {

        request.getSession().setAttribute("user", null);
        request.getSession().setAttribute("role", null);

        return "redirect:/index?logout=true";
    }
}
