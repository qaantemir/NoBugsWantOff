package clean_code.patterns_java.adapter_miles2kilometers;

public class Main {
    public static void main(String[] args) {

        Mile m1 = new Mile();
        m1.setValue(100);

        Kilometer k1 = DistanceAdapter.convertToKilometer(m1);


        Kilometer k2 = new Kilometer();
        k2.setValue(160.9344);

        Mile m2 = DistanceAdapter.convertToMile(k2);

        System.out.println(m1.getDistance());
        System.out.println(k1.getDistance());
        System.out.println("-----------------------");
        System.out.println(m2.getDistance());
        System.out.println(k2.getDistance());

    }
}
