package clean_code.patterns_java.adapter_miles2kilometers;

public class DistanceAdapter {

    public static Kilometer convertToKilometer(Mile mile) {
        var kilometer = new Kilometer();
        kilometer.setValue(mile.getDistance() * 1.609344);
        return  kilometer;
    }

    public static Mile convertToMile(Kilometer kilometer) {
        var mile = new Mile();
        mile.setValue(kilometer.getDistance() / 1.609344);
        return mile;
    }
}
