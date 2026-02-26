package ua.com.foxmined.carrestservice.service.modelcategoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxmined.carrestservice.dao.modelcategory.CarModelCategoryRepository;
import ua.com.foxmined.carrestservice.model.CarModelCategory;

import java.util.List;

@Service
public class CarModelCategoryServiceImpl implements CarModelCategoryService {

    @Autowired
    private CarModelCategoryRepository carModelCategoryRepository;

    @Override
    public CarModelCategory save(CarModelCategory carModelCategory) {
        return carModelCategoryRepository.save(carModelCategory);
    }

    @Override
    public void update(CarModelCategory carModelCategory) {
        carModelCategoryRepository.save(carModelCategory);
    }

    @Override
    public void delete(CarModelCategory carModelCategory) {
        carModelCategoryRepository.delete(carModelCategory);
    }

    @Override
    public Page<CarModelCategory> findAll(Pageable pageable) {
        return carModelCategoryRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        carModelCategoryRepository.deleteAll();
    }

}
