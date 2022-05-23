package lt.bit.sandelys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.bit.sandelys.entities.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {

}
