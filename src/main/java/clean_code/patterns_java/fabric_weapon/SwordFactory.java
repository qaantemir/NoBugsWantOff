package clean_code.patterns_java.fabric_weapon;

public class SwordFactory implements WeaponFactory {
    @Override
    public Weapon getWeapon() {
        return new Sword();
    }
}
