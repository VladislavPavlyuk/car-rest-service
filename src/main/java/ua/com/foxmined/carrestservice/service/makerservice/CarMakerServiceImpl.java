package ua.com.foxmined.carrestservice.service.makerservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.maker.CarMakerRepository;
import ua.com.foxmined.carrestservice.model.CarMaker;

import java.util.List;

@Service
public class CarMakerServiceImpl implements CarMakerService{

    private final CarMakerRepository carMakerRepository;

    public CarMakerServiceImpl(CarMakerRepository carMakerRepository) {
        this.carMakerRepository = carMakerRepository;
    }

    @Override
    public CarMaker save(CarMaker carMaker) {
        return carMakerRepository.save(carMaker);
    }

    @Override
    public void update(CarMaker carMaker) {
        carMakerRepository.save(carMaker);
    }

    @Override
    public void delete(CarMaker carMaker) {
        carMakerRepository.delete(carMaker);
    }

    @Override
    public List<CarMaker> findByName(String name) {
        return carMakerRepository.findByNameLike(name);
    }

    @Override
    public Page<CarMaker> findAll(Pageable pageable) {
        return carMakerRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        carMakerRepository.deleteAll();
    }

    @Override
    public CarMaker addManufacturer(String manufacturer) {
        List<CarMaker> carMakers = findByName(manufacturer);
        if (carMakers.isEmpty()) {
            CarMaker addCarMaker = new CarMaker();
            addCarMaker.setName(manufacturer);
            return save(addCarMaker);
        }
        return carMakers.get(0);
    }

    @Override
    public void deleteManufacturer(String manufacturer) {
        List<CarMaker> carMakers = findByName(manufacturer);
        if (!carMakers.isEmpty()) {
            carMakerRepository.delete(carMakers.get(0));
        }
    }

    @Override
    public void updateManufacturer(String oldManufacturer, String newManufacturer) {
        List<CarMaker> carMakers = carMakerRepository.findByNameLike(oldManufacturer);
        if (!carMakers.isEmpty()) {
            CarMaker updateCarMaker = carMakers.get(0);
            updateCarMaker.setName(newManufacturer);
            carMakerRepository.save(updateCarMaker);
        }
    }

}
