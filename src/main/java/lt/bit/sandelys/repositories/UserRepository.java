package lt.bit.sandelys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.bit.sandelys.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);

}
