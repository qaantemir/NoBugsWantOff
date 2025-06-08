package java_express.oop.park;

public class Park {
    private Attraction attraction;

    public void add(Attraction attraction) {
        this.attraction = attraction;
    }

    public void howToCare() {
        attraction.care();
    }
}
