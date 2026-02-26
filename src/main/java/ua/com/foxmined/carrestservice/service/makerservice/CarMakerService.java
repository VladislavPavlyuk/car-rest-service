package ua.com.foxmined.carrestservice.service.makerservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.EntityPresentException;
import ua.com.foxmined.carrestservice.model.CarMaker;
import ua.com.foxmined.carrestservice.service.DAOInterface;

import java.util.List;

@Service
public interface CarMakerService extends DAOInterface<CarMaker> {

    List<CarMaker> findByName(String name);

    CarMaker addManufacturer(String manufacturer);

    void deleteManufacturer(String manufacturer);

    void updateManufacturer(String oldManufacturer,String newManufacturer);

}
