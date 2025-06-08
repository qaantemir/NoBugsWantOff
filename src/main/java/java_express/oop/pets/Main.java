package java_express.oop.pets;

public class Main {

    public static void main(String[] args) {
        PetManager petManager = new PetManager();

        petManager.add(new Cat());

        petManager.petActions();
    }
}
