package ua.com.foxmined.carrestservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class CarDto {

    private String manufacturer;
    private Long minYear;
    private Long maxYear;
    private String category;
    private String model;

    @Min(0)
    private Integer page = 0;

    @Min(1)
    @Max(100)
    private Integer pageSize = 10;

}
