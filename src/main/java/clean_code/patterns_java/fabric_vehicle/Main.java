package clean_code.patterns_java.fabric_vehicle;

public class Main {


    public static void main(String[] args) {
        Bycicle bycicle = (Bycicle) new BycicleFactory().getVehicle();
        if (bycicle instanceof Bycicle)
            System.out.println("bycicle instanceof Bycicle == true");
        else
            System.out.println("bycicle instanceof Bycicle == false");

        Car car = (Car) new CarFactory().getVehicle();
        if (car instanceof Car)
            System.out.println("car instanceof Car == true");
        else
            System.out.println("car instanceof Car == false");
    }

}
