package dto;

import java.util.List;

public class FlightDto {

    private final String airlineName;

    private final String sourceCityCode;

    private final String destinationCityCode;

    private final Double price;

    private final List<String> complementaryServices;

    public FlightDto(String airlineName, String sourceCityCode, String destinationCityCode, Double price,
                     List<String> complementaryServices) {
        this.airlineName = airlineName;
        this.sourceCityCode = sourceCityCode;
        this.destinationCityCode = destinationCityCode;
        this.price = price;
        this.complementaryServices = complementaryServices;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getSourceCityCode() {
        return sourceCityCode;
    }

    public String getDestinationCityCode() {
        return destinationCityCode;
    }

    public Double getPrice() {
        return price;
    }

    public List<String> getComplementaryServices() {
        return complementaryServices;
    }
}