package clean_code.patterns_java.fabric_weapon;

public class PistolFactory implements WeaponFactory {
    @Override
    public Weapon getWeapon() {
        return new Pistol();
    }
}
