package demo.repository;

import demo.entity.Aplikacija;
import demo.entity.AplikacijaId;
import org.springframework.data.repository.CrudRepository;

public interface AplikacijaRepository extends CrudRepository<Aplikacija, AplikacijaId> {
}
