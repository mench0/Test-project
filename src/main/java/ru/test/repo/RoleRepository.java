package ru.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getByName(String name);
}
