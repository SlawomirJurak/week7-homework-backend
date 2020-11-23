package pl.sgnit.week7homeworkbackend.repository;

import pl.sgnit.week7homeworkbackend.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarDao {

    void save(Car car);

    List<Car> findAll();

    Optional<Car> findById(long id);

    void update(Car car);

    void delete(long id);

    List<Car> getCarListFilteredByYear(int yearFrom, int yearTo);
}
