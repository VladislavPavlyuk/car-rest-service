package ua.com.foxmined.carrestservice.service.modelservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.model.CarMaker;
import ua.com.foxmined.carrestservice.model.CarModel;
import ua.com.foxmined.carrestservice.service.DAOInterface;

import java.util.List;

@Service
public interface CarModelService extends DAOInterface<CarModel> {

    List<CarModel> findByName(String name);

    List<CarModel> findByNameAndCarMakerLike(String name, Long carMakerId);

}
