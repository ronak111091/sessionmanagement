package sw.arch.sessionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sw.arch.sessionmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
