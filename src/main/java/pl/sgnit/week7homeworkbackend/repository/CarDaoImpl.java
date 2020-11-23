package pl.sgnit.week7homeworkbackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import pl.sgnit.week7homeworkbackend.model.Car;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CarDaoImpl implements CarDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Car car) {
        String sql = "insert into cars (mark, model, color, production_year) values (?, ?, ?, ?)";

        jdbcTemplate.update(sql, car.getMark(), car.getModel(), car.getColor(), car.getProductionYear());
    }

    @Override
    public List<Car> findAll() {
        String sql = "select * from cars";
        List<Map<String, Object>> cars = jdbcTemplate.queryForList(sql);

        return createCarList(cars);
    }

    @Override
    public Optional<Car> findById(long id) {
        String sql = "select * from cars where id=?";
        Car car = jdbcTemplate.queryForObject(sql, new RowMapper<Car>() {
            @Override
            public Car mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Car(resultSet.getLong("id"),
                        resultSet.getString("mark"),
                        resultSet.getString("model"),
                        resultSet.getString("color"),
                        resultSet.getInt("production_year"));
            }
        }, id);
        return Optional.of(car);
    }

    @Override
    public void update(Car car) {
        String sql = "update cars set mark=?, model=?, color=?, production_year=? where id=?";

        jdbcTemplate.update(sql, car.getMark(), car.getModel(), car.getColor(), car.getProductionYear(),
                car.getId());
    }

    @Override
    public void delete(long id) {
        String sql = "delete from cars where id=?";

        jdbcTemplate.update(sql, id);
    }

    public List<Car> getCarListFilteredByYear(int yearFrom, int yearTo) {
        String sql = "select * from cars where production_year between ? and ?";
        List<Map<String, Object>> cars = jdbcTemplate.queryForList(sql, yearFrom, yearTo);

        return createCarList(cars);
    }

    private List<Car> createCarList(List<Map<String, Object>> cars) {
        List<Car> carList = new ArrayList<>();

        cars.stream().forEach(car -> carList.add(new Car(
                (long) car.get("id"),
                (String) car.get("mark"),
                (String) car.get("model"),
                (String) car.get("color"),
                (int) car.get("production_year")
        )));
        return carList;
    }
}
