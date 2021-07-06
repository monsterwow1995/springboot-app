package demo.entity;


import demo.constraint.EmailUniqueConstraint;
import demo.constraint.ValidationStep;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.StringJoiner;

@Entity
@DynamicUpdate
@Table(name = "poslodavac")
public class Poslodavac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "opis")
    @NotNull(message = "Obavezno polje", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    @Size(min = 100, message = "Ne sme biti krace od 100 karaktera", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    @Size(max = 3000, message = "Ne sme biti duze od 3000 karaktera", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    private String opis;

    @Column(name = "naziv")
    @NotNull(message = "Obavezno polje", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    @Size(max = 30, message = "Ne sme biti duze od 30 karaktera", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    private String naziv;

    @Column(name = "adresa")
    @NotNull(message = "Obavezno polje", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    @Size(max = 100, message = "Ne sme biti duze od 100 karaktera", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    private String adresa;

    @Column(name = "telefon")
    @NotNull(message = "Obavezno polje", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    @Size(max = 45, message = "Ne sme biti duze od 45 karaktera", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje", groups = ValidationStep.ValidationPoslodavacStepOne.class)
    private String telefon;

    @Column(name = "email")
    @NotNull(message = "Obavezno polje", groups = ValidationStep.ValidationPoslodavacStepTwo.class)
    @Size(max = 45, message = "Ne sme biti duze od 45 karaktera", groups = ValidationStep.ValidationPoslodavacStepTwo.class)
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje", groups = ValidationStep.ValidationPoslodavacStepTwo.class)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "Nevalidan email", groups = ValidationStep.ValidationPoslodavacStepTwo.class)
    @EmailUniqueConstraint(groups = ValidationStep.ValidationPoslodavacStepTwo.class)
    private String email;

    @Column(name = "password")
    @NotNull(message = "Obavezno polje", groups = ValidationStep.ValidationPoslodavacStepTwo.class)
    @Size(max = 100, message = "Ne sme biti duze od 100 karaktera", groups = ValidationStep.ValidationPoslodavacStepTwo.class)
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje", groups = ValidationStep.ValidationPoslodavacStepTwo.class)
    private String password;

    @OneToMany(mappedBy = "id_poslodavac")
    @Transient
    private List<Posao> posaoList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Posao> getPosaoList() {
        return posaoList;
    }

    public void setPosaoList(List<Posao> posaoList) {
        this.posaoList = posaoList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Poslodavac.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("opis='" + opis + "'")
                .add("naziv='" + naziv + "'")
                .add("adresa='" + adresa + "'")
                .add("telefon='" + telefon + "'")
                .add("email='" + email + "'")
                .add("password='" + password + "'")
                .add("posaoList=" + posaoList)
                .toString();
    }
}
