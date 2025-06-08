package java_express.oop.farm;

public class Farm {
    private FarmAnimal farmAnimal;

    public void add(FarmAnimal farmAnimal) {
        this.farmAnimal = farmAnimal;
    }

    public void showActions() {
        farmAnimal.feed();
        farmAnimal.care();
        farmAnimal.function();
    }
}
