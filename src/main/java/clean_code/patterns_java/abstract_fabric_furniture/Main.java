package clean_code.patterns_java.abstract_fabric_furniture;

public class Main {
    public static void main(String[] args) {
        FurnitureFabric mf = new ModernFurnitureFabric();
        FurnitureFabric cf = new ClassicFurnitureFabric();

        Furniture classicChair = cf.getChair();
        Furniture modernChair = mf.getChair();

        if (classicChair instanceof VintageChair) System.out.println("claccis ok");
        if (modernChair instanceof YouthChair) System.out.println("modern ok");
    }
}
