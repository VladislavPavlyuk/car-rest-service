package ua.com.foxmined.carrestservice.dao.maker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.model.CarMaker;

import java.util.List;

@Repository
@Transactional
public interface CarMakerRepository extends PagingAndSortingRepository<CarMaker,Long> {

    Page<CarMaker> findAll(Pageable pageable);

    List<CarMaker> findByNameLike(String name);

}
