package clean_code.patterns_java.fabric_weapon;

public class Main {
    public static void main(String[] args) {
        WeaponFactory pf = new PistolFactory();
        var bf = new BowFactory();
        var sf = new SwordFactory();

        if (bf.getWeapon() instanceof Bow) System.out.println("boooooooow");
        if (sf.getWeapon() instanceof Sword) System.out.println("swoooord");
        if (pf.getWeapon() instanceof Pistol) System.out.println("3.14 stol");
    }
}
