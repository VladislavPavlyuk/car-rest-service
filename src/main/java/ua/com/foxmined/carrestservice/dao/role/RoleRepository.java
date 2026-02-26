package ua.com.foxmined.carrestservice.dao.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.model.Role;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

}
