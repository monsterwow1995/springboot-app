package demo.repository;

import demo.entity.Radnik;
import org.springframework.data.repository.CrudRepository;

public interface RadnikRepository extends CrudRepository<Radnik, Integer> {
}
