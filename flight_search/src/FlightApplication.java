import dto.FlightDto;
import dto.SearchDto;
import entity.Airline;
import entity.City;
import enums.SearchParameter;
import repository.AirlineRepository;
import repository.CityRepository;
import service.FlightService;
import service.impl.FlightServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Anukul Jain
 */
public class FlightApplication {

    private static final String DELHI = "DEL";

    private static final String BANGALORE = "BLR";

    private static final String LONDON = "LON";

    private static final String NEW_YORK = "NYC";

    private static final String PARIS = "PAR";

    private static final String JET_AIR = "JetAir";

    private static final String INDI_GO = "IndiGo";

    private static final String DELTA = "Delta";

    public static void main(String[] args) {

        //declare cities to be included in application
        CityRepository cityRepository = createCities();

        //declare airlines to be included in application
        AirlineRepository airlineRepository = createAirline();

        FlightService flightService = new FlightServiceImpl(airlineRepository, cityRepository);

        registerFlights(flightService);

        searchFlights(flightService);
    }

    private static void searchFlights(FlightService flightService) {
        List<SearchDto> searches = new ArrayList<>();
        searches.add(new SearchDto(DELHI, NEW_YORK, Collections.emptyList(), SearchParameter.MINIMUM_HOPS));
        searches.add(new SearchDto(DELHI, NEW_YORK, Collections.emptyList(), SearchParameter.MINIMUM_PRICE));
        searches.add(new SearchDto(DELHI, NEW_YORK, Collections.singletonList("meal"), SearchParameter.MINIMUM_HOPS));
        searches.add(new SearchDto(DELHI, NEW_YORK, Collections.singletonList("meal"), SearchParameter.MINIMUM_PRICE));

        searches.forEach(
            search -> flightService.search(search.getSourceCityCode(), search.getDestinationCityCode(), search.getComplementaryServices(),
                                           search.getSearchParameter()));
    }

    private static void registerFlights(FlightService flightService) {
        List<FlightDto> flights = new ArrayList<>();
        flights.add(new FlightDto(JET_AIR, DELHI, BANGALORE, 500d, Collections.emptyList()));
        flights.add(new FlightDto(JET_AIR, BANGALORE, LONDON, 1000d, Collections.emptyList()));
        flights.add(new FlightDto(DELTA, DELHI, LONDON, 2000d, Collections.emptyList()));
        flights.add(new FlightDto(DELTA, LONDON, NEW_YORK, 2000d, Collections.emptyList()));

        List<String> indigoComplementaryServices = new ArrayList<>();
        indigoComplementaryServices.add("meal");
        flights.add(new FlightDto(INDI_GO, LONDON, NEW_YORK, 2500d, indigoComplementaryServices));
        flights.add(new FlightDto(INDI_GO, DELHI, BANGALORE, 600d, indigoComplementaryServices));
        flights.add(new FlightDto(INDI_GO, BANGALORE, PARIS, 800d, indigoComplementaryServices));
        flights.add(new FlightDto(INDI_GO, PARIS, LONDON, 300d, indigoComplementaryServices));

        flights.forEach(flightDto -> flightService.registerFlight(flightDto.getAirlineName(), flightDto.getSourceCityCode(),
                                                                  flightDto.getDestinationCityCode(), flightDto.getPrice(),
                                                                  flightDto.getComplementaryServices()));
    }

    private static AirlineRepository createAirline() {
        List<Airline> airlines = new ArrayList<>();

        airlines.add(new Airline(1, JET_AIR));
        airlines.add(new Airline(1, DELTA));
        airlines.add(new Airline(1, INDI_GO));
        Map<String, Airline> nameToAirlineMap = airlines.stream().collect(Collectors.toMap(Airline::getName, Function.identity()));

        return new AirlineRepository(nameToAirlineMap);
    }

    private static CityRepository createCities() {
        List<City> cities = new ArrayList<>();
        cities.add(new City(DELHI, "Delhi"));
        cities.add(new City(BANGALORE, "Bangalore"));
        cities.add(new City(LONDON, "London"));
        cities.add(new City(NEW_YORK, "New York"));
        cities.add(new City(PARIS, "Paris"));
        Map<String, City> cityCodeToCityMap = cities.stream().collect(Collectors.toMap(City::getId, Function.identity()));
        return new CityRepository(cityCodeToCityMap);
    }

}