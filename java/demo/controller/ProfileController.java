package demo.controller;

import demo.constraint.ValidationStep;
import demo.entity.*;
import demo.repository.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private PoslodavacRepository poslodavacRepository;
    @Autowired
    private RadnikRepository radnikRepository;
    @Autowired
    private PosaoRepository posaoRepository;
    @Autowired
    private TehnologijaRepository tehnologijaRepository;
    @Autowired
    private AplikacijaRepository aplikacijaRepository;

    private SessionFactory sessionFactory;

    @RequestMapping("/poslodavac")
    public String displayPoslodavac() {
        return "nalog/poslodavac-meni";
    }

    @GetMapping("/poslodavac/izmeni")
    public String updatePoslodavac(Model model, HttpServletRequest request) {

        model.addAttribute("poslodavac", request.getSession().getAttribute("user"));

        return "nalog/poslodavac-izmeni";
    }

    @PostMapping("/poslodavac/izmeni")
    public String updatePoslodavac(@ModelAttribute("poslodavac") @Validated(ValidationStep.ValidationPoslodavacStepOne.class) Poslodavac poslodavac, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors())
            return "nalog/poslodavac-izmeni";

        poslodavacRepository.save(poslodavac);
        request.getSession().setAttribute("user", poslodavac);

        return "redirect:/profile/poslodavac";
    }

    @GetMapping("/poslodavac/posao")
    public String displayPosao(Model model, HttpServletRequest request) {

        ArrayList<Posao> poslovi = new ArrayList<Posao>();

        Poslodavac poslodavac = (Poslodavac) request.getSession().getAttribute("user");

        for (Posao posao : posaoRepository.findAll()) {
            if (posao.getPoslodavacId().equals(poslodavac.getId())) {
                poslovi.add(posao);

                List<Tehnologija> tehnologijaList = new ArrayList<>();

                for (Tehnologija tehnologija : tehnologijaRepository.findAll()) {
                    if (tehnologija.getIdPosao().equals(posao.getId()))
                        tehnologijaList.add(tehnologija);
                }


                for (Aplikacija aplikacija : aplikacijaRepository.findAll()) {
                    System.out.println(aplikacija);
                    if (aplikacija.getId().getIdPosao().equals(posao.getId()))
                        posao.getAplikacijaList().add(aplikacija);

                }

                posao.setTehnologijaList(tehnologijaList);
            }
        }

        model.addAttribute("poslovi", poslovi);

        return "nalog/poslodavac-posao";
    }

    @GetMapping("/poslodavac/posao/aplikacija/{id}")
    public String displayAplikacijaList(@PathVariable("id") int idPosao, Model model) {

        List<Aplikacija> aplikacijaList = new ArrayList<>();

        for (Aplikacija aplikacija : aplikacijaRepository.findAll()) {

            aplikacija.setRadnik(radnikRepository.findById(aplikacija.getId().getIdRadnik()).get());

            if (aplikacija.getId().getIdPosao().equals(idPosao))
                aplikacijaList.add(aplikacija);
        }

        model.addAttribute("aplikacije", aplikacijaList);

        return "nalog/poslodavac-posao-aplikacija";

    }

    @GetMapping("/poslodavac/posao/aplikacija/{idPosao}/{idRadnik}")
    public void displayAplikacijaList(@PathVariable("idPosao") int idPosao, @PathVariable("idRadnik") int idRadnik, Model model, HttpServletResponse response) {

        String downloadDocument;

        AplikacijaId aplikacijaId = new AplikacijaId();
        aplikacijaId.setIdPosao(idPosao);
        aplikacijaId.setIdRadnik(idRadnik);

        Aplikacija aplikacija = aplikacijaRepository.findById(aplikacijaId).get();

        byte[] downloadDocumentBytes = aplikacija.getCv();

        try {
            InputStream inputStream = new ByteArrayInputStream(downloadDocumentBytes);
            response.setHeader("Content-Disposition", "inline");
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            OutputStream out = response.getOutputStream();
            IOUtils.copy(inputStream, out);
            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @GetMapping("/poslodavac/posao/dodaj")
    public String addPosao(Model model, HttpServletRequest request) {

        model.addAttribute("posao", new Posao());

        return "nalog/poslodavac-posao-dodaj";
    }

    @PostMapping("/poslodavac/posao/dodaj")
    public String addPosao(@Valid @ModelAttribute("posao") Posao posao, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors())
            return "nalog/poslodavac-posao-dodaj";

        String[] tagsString = posao.getTehnologije().split(" ");
        List<Tehnologija> tehnologijaList = new ArrayList<Tehnologija>();

        for (int i = 0; i < tagsString.length; i++) {
            Tehnologija tehnologija = new Tehnologija();
            tehnologija.setNaziv(tagsString[i]);
            tehnologijaList.add(tehnologija);
        }

        posao.setPoslodavacId(((Poslodavac) session.getAttribute("user")).getId());
        posaoRepository.save(posao);

        for (Tehnologija tehnologija : tehnologijaList) {
            tehnologija.setIdPosao(posao.getId());
            tehnologijaRepository.save(tehnologija);
        }
        return "redirect:/profile/poslodavac/posao";
    }

    @GetMapping("poslodavac/posao/izmeni/{id}")
    public String updatePosao(@PathVariable("id") Integer id, Model model) {

        Posao posao = posaoRepository.findById(id).get();
        List<Tehnologija> tehnologijaList = new ArrayList<>();

        for (Tehnologija tehnologija : tehnologijaRepository.findAll()) {
            if (tehnologija.getIdPosao().equals(posao.getId()))
                tehnologijaList.add(tehnologija);
        }

        posao.setTehnologijaList(tehnologijaList);
        String tehnologije = "";

        for (Tehnologija tehnologija : tehnologijaList)
            tehnologije += tehnologija.getNaziv() + " ";

        posao.setTehnologije(tehnologije);


        model.addAttribute("posao", posao);


        return "nalog/poslodavac-posao-izmeni";
    }

    @PostMapping("poslodavac/posao/izmeni/{id}")
    public String updatePosao(@PathVariable("id") Integer id, @Valid @ModelAttribute("posao") Posao posao, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors())
            return "nalog/poslodavac-posao-izmeni";

        String[] tagsString = posao.getTehnologije().split(" ");
        List<Tehnologija> tehnologijaList = new ArrayList<Tehnologija>();

        for (int i = 0; i < tagsString.length; i++) {
            Tehnologija tehnologija = new Tehnologija();
            tehnologija.setNaziv(tagsString[i]);
            tehnologijaList.add(tehnologija);
        }

        posao.setPoslodavacId(((Poslodavac) session.getAttribute("user")).getId());

        posaoRepository.save(posao);

        for (Tehnologija tehnologija : tehnologijaRepository.findAll()) {
            if (tehnologija.getIdPosao().equals(posao.getId()))
                tehnologijaRepository.delete(tehnologija);
        }

        for (Tehnologija tehnologija : tehnologijaList) {
            tehnologija.setIdPosao(posao.getId());
            tehnologijaRepository.save(tehnologija);
        }

        return "redirect:/profile/poslodavac/posao";
    }

    @RequestMapping("poslodavac/posao/izbrisi/{id}")
    public String deletePosao(@PathVariable("id") Integer id) {

        posaoRepository.deleteById(id);

        return "redirect:/profile/poslodavac/posao";
    }

    @RequestMapping("/radnik")
    public String displayRadnik() {
        return "nalog/radnik-meni";
    }

    @GetMapping("/radnik/posao/{id}")
    public String addAplikacija(Model model, @PathVariable("id") int id) {

        model.addAttribute("cv");
        model.addAttribute("idPosao", id);

        return "nalog/radnik-posao";
    }

    @PostMapping("/radnik/posao/{id}")
    public String addAplikacija(@RequestParam("cv") MultipartFile cv, @PathVariable("id") int idPosao, HttpSession session, Model model) {
        if (!cv.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            model.addAttribute("isNotValid", true);
            return "nalog/radnik-posao";
        }
        byte[] byteData = new byte[0];
        try {
            byteData = cv.getBytes();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Aplikacija aplikacija = new Aplikacija();
        AplikacijaId aplikacijaId = new AplikacijaId();

        aplikacijaId.setIdRadnik(((Radnik) session.getAttribute("user")).getId());
        aplikacijaId.setIdPosao(idPosao);

        aplikacija.setId(aplikacijaId);
        aplikacija.setCv(byteData);

        aplikacijaRepository.save(aplikacija);

        return "redirect:/index";
    }
}
