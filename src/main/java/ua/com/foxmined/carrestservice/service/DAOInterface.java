package ua.com.foxmined.carrestservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DAOInterface<T> {

    T save(T entity) ;

    void update(T entity) ;

    void delete(T entity) ;

    Page<T> findAll(Pageable pageable) ;

    void deleteAll() ;

}
