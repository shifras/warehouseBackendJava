package lt.bit.sandelys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.bit.sandelys.entities.Preke;


public interface PrekeRepository extends JpaRepository<Preke, Integer> {
	Preke findByName(String name);
}
