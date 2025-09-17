package clean_code.patterns_java.abstract_fabric_furniture;

public class ClassicFurnitureFabric implements FurnitureFabric {

    @Override
    public Furniture getChair() {
        return new VintageChair();
    }

    @Override
    public Furniture getTable() {
        return new VintageTable();
    }
}
