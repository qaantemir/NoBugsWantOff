package clean_code.patterns_java.fabric_vehicle;

public class CarFactory implements VehicleFactory {

    @Override
    public Vehicle getVehicle() {
        return new Car();
    }
}
