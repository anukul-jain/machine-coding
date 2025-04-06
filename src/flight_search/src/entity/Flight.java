package entity;

import java.util.List;
import java.util.Set;

/**
 * @author Anukul Jain
 */
public class Flight {

    private String airline;

    private String sourceCityCode;

    private String destinationCityCode;

    private Double price;

    private Set<String> complementaryServices;

    public Flight(String airline, String sourceCityCode, String destinationCityCode, Double price, Set<String> complementaryServices) {
        this.airline = airline;
        this.sourceCityCode = sourceCityCode;
        this.destinationCityCode = destinationCityCode;
        this.price = price;
        this.complementaryServices = complementaryServices;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getSourceCityCode() {
        return sourceCityCode;
    }

    public void setSourceCityCode(String sourceCityCode) {
        this.sourceCityCode = sourceCityCode;
    }

    public String getDestinationCityCode() {
        return destinationCityCode;
    }

    public void setDestinationCityCode(String destinationCityCode) {
        this.destinationCityCode = destinationCityCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<String> getComplementaryServices() {
        return complementaryServices;
    }

    public void setComplementaryServices(Set<String> complementaryServices) {
        this.complementaryServices = complementaryServices;
    }
}