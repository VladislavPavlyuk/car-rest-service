package ua.com.foxmined.carrestservice.dao.modelcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.model.CarModelCategory;

@Repository
@Transactional
public interface CarModelCategoryRepository extends JpaRepository<CarModelCategory,Long> {

}
