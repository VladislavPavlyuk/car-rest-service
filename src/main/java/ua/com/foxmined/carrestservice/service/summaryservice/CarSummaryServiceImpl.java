package ua.com.foxmined.carrestservice.service.summaryservice;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.CarServiceException;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.model.CarInformation;
import ua.com.foxmined.carrestservice.model.CarMaker;
import ua.com.foxmined.carrestservice.model.CarModel;
import ua.com.foxmined.carrestservice.service.informationservice.CarInformationService;
import ua.com.foxmined.carrestservice.service.makerservice.CarMakerService;
import ua.com.foxmined.carrestservice.service.modelservice.CarModelService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

@Log4j2
@Service
public class CarSummaryServiceImpl implements CarSummaryService{

    private final CarMakerService carMakerService;
    private final CarModelService carModelService;
    private final CarInformationService carInformationService;

    public CarSummaryServiceImpl(CarMakerService carMakerService, CarModelService carModelService,
                                CarInformationService carInformationService) {
        this.carMakerService = carMakerService;
        this.carModelService = carModelService;
        this.carInformationService = carInformationService;
    }

    @Override
    public CarModel addManufacturerAndModel(String manufacturer, String model) {
        CarMaker carMaker = carMakerService.addManufacturer(manufacturer);
        return findOrCreateModel(carMaker, model);
    }

    private CarModel findOrCreateModel(CarMaker carMaker, String modelName) {
        List<CarModel> existing = carModelService.findByNameAndCarMakerLike(modelName, carMaker.getId());
        if (!existing.isEmpty()) {
            return existing.get(0);
        }
        CarModel newModel = new CarModel();
        newModel.setCarMaker(carMaker);
        newModel.setName(modelName);
        return carModelService.save(newModel);
    }

    @Override
    public void deleteManufacturerAndModel(String manufacturer, String model) throws EntityNotPresentException {
        CarMaker carMaker = findManufacturerOrThrow(manufacturer);
        CarModel carModel = findModelOrThrow(model, carMaker.getId());
        carModelService.delete(carModel);
    }

    private CarMaker findManufacturerOrThrow(String manufacturer) throws EntityNotPresentException {
        List<CarMaker> carMakers = carMakerService.findByName(manufacturer);
        if (carMakers.isEmpty()) {
            throw new EntityNotPresentException("Manufacturer not found: " + manufacturer);
        }
        return carMakers.get(0);
    }

    private CarModel findModelOrThrow(String modelName, Long makerId) throws EntityNotPresentException {
        List<CarModel> models = carModelService.findByNameAndCarMakerLike(modelName, makerId);
        if (models.isEmpty()) {
            throw new EntityNotPresentException("Model not found: " + modelName);
        }
        return models.get(0);
    }

    @Override
    public void updateModelCurrentManufacturer(String manufacturer, String oldModel, String newModel) {
        List<CarMaker> carMakers = carMakerService.findByName(manufacturer);
        if (carMakers.isEmpty()) {
            return;
        }
        CarMaker carMaker = carMakers.get(0);
        List<CarModel> models = carModelService.findByNameAndCarMakerLike(oldModel, carMaker.getId());
        if (!models.isEmpty()) {
            CarModel carModel = models.get(0);
            carModel.setName(newModel);
            carModelService.save(carModel);
        }
    }

    @Override
    public CarInformation addManufacturerAndModelAndYear(String manufacturer, String model, String year) throws EntityNotPresentException {
        CarMaker carMaker = carMakerService.addManufacturer(manufacturer);
        CarModel carModel = findModelOrThrow(model, carMaker.getId());
        CarInformation carInformation = buildCarInformation(carModel, year);
        return carInformationService.save(carInformation);
    }

    private CarInformation buildCarInformation(CarModel carModel, String year) {
        try {
            CarInformation info = new CarInformation();
            info.setCarModel(carModel);
            info.setDateOfManifacture(new SimpleDateFormat("yyyy").parse(year));
            info.setObjectId(String.valueOf(new Random().nextInt(1000000)));
            return info;
        } catch (ParseException e) {
            log.warn("Invalid date format for year: {}", year);
            throw new CarServiceException("Invalid date format: " + year, e);
        }
    }

}
