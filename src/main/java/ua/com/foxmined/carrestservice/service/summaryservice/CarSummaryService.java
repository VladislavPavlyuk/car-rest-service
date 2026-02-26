package ua.com.foxmined.carrestservice.service.summaryservice;

import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.model.CarInformation;
import ua.com.foxmined.carrestservice.model.CarModel;

@Service
public interface CarSummaryService {

    CarModel addManufacturerAndModel(String manufacturer, String model);

    void deleteManufacturerAndModel(String manufacturer, String model) throws EntityNotPresentException;

    void updateModelCurrentManufacturer(String manufacturer, String oldModel, String newModel);

    CarInformation addManufacturerAndModelAndYear(String manufacturer, String model, String year) throws EntityNotPresentException;

}
