package ua.com.foxmined.carrestservice.service.categoryservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxmined.carrestservice.dao.category.CarCategoryRepository;
import ua.com.foxmined.carrestservice.exception.EntityNotPresentException;
import ua.com.foxmined.carrestservice.model.CarCategory;

import java.util.List;


@Service
public class CarCategoryServiceImpl implements CarCategoryService{

    private final CarCategoryRepository carCategoryRepository;

    public CarCategoryServiceImpl(CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

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
        CarCategory carCategory = carCategoryRepository.findByName(category)
                .orElseGet(() -> createNewCategory(category));
        return carCategoryRepository.save(carCategory);
    }

    private CarCategory createNewCategory(String name) {
        CarCategory category = new CarCategory();
        category.setName(name);
        return category;
    }

    @Override
    @Transactional
    public CarCategory updateCategory(String oldCategory, String newCategory) throws EntityNotPresentException {
        CarCategory existing = findCategoryOrThrow(oldCategory);
        existing.setName(newCategory);
        return carCategoryRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteCategory(String category) throws EntityNotPresentException {
        CarCategory existing = findCategoryOrThrow(category);
        carCategoryRepository.delete(existing);
    }

    private CarCategory findCategoryOrThrow(String name) throws EntityNotPresentException {
        return carCategoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotPresentException("Entity not present"));
    }

}
