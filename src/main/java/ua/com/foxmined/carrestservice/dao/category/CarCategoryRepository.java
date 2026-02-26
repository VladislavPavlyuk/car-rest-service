package ua.com.foxmined.carrestservice.dao.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.model.CarCategory;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface CarCategoryRepository extends PagingAndSortingRepository<CarCategory,Long> {

    Page<CarCategory> findAll(Pageable pageable);

    List<CarCategory> findByNameLike(String name);

    Optional<CarCategory> findByName(String name);
}
