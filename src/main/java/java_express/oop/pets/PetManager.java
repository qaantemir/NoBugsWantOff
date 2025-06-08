package java_express.oop.pets;

public class PetManager {
    private Pet pet;

    public void add(Pet pet) {
        this.pet = pet;
    }

    public void petActions() {
        pet.doAction();
        pet.feed();
    }
}
