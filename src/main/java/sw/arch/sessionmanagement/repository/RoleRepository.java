package sw.arch.sessionmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sw.arch.sessionmanagement.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRole(String role);
}
