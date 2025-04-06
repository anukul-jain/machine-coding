package service;

import entity.Flight;
import enums.SearchParameter;
import response.RouteResponse;

import java.util.List;

/**
 * @author Anukul Jain
 */
public interface FlightService {

    Flight registerFlight(String airlineName, String sourceCityCode, String destinationCityCode, double price,
                          List<String> complementaryServices);

    RouteResponse search(String sourceCityCode, String destinationCityCode, List<String> complementaryServices,
                           SearchParameter parameter);
}