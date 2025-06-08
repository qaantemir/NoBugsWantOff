package java_express.oop.museum;

public class Main {
    public static void main(String[] args) {
        MuseumManager museumManager = new MuseumManager();

        museumManager.add(new Manuscript());
        museumManager.howToCare();
    }
}
