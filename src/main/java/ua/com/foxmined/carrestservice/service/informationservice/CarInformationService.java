package ua.com.foxmined.carrestservice.service.informationservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dto.CarDto;
import ua.com.foxmined.carrestservice.model.CarInformation;
import ua.com.foxmined.carrestservice.service.DAOInterface;

import java.util.List;

@Service
public interface CarInformationService extends DAOInterface<CarInformation> {

    List<CarInformation> findByCarMakerId(Long id);

    Page<CarInformation> findByCarCategoryId(Long id, Pageable pageable);

    Page<CarInformation> findByCarFilters(CarDto carDto);

}
