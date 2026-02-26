package ua.com.foxmined.carrestservice.dao.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.model.CarMaker;
import ua.com.foxmined.carrestservice.model.CarModel;

import java.util.List;

@Repository
@Transactional
public interface CarModelRepository extends PagingAndSortingRepository<CarModel,Long> {

    String FIND_ALL_MODELS_FROM_CURRENT_MODEL_NAME_AND_MAKER =
            """
            SELECT model_.*
            FROM model model_
            WHERE model_.name = :modelName and model_.maker_id = :carMakerId\s""";


    Page<CarModel> findAll(Pageable pageable);

    List<CarModel> findByNameLike(String name);

    @Query(value = FIND_ALL_MODELS_FROM_CURRENT_MODEL_NAME_AND_MAKER, nativeQuery = true)
    List<CarModel> findByNameAndCarMakerLike(@Param("modelName") String name,
                                             @Param("carMakerId") Long carMakerId);

}
