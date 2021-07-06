package demo.entity;

import demo.constraint.EmailUniqueConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

@Entity
@Table(name = "radnik")
public class Radnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    @NotNull(message = "Obavezno polje")
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "Nevalidan email")
    @EmailUniqueConstraint
    private String email;

    @Column(name = "password")
    @NotNull(message = "Obavezno polje")
    @Size(min = 8, message = "Mora imati bar 8 karakter")
    @Size(max = 100, message = "Ne sme biti duze od 100 karaktera")
    @Pattern(regexp = "^\\S+(\\s\\S+)*$", message = "Prazno polje")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", Radnik.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("email='" + email + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
