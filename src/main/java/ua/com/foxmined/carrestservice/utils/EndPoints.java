package ua.com.foxmined.carrestservice.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EndPoints {

    static public final String VERSION_1 = "/api/v1";
    static public final String GET_CARS = "/cars";
    static public final String GET_CATEGORIES = "/categories";
    static public final String GET_MANUFACTURERS = "/manufacturers";
    static public final String SET_MANUFACTURER = "/manufacturers/{manufacturer}";
    static public final String SET_MANUFACTURER_AND_MODEL = "/manufacturers/{manufacturer}/models/{model}";
    static public final String UPDATE_MANUFACTURER = "/manufacturers/{oldManufacturer}/{newManufacturer}";
    static public final String UPDATE_MODEL = "/manufacturers/{manufacturer}/models/{oldModel}/{newModel}";
    static public final String SET_CATEGORY = "/categories/{category}";
    static public final String UPDATE_CATEGORY = "/categories/{oldCategory}/{newCategory}";
    static public final String SET_MANUFACTURER_AND_MODEL_AND_YEAR = "/manufacturers/{manufacturer}/models/{model}/{year}";


    static public List<String> getEndpointForAllUsers() {
        List<String> endpoints = new ArrayList<>();
        endpoints.add(VERSION_1+GET_CARS);
        endpoints.add(VERSION_1+GET_CATEGORIES);
        endpoints.add(VERSION_1+GET_MANUFACTURERS);
        return endpoints;
    }

    static public List<String> getEndpointForAuthorizedUsers() {
        List<String> endpoints = new ArrayList<>();
        endpoints.add(VERSION_1+SET_MANUFACTURER);
        endpoints.add(VERSION_1+SET_MANUFACTURER_AND_MODEL);
        endpoints.add(VERSION_1+UPDATE_MANUFACTURER);
        endpoints.add(VERSION_1+UPDATE_MODEL);
        endpoints.add(VERSION_1+SET_CATEGORY);
        endpoints.add(VERSION_1+UPDATE_CATEGORY);
        endpoints.add(VERSION_1+SET_MANUFACTURER_AND_MODEL_AND_YEAR);
        return endpoints;
    }

}
