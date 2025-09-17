package clean_code.patterns_java.builder_character;

public class Character {
    private double health;
    private double damage;
    private double armor;
    private double mana;

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public double getArmor() {
        return armor;
    }

    public double getMana() {
        return mana;
    }

    public Character(double health, double damage, double armor, double mana) {
        this.health = health;
        this.damage = damage;
        this.armor = armor;
        this.mana = mana;
    }

    static class Builder {
        private double health;
        private double damage;
        private double armor;
        private double mana;

        public Builder health(double health) {
            this.health = health;
            return this;
        }

        public Builder damage(double damage) {
            this.damage = damage;
            return this;
        }

        public Builder armor(double armor) {
            this.armor = armor;
            return this;
        }

        public Builder mana(double mana) {
            this.mana = mana;
            return this;
        }

        public Character build() {
            return new Character(
                    this.health,
                    this.damage,
                    this.armor,
                    this.mana
            );
        }
    }
}
