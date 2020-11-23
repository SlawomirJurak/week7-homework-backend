package pl.sgnit.week7homeworkbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sgnit.week7homeworkbackend.model.Car;
import pl.sgnit.week7homeworkbackend.repository.CarDao;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
@CrossOrigin
public class CarController {

    private final CarDao carDao;

    @Autowired
    public CarController(CarDao carDao) {
        this.carDao = carDao;
    }

    @GetMapping
    public List<Car> getCars() {
        return carDao.findAll();
    }

    @GetMapping("/{id}")
    public Car getCar(@PathVariable Long id) {
        Optional<Car> car = carDao.findById(id);
        if (car.isEmpty()) {
            return new Car();
        }
        return car.get();
    }

    @PostMapping
    public void addCar(@RequestBody Car newCar) {
        carDao.save(newCar);
    }

    @PutMapping
    public void updateCar(@RequestBody Car car) {
        carDao.update(car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carDao.delete(id);
    }

    @GetMapping("/{yearFrom}/{yearTo}")
    public List<Car> getCarListFilteredByYear(@PathVariable int yearFrom, @PathVariable int yearTo) {
        return carDao.getCarListFilteredByYear(yearFrom, yearTo);
    }
}
