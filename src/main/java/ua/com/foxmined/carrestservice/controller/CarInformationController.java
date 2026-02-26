package ua.com.foxmined.carrestservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.service.summaryservice.CarSummaryService;
import ua.com.foxmined.carrestservice.utils.EndPoints;

@RestController
@RequestMapping(value = EndPoints.VERSION_1)
@SecurityRequirement(name = "bearerAuth")
public class CarInformationController {

    @Autowired
    private CarSummaryService carSummaryService;

    @RequestMapping(value = EndPoints.SET_MANUFACTURER_AND_MODEL_AND_YEAR, method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create:items')")
    public ResponseEntity<?> addManufacturerAndModelAndYear(@PathVariable(name = "manufacturer") String manufacturer,
                                                           @PathVariable(name = "model") String model,
                                                           @PathVariable(name = "year") String year) throws EntityNotPresentException {
        carSummaryService.addManufacturerAndModelAndYear(manufacturer, model, year);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
