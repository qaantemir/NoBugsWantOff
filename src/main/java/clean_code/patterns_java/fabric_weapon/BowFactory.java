package clean_code.patterns_java.fabric_weapon;

public class BowFactory implements WeaponFactory {
    @Override
    public Weapon getWeapon() {
        return new Bow();
    }
}
