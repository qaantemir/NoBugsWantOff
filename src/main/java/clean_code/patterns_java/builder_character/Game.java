package clean_code.patterns_java.builder_character;

public class Game {
    public static void main(String[] args) {
        Character ch = new Character.Builder()
                .health(100.)
                .damage(20.)
                .armor(40.)
                .mana(10.)
                .build();

        if (ch instanceof Character) {
            System.out.println(ch.getHealth());
            System.out.println(ch.getArmor());
            System.out.println(ch.getDamage());
            System.out.println(ch.getMana());
        }

    }
}
