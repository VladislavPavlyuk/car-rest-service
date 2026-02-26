package ua.com.foxmined.carrestservice.dao.information;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.model.CarInformation;

import java.util.List;

@Repository
@Transactional
public interface CarInformationRepository extends PagingAndSortingRepository<CarInformation,Long> {

    String FIND_ALL_CARS_FROM_CURRENT_CAR_MAKER =
            """
            SELECT information_.*
            FROM information information_ JOIN model model_ ON information_.model_id = model_.id
            WHERE model_.maker_id = :carMakerId\s""";

    String FIND_ALL_CARS_FROM_CURRENT_CAR_CATEGORY =
            """
            SELECT information_.*
            FROM information information_ JOIN model model_ ON information_.model_id = model_.id
            JOIN modelCategory modelCategory_ ON model_.id = modelCategory_.model_id
            WHERE modelCategory_.category_id = :carCategoryId\s""";

    String FIND_ALL_CARS_FROM_CURRENT_FILTERS =
            """
            SELECT information_.*
            FROM information information_ JOIN model model_ ON information_.model_id = model_.id
            JOIN modelcategory modelcategory_ ON model_.id = modelcategory_.model_id
            JOIN category category_ ON category_.id = modelcategory_.category_id
            JOIN maker maker_ ON maker_.id = model_.maker_id
            WHERE (category_.name = :carCategoryName OR :carCategoryName IS NULL) AND
            (model_.name = :carModelName OR :carModelName IS NULL) AND
            (maker_.name = :carMakerName OR :carMakerName IS NULL) AND
            (EXTRACT(YEAR FROM information_.\"year\") > :minYear OR :minYear IS NULL) AND
            (EXTRACT(YEAR FROM information_.\"year\") < :maxYear OR :maxYear IS NULL)
            ORDER BY information_.\"year\" ASC
            \s""";

    Page<CarInformation> findAll(Pageable pageable);

    @Query(value = FIND_ALL_CARS_FROM_CURRENT_CAR_MAKER, nativeQuery = true)
    List<CarInformation> findByCarMakerId(@Param("carMakerId") Long id);

    @Query(value = FIND_ALL_CARS_FROM_CURRENT_CAR_CATEGORY, nativeQuery = true)
    Page<CarInformation> findByCarCategoryId(@Param("carCategoryId") Long id, Pageable pageable);

    @Query(value = FIND_ALL_CARS_FROM_CURRENT_FILTERS, nativeQuery = true)
    Page<CarInformation> findByCarFilters(@Param("carCategoryName") String categoryName,
                                          @Param("carMakerName") String makerName,
                                          @Param("carModelName") String modelName,
                                          @Param("minYear") Long minYear,
                                          @Param("maxYear") Long maxYear,
                                          Pageable pageable);


}
