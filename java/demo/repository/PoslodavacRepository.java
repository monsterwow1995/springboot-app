package demo.repository;

import demo.entity.Poslodavac;
import org.springframework.data.repository.CrudRepository;


public interface PoslodavacRepository extends CrudRepository<Poslodavac, Integer> {
}
