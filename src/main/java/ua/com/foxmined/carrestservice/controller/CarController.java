package ua.com.foxmined.carrestservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.foxmined.carrestservice.dto.CarDto;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.model.CarInformation;
import ua.com.foxmined.carrestservice.service.informationservice.CarInformationService;
import ua.com.foxmined.carrestservice.service.summaryservice.CarSummaryService;
import ua.com.foxmined.carrestservice.utils.EndPoints;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = EndPoints.VERSION_1)
@SecurityRequirement(name = "bearerAuth")
public class CarController {

    private final CarInformationService carInformationService;
    private final CarSummaryService carSummaryService;

    public CarController(CarInformationService carInformationService, CarSummaryService carSummaryService) {
        this.carInformationService = carInformationService;
        this.carSummaryService = carSummaryService;
    }

    @RequestMapping(value = EndPoints.GET_CARS, method = RequestMethod.GET)
    public ResponseEntity<List<CarInformation>> getCars(@Valid CarDto sourceCarFilter) {
        List<CarInformation> carInformationResult = carInformationService.findByCarFilters(sourceCarFilter).toList();
        return new ResponseEntity<>(carInformationResult, HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.SET_MANUFACTURER_AND_MODEL, method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create:items')")
    public ResponseEntity<?> addManufacturerAndModel(@PathVariable(name = "manufacturer") String manufacturer,
                                             @PathVariable(name = "model") String model) {
        carSummaryService.addManufacturerAndModel(manufacturer, model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.SET_MANUFACTURER_AND_MODEL, method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('delete:items')")
    public ResponseEntity<?> deleteManufacturerAndModel(@PathVariable(name = "manufacturer") String manufacturer,
                                                     @PathVariable(name = "model") String model) throws EntityNotPresentException {
        carSummaryService.deleteManufacturerAndModel(manufacturer, model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = EndPoints.UPDATE_MODEL, method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('update:items')")
    public ResponseEntity<?> updateModel(@PathVariable(name = "manufacturer") String manufacturer,
                                        @PathVariable(name = "oldModel") String oldModel,
                                        @PathVariable(name = "newModel") String newModel) {
        carSummaryService.updateModelCurrentManufacturer(manufacturer, oldModel, newModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
