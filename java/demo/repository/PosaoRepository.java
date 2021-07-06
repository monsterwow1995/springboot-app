package demo.repository;

import demo.entity.Posao;
import org.springframework.data.repository.CrudRepository;

public interface PosaoRepository extends CrudRepository<Posao,Integer> {
}
