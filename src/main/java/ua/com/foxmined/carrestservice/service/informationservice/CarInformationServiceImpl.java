package ua.com.foxmined.carrestservice.service.informationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.information.CarInformationRepository;
import ua.com.foxmined.carrestservice.dto.CarDto;
import ua.com.foxmined.carrestservice.model.CarInformation;

import java.util.List;

@Service
public class CarInformationServiceImpl implements CarInformationService{

    @Autowired
    private CarInformationRepository carInformationRepository;

    @Override
    public CarInformation save(CarInformation carInformation) {
        return carInformationRepository.save(carInformation);
    }

    @Override
    public void update(CarInformation carInformation) {
        carInformationRepository.save(carInformation);
    }

    @Override
    public void delete(CarInformation carInformation) {
        carInformationRepository.delete(carInformation);
    }

    @Override
    public Page<CarInformation> findAll(Pageable pageable) {
        return carInformationRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        carInformationRepository.deleteAll();
    }

    @Override
    public List<CarInformation> findByCarMakerId(Long id) {
        return carInformationRepository.findByCarMakerId(id);
    }

    @Override
    public Page<CarInformation> findByCarCategoryId(Long id, Pageable pageable) {
        return carInformationRepository.findByCarCategoryId(id, pageable);
    }

    @Override
    public Page<CarInformation> findByCarFilters(CarDto carDto) {

        return carInformationRepository.findByCarFilters(carDto.getCategory(),
                carDto.getManufacturer(),
                carDto.getModel(),
                carDto.getMinYear(),
                carDto.getMaxYear(),
                PageRequest.of(carDto.getPage(), carDto.getPageSize()));
    }
}
