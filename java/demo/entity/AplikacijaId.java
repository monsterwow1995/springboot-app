package demo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.StringJoiner;

@Embeddable
public class AplikacijaId implements Serializable {

    @Column(name = "id_radnik")
    private Integer idRadnik;

    @Column(name = "id_posao")
    private Integer idPosao;

    public Integer getIdRadnik() {
        return idRadnik;
    }

    public void setIdRadnik(Integer idRadnik) {
        this.idRadnik = idRadnik;
    }

    public Integer getIdPosao() {
        return idPosao;
    }

    public void setIdPosao(Integer idPosao) {
        this.idPosao = idPosao;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", AplikacijaId.class.getSimpleName() + "[", "]")
                .add("idRadnik=" + idRadnik)
                .add("idPosao=" + idPosao)
                .toString();
    }
}
