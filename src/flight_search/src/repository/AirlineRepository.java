package repository;

import entity.Airline;

import java.util.Map;

/**
 * @author Anukul Jain
 */
public class AirlineRepository {

    private final Map<String, Airline> nameToAirlineMap;

    public AirlineRepository(Map<String, Airline> nameToAirlineMap) {this.nameToAirlineMap = nameToAirlineMap;}

    public Airline findByName(String airlineName) {
        return nameToAirlineMap.get(airlineName);
    }
}