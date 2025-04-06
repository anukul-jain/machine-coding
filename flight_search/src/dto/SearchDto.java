package dto;

import enums.SearchParameter;

import java.util.List;

public class SearchDto {

    private final String sourceCityCode;

    private final String destinationCityCode;

    private final List<String> complementaryServices;

    private final SearchParameter searchParameter;

    public SearchDto(String sourceCityCode, String destinationCityCode, List<String> complementaryServices,
                     SearchParameter searchParameter) {
        this.sourceCityCode = sourceCityCode;
        this.destinationCityCode = destinationCityCode;
        this.searchParameter = searchParameter;
        this.complementaryServices = complementaryServices;
    }

    public String getSourceCityCode() {
        return sourceCityCode;
    }

    public String getDestinationCityCode() {
        return destinationCityCode;
    }

    public List<String> getComplementaryServices() {
        return complementaryServices;
    }

    public SearchParameter getSearchParameter() {
        return searchParameter;
    }
}