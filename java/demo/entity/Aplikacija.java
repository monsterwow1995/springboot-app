package demo.entity;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name = "aplikacija")
public class Aplikacija {

    @EmbeddedId
    private AplikacijaId id;

    @Column(name = "cv")
    @Lob
    private byte[] cv;

    @Transient
    private Radnik radnik;

    public AplikacijaId getId() {
        return id;
    }

    public void setId(AplikacijaId id) {
        this.id = id;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public Radnik getRadnik() {
        return radnik;
    }

    public void setRadnik(Radnik radnik) {
        this.radnik = radnik;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Aplikacija.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("cv=" + cv)
                .add("radnik=" + radnik)
                .toString();
    }
}
