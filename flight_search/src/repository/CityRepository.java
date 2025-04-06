package repository;

import entity.City;

import java.util.Map;

/**
 * @author Anukul Jain
 */
public class CityRepository {

    private final Map<String, City> codeToCityMap;

    public CityRepository(Map<String, City> codeToCityMap) {
        this.codeToCityMap = codeToCityMap;
    }

    public City findById(String cityCode) {
        return codeToCityMap.get(cityCode);
    }

    public boolean existsById(String cityCode) {
        return codeToCityMap.containsKey(cityCode);
    }
}