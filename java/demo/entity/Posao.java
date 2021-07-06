package demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "posao")
public class Posao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sektor")
    @NotNull(message = "Obavezno polje")
    private String sektor;

    @Column(name = "naziv_pozicije")
    @NotNull(message = "Obavezno polje")
    @Size(min = 4, message = "Mora imati bar 4 karaktera")
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje")
    private String nazivPozicije;

    @Column(name = "grad")
    @NotNull(message = "Obavezno polje")
    @Size(min = 2, message = "Mora imati bar 2 karaktera")
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje")
    private String grad;

    @Column(name = "senioritet")
    @NotNull(message = "Obavezno polje")
    @Size(min = 2, message = "Mora imati bar 2 karaktera")
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje")
    private String senioritet;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private  List<Tehnologija> tehnologijaList;

    @Transient
    private String tehnologije;

    @Column(name = "id_poslodavca")
    private Integer poslodavacId;

    @Transient
    private List<Aplikacija> aplikacijaList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSektor() {
        return sektor;
    }

    public void setSektor(String sektor) {
        this.sektor = sektor;
    }

    public Integer getPoslodavacId() {
        return poslodavacId;
    }

    public void setPoslodavacId(Integer poslodavacId) {
        this.poslodavacId = poslodavacId;
    }

    public String getNazivPozicije() {
        return nazivPozicije;
    }

    public void setNazivPozicije(String nazivPozicije) {
        this.nazivPozicije = nazivPozicije;
    }

    public List<Tehnologija> getTehnologijaList() {
        return tehnologijaList;
    }

    public void setTehnologijaList(List<Tehnologija> tehnologijaList) {
        this.tehnologijaList = tehnologijaList;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getSenioritet() {
        return senioritet;
    }

    public void setSenioritet(String senioritet) {
        this.senioritet = senioritet;
    }

    public String getTehnologije() {
        return tehnologije;
    }

    public void setTehnologije(String tehnologije) {
        this.tehnologije = tehnologije;
    }

    public List<Aplikacija> getAplikacijaList() {
        return aplikacijaList;
    }

    public void setAplikacijaList(List<Aplikacija> aplikacijaList) {
        this.aplikacijaList = aplikacijaList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Posao.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("sektor='" + sektor + "'")
                .add("nazivPozicije='" + nazivPozicije + "'")
                .add("grad='" + grad + "'")
                .add("senioritet='" + senioritet + "'")
                .add("tehnologijaList=" + tehnologijaList)
                .add("tehnologije='" + tehnologije + "'")
                .add("poslodavacId=" + poslodavacId)
                .add("aplikacijaList=" + aplikacijaList)
                .toString();
    }
}
