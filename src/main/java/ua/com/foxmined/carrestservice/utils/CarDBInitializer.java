package ua.com.foxmined.carrestservice.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.exception.FileException;
import ua.com.foxmined.carrestservice.model.*;
import ua.com.foxmined.carrestservice.service.categoryservice.CarCategoryService;
import ua.com.foxmined.carrestservice.service.informationservice.CarInformationService;
import ua.com.foxmined.carrestservice.service.modelcategoryservice.CarModelCategoryService;
import ua.com.foxmined.carrestservice.service.summaryservice.CarSummaryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CarDBInitializer {

    @Value("${data.carsource}")
    private String carsource;

    @Autowired
    private CarCategoryService carCategoryService;

    @Autowired
    private CarInformationService carInformationService;

    @Autowired
    private CarModelCategoryService carModelCategoryService;

    @Autowired
    private TxtFileReader txtFileReader;

    @Autowired
    private CarSummaryService carSummaryService;

    public void createRowsInDb() {

        if (testRowsInDB()) {
            log.info("DB initialize complete");
            return;
        }

        List<String> carRows = getListOfFilesEntries();
        for (var currentString : carRows) {
            String fragments[] = currentString.split(",");
            if (fragments.length < 5) {
                continue;
            }
            CarModel addCarModel = carSummaryService.addManufacturerAndModel(fragments[1],fragments[3]);
            List<CarCategory> addCategories = getListOfCategories(currentString);
            saveCategoriesToModel(addCarModel, addCategories);
            saveCarInformation(addCarModel,fragments[2],fragments[0]);
        }
        log.info("DB initialize complete");
    }

    private boolean testRowsInDB() {
        Page<CarInformation> testCarInformation = carInformationService.findAll(PageRequest.of(0, 10));
        if (testCarInformation.toList().size() != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    private List<CarCategory> getListOfCategories(String currentFileEntry) {
        String fragments[] = currentFileEntry.split(",");
        List<CarCategory> addCategories;

        if (fragments.length == 5) {
            addCategories = createListCategory(fragments[4]);
        } else {
            String fragmentsCategory[] = currentFileEntry.split("\"");
            String categoryString = fragmentsCategory[1];
            addCategories = createListCategory(categoryString);
        }

        return addCategories;
    }

    private void saveCarInformation(CarModel addCarModel, String year, String objectId) {
        CarInformation addCarInformation = new CarInformation();
        addCarInformation.setCarModel(addCarModel);
        try {
            addCarInformation.setDateOfManifacture(new SimpleDateFormat("yyyy").parse(year));
        } catch (ParseException e) {
            log.debug("invalid Date format");
        }
        addCarInformation.setObjectId(objectId);
        carInformationService.save(addCarInformation);
    }

    private void saveCategoriesToModel(CarModel carModel, List<CarCategory> categories) {
        for (var currentCategory : categories) {
            CarModelCategory addCategoryToModel = new CarModelCategory();
            addCategoryToModel.setCarModel(carModel);
            addCategoryToModel.setCarCategory(currentCategory);
            carModelCategoryService.save(addCategoryToModel);
        }
    }

    private List<String> getListOfFilesEntries() {
        List<String> carRows = new ArrayList<>();
        try {
            carRows = txtFileReader.readFile(carsource);
        } catch (FileException e) {
            log.error("Error open file");
            return carRows;
        }
        if (carRows.size() != 0) {
            carRows.remove(0);
        }
        return carRows;
    }

    private List<CarCategory> createListCategory(String categories) {

        String fragments[] = categories.split(",");

        List<CarCategory> returnCategories = new ArrayList<>();

        for (var currentCategory : fragments) {
            currentCategory = currentCategory.trim();
            CarCategory findCategory = receiveCategory(currentCategory);
            returnCategories.add(findCategory);
        }
        return returnCategories;
    }

    private CarCategory receiveCategory(String categoryName) {

        List<CarCategory> findCategories = carCategoryService.findByName(categoryName);

        if (findCategories.size() == 0) {
            CarCategory newCarCategory = new CarCategory();
            newCarCategory.setName(categoryName);
            return carCategoryService.save(newCarCategory);
        } else {
            Optional<CarCategory> addCarCategory = Optional.ofNullable(findCategories.get(0));
            if (addCarCategory.isPresent()) {
                return addCarCategory.get();
            } else {
                CarCategory newCarCategory = new CarCategory();
                newCarCategory.setName(categoryName);
                return carCategoryService.save(newCarCategory);
            }
        }
    }

}
