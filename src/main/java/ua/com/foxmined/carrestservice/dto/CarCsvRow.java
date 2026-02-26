package ua.com.foxmined.carrestservice.dto;

import java.util.List;

public record CarCsvRow(String objectId, String manufacturer, String year, String model, List<String> categoryNames) {
}
