package demo.controller;

import demo.entity.Posao;
import demo.entity.Tehnologija;
import demo.repository.PosaoRepository;
import demo.repository.TehnologijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pretraga")
public class PretragaController {

    @Autowired
    private PosaoRepository posaoRepository;
    @Autowired
    private TehnologijaRepository tehnologijaRepository;

    @RequestMapping("/meni")
    public String display() {
        return "pretraga/pretraga";
    }

    @PostMapping("/rezultati")
    public String findPosao(Model model, HttpServletRequest request) {

        List<Posao> posaoList = new ArrayList<>();
        String sektor = request.getParameter("sektor");
        String nazivPozicije = request.getParameter("pozicija");
        String tehnologije = request.getParameter("tehnologije");
        String[] tehnologijeArr = tehnologije.split(" ");
        String grad = request.getParameter("grad");
        String senioritet = request.getParameter("senioritet");

        for (Posao posao : posaoRepository.findAll()) {
            List<Tehnologija> tehnologijaList = new ArrayList<>();

            for (Tehnologija tehnologija : tehnologijaRepository.findAll()) {
                if (tehnologija.getIdPosao().equals(posao.getId()))
                    tehnologijaList.add(tehnologija);
            }


            posao.setTehnologijaList(tehnologijaList);


            if ((sektor != null && posao.getSektor().equals(sektor)) ||
                    (nazivPozicije != null && posao.getNazivPozicije().equalsIgnoreCase(nazivPozicije)) ||
                    (grad != null && posao.getGrad().equals(grad)) ||
                    (senioritet != null && posao.getSenioritet().equalsIgnoreCase(senioritet)))
                posaoList.add(posao);

            for (String tehnologijaStr : tehnologijeArr) {
                for (Tehnologija tehnologija : posao.getTehnologijaList()) {
                    if (tehnologija.getNaziv().equalsIgnoreCase(tehnologijaStr))
                        posaoList.add(posao);
                }
            }
        }

        model.addAttribute("poslovi", posaoList);

        return "pretraga/rezultati";
    }
}
