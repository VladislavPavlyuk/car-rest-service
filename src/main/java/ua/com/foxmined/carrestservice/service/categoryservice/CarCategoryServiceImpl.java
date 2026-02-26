package ua.com.foxmined.carrestservice.service.categoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.dao.category.CarCategoryRepository;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.model.CarCategory;

import java.util.List;
import java.util.Optional;


@Service
public class CarCategoryServiceImpl implements CarCategoryService{

    @Autowired
    private CarCategoryRepository carCategoryRepository;

    @Override
    public CarCategory save(CarCategory carCategory) {
        return carCategoryRepository.save(carCategory);
    }

    @Override
    public void update(CarCategory carCategory) {
        carCategoryRepository.save(carCategory);
    }

    @Override
    public void delete(CarCategory carCategory) {
        carCategoryRepository.delete(carCategory);
    }

    @Override
    public List<CarCategory> findByName(String name) {
        return carCategoryRepository.findByNameLike(name);
    }

    @Override
    public Page<CarCategory> findAll(Pageable pageable) {
        return carCategoryRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        carCategoryRepository.deleteAll();
    }

    @Override
    @Transactional
    public CarCategory addCategory(String category) {

        CarCategory findCarCategory = carCategoryRepository.findByName(category)
                .orElseGet(() -> {
                    CarCategory addCarCategory = new CarCategory();
                    addCarCategory.setName(category);
                    return addCarCategory;});
        return carCategoryRepository.save(findCarCategory);
    }

    @Override
    @Transactional
    public CarCategory updateCategory(String oldCategory, String newCategory) throws EntityNotPresentException {
        Optional<CarCategory> findCarCategory = carCategoryRepository.findByName(oldCategory)
                .map(existed -> { existed.setName(newCategory);
                    return existed;
                });
        if (findCarCategory.isPresent()) {
            return carCategoryRepository.save(findCarCategory.get());
        }
        else {
            throw new EntityNotPresentException("Entity not present");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(String category) throws EntityNotPresentException {

        Optional<CarCategory> findCarCategory = carCategoryRepository.findByName(category);
        if (findCarCategory.isPresent()) {
            carCategoryRepository.delete(findCarCategory.get());
        }
        else {
            throw new EntityNotPresentException("Entity not present");
        }
    }

}
