package enums;

/**
 * @author Anukul Jain
 */
public enum SearchParameter {

    MINIMUM_HOPS("Minimum Hops"),
    MINIMUM_PRICE("Minimum Price");

    private final String value;

    SearchParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}