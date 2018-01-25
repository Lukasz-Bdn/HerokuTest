package pl.schoolmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.schoolmanager.entity.SchoolUser;

public interface UserRepository extends JpaRepository<SchoolUser, Long>{
	SchoolUser findOneByUsername(String username);
	SchoolUser findOneByEmail(String username);
}
