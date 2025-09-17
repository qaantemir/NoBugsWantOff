package clean_code.patterns_java.adapter_miles2kilometers;

public class Mile implements Distance {
    private double value;

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getDistance() {
        return value;
    }
}
