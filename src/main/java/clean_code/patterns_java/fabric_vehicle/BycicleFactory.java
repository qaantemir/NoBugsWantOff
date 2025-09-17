package clean_code.patterns_java.fabric_vehicle;

public class BycicleFactory implements VehicleFactory {
    @Override
    public Vehicle getVehicle() {
        return new Bycicle();
    }
}
