package clean_code.patterns_java.abstract_fabric_furniture;

public class ModernFurnitureFabric implements FurnitureFabric {

    @Override
    public Furniture getChair() {
        return new YouthChair();
    }

    @Override
    public Furniture getTable() {
        return new YouthTable();
    }
}
