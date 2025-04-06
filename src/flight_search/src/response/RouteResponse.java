package response;

import entity.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anukul Jain
 */
public class RouteResponse {

    private List<Flight> flights;

    private double totalPrice;

    public RouteResponse(double totalPrice, List<Flight> flights) {
        this.totalPrice = totalPrice;
        this.flights = flights;
    }

    public RouteResponse() {
        this.flights = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}