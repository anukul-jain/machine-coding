package service.impl;

import entity.Airline;
import entity.City;
import entity.Flight;
import enums.SearchParameter;
import repository.AirlineRepository;
import repository.CityRepository;
import response.RouteResponse;
import service.FlightService;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anukul Jain
 */
public class FlightServiceImpl implements FlightService {

    private static final Logger LOGGER = Logger.getLogger(FlightServiceImpl.class.getName());

    private final AirlineRepository airlineRepository;

    private final CityRepository cityRepository;

    //create a source to list of flight details map
    private Map<String, List<Flight>> sourceToFlightMap;

    public FlightServiceImpl(AirlineRepository airlineRepository, CityRepository cityRepository) {
        this.airlineRepository = airlineRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public Flight registerFlight(String airlineName, String sourceCityCode, String destinationCityCode, double price,
                                 List<String> complementaryServices) {
        Airline airline = airlineRepository.findByName(airlineName);
        City sourceCity = cityRepository.findById(sourceCityCode);
        City destinationCity = cityRepository.findById(destinationCityCode);
        Flight flight = new Flight(airline.getName(), sourceCity.getId(), destinationCity.getId(), price,
                                   new HashSet<>(complementaryServices));
        if (sourceToFlightMap == null) {
            sourceToFlightMap = new HashMap<>();
        }
        sourceToFlightMap.computeIfAbsent(sourceCityCode, value -> new ArrayList<>()).add(flight);
        LOGGER.log(Level.INFO, String.format("%s %s -> %s flight registered%n", flight.getAirline(), flight.getSourceCityCode(),
                                             flight.getDestinationCityCode()));
        return flight;
    }

    @Override
    public RouteResponse search(String sourceCityCode, String destinationCityCode, List<String> complementaryServices,
                                SearchParameter parameter) {
        if (!cityRepository.existsById(sourceCityCode)) {
            throw new IllegalArgumentException("Source city not found");
        }
        if (!cityRepository.existsById(destinationCityCode)) {
            throw new IllegalArgumentException("Destination city not found");
        }
        RouteResponse bestRoute = searchBestRoute(sourceCityCode, destinationCityCode, complementaryServices, parameter);
        LOGGER.log(Level.INFO, String.format("Route with %s :", parameter.getValue()));

        for (Flight flight : bestRoute.getFlights()) {
            LOGGER.log(Level.INFO, String.format("%s -> %s via %s for Rs.%s", flight.getSourceCityCode(), flight.getDestinationCityCode(),
                                                 flight.getAirline(), flight.getPrice()));
        }

        LOGGER.log(Level.INFO, String.format("Total Price: Rs.%s", bestRoute.getTotalPrice()));
        LOGGER.log(Level.INFO, String.format("Total Hops: %s%n%n", bestRoute.getFlights().size()));
        return bestRoute;
    }

    private RouteResponse searchBestRoute(String sourceCityCode, String destinationCityCode, List<String> complementaryServices,
                                          SearchParameter parameter) {

        Queue<RouteResponse> flightRoutes = new LinkedList<>();
        List<Flight> availableFlightsFromSource = sourceToFlightMap.get(sourceCityCode);
        availableFlightsFromSource.stream().filter(flight -> requestedComplementaryServicesPresent(complementaryServices, flight))
                                  .forEach(flight -> {
                                      RouteResponse route = new RouteResponse();
                                      route.setFlights(new ArrayList<>());
                                      route.getFlights().add(flight);
                                      route.setTotalPrice(flight.getPrice());
                                      flightRoutes.offer(route);
                                  });
        RouteResponse bestRoute = null;
        while (!flightRoutes.isEmpty()) {
            RouteResponse currentRoute = flightRoutes.poll();
            List<Flight> flights = currentRoute.getFlights();
            Flight nextFlight = flights.getLast();
            String lastCityReached = nextFlight.getDestinationCityCode();

            //if destination is reached via searchBestRoute
            if (lastCityReached.equals(destinationCityCode)) {
                if (bestRoute == null || isBetterRoute(bestRoute, currentRoute, parameter)) {
                    bestRoute = currentRoute;
                }
                //destination reached no need to proceed further to check flights
                continue;
            }
            addFlightsToRoute(lastCityReached, currentRoute, flightRoutes, complementaryServices);
        }
        return bestRoute;
    }

    private void addFlightsToRoute(String lastCityReached, RouteResponse currentRoute, Queue<RouteResponse> flightRoutes,
                                   List<String> complementaryServices) {
        for (Flight flight : sourceToFlightMap.getOrDefault(lastCityReached, new ArrayList<>())) {
            //To avoid cycles
            if (destinationAlreadyReached(flight.getDestinationCityCode(), currentRoute.getFlights()) ||
                !requestedComplementaryServicesPresent(complementaryServices, flight)) {
                continue;
            }

            //add new route with added flight to the end of queue
            RouteResponse newRoute = new RouteResponse();
            newRoute.setFlights(new ArrayList<>(currentRoute.getFlights()));
            newRoute.getFlights().add(flight);
            newRoute.setTotalPrice(currentRoute.getTotalPrice() + flight.getPrice());
            flightRoutes.offer(newRoute);
        }
    }

    private static boolean requestedComplementaryServicesPresent(List<String> complementaryServices, Flight flight) {
        return complementaryServices == null || complementaryServices.isEmpty() ||
               flight.getComplementaryServices().containsAll(complementaryServices);
    }

    private static boolean destinationAlreadyReached(String destinationCityCode, List<Flight> currentRouteFlights) {
        return currentRouteFlights.stream().anyMatch(fl -> fl.getSourceCityCode().equals(destinationCityCode));
    }

    private static boolean isBetterRoute(RouteResponse bestRoute, RouteResponse currentRoute, SearchParameter parameter) {
        return switch (parameter) {
            case MINIMUM_HOPS -> (currentRoute.getFlights().size() < bestRoute.getFlights().size()) ||
                                 (currentRoute.getFlights().size() == bestRoute.getFlights().size() &&
                                  currentRoute.getTotalPrice() < bestRoute.getTotalPrice());
            case MINIMUM_PRICE -> (currentRoute.getTotalPrice() < bestRoute.getTotalPrice()) ||
                                  (currentRoute.getTotalPrice() == bestRoute.getTotalPrice() &&
                                   currentRoute.getFlights().size() < bestRoute.getFlights().size());
        };
    }
}