package ua.com.foxmined.carrestservice.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dto.CarCsvRow;
import ua.com.foxmined.carrestservice.exception.CarServiceException;
import ua.com.foxmined.carrestservice.model.CarCategory;
import ua.com.foxmined.carrestservice.model.CarInformation;
import ua.com.foxmined.carrestservice.model.CarModel;
import ua.com.foxmined.carrestservice.model.CarModelCategory;
import ua.com.foxmined.carrestservice.service.categoryservice.CarCategoryService;
import ua.com.foxmined.carrestservice.service.informationservice.CarInformationService;
import ua.com.foxmined.carrestservice.service.modelcategoryservice.CarModelCategoryService;
import ua.com.foxmined.carrestservice.service.summaryservice.CarSummaryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Orchestrates database initialization from CSV car data.
 */
@Log4j2
@Service
public class CarDBInitializer {

    private final String carsource;
    private final CarCategoryService carCategoryService;
    private final CarInformationService carInformationService;
    private final CarModelCategoryService carModelCategoryService;
    private final CarCsvParser carCsvParser;
    private final TxtFileReader txtFileReader;
    private final CarSummaryService carSummaryService;

    public CarDBInitializer(@Value("${data.carsource}") String carsource,
                           CarCategoryService carCategoryService,
                           CarInformationService carInformationService,
                           CarModelCategoryService carModelCategoryService,
                           CarCsvParser carCsvParser,
                           TxtFileReader txtFileReader,
                           CarSummaryService carSummaryService) {
        this.carsource = carsource;
        this.carCategoryService = carCategoryService;
        this.carInformationService = carInformationService;
        this.carModelCategoryService = carModelCategoryService;
        this.carCsvParser = carCsvParser;
        this.txtFileReader = txtFileReader;
        this.carSummaryService = carSummaryService;
    }

    public void createRowsInDb() {
        try {
            if (isDbAlreadyPopulated()) {
                log.info("DB initialize complete");
                return;
            }
            List<String> carRows = loadAndSkipHeader();
            for (String line : carRows) {
                carCsvParser.parseLine(line).ifPresent(this::persistRow);
            }
            log.info("DB initialize complete");
        } catch (CarServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("DB initialization failed: {}", e.getMessage());
            throw new CarServiceException("Database initialization failed", e);
        }
    }

    private boolean isDbAlreadyPopulated() {
        Page<CarInformation> page = carInformationService.findAll(PageRequest.of(0, 10));
        return !page.isEmpty();
    }

    private List<String> loadAndSkipHeader() {
        try {
            List<String> lines = txtFileReader.readFile(carsource);
            return carCsvParser.skipHeader(lines);
        } catch (CarServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error loading car data: {}", e.getMessage());
            throw new CarServiceException("Failed to load car data file", e);
        }
    }

    private void persistRow(CarCsvRow row) {
        try {
            CarModel carModel = carSummaryService.addManufacturerAndModel(row.manufacturer(), row.model());
            List<CarCategory> categories = resolveCategories(row.categoryNames());
            saveCategoriesToModel(carModel, categories);
            saveCarInformation(carModel, row.year(), row.objectId());
        } catch (Exception e) {
            log.warn("Failed to persist row {}: {}", row.objectId(), e.getMessage());
            throw new CarServiceException("Failed to persist car row", e);
        }
    }

    private List<CarCategory> resolveCategories(List<String> categoryNames) {
        List<CarCategory> result = new ArrayList<>();
        for (String name : categoryNames) {
            result.add(findOrCreateCategory(name));
        }
        return result;
    }

    private CarCategory findOrCreateCategory(String categoryName) {
        List<CarCategory> existing = carCategoryService.findByName(categoryName);
        if (!existing.isEmpty()) {
            return existing.get(0);
        }
        CarCategory newCategory = new CarCategory();
        newCategory.setName(categoryName);
        return carCategoryService.save(newCategory);
    }

    private void saveCarInformation(CarModel carModel, String year, String objectId) {
        CarInformation carInformation = buildCarInformation(carModel, year, objectId);
        carInformationService.save(carInformation);
    }

    private CarInformation buildCarInformation(CarModel carModel, String year, String objectId) {
        try {
            CarInformation info = new CarInformation();
            info.setCarModel(carModel);
            info.setDateOfManifacture(new SimpleDateFormat("yyyy").parse(year));
            info.setObjectId(objectId);
            return info;
        } catch (ParseException e) {
            log.warn("Invalid date format for year: {}", year);
            throw new CarServiceException("Invalid date format: " + year, e);
        }
    }

    private void saveCategoriesToModel(CarModel carModel, List<CarCategory> categories) {
        categories.forEach(category -> saveCategoryLink(carModel, category));
    }

    private void saveCategoryLink(CarModel carModel, CarCategory category) {
        CarModelCategory link = new CarModelCategory();
        link.setCarModel(carModel);
        link.setCarCategory(category);
        carModelCategoryService.save(link);
    }
}
