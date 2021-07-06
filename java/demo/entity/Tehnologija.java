package demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "tehnologija")
public class Tehnologija {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "id_posao")
    private Integer idPosao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getIdPosao() {
        return idPosao;
    }

    public void setIdPosao(Integer idPosao) {
        this.idPosao = idPosao;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tehnologija.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("naziv='" + naziv + "'")
                .add("idPosao=" + idPosao)
                .toString();
    }
}
