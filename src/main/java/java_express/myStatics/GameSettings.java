package java_express.myStatics;

public class GameSettings {
    static int maxPlayers = 10;
    final String gameName;
    int currentPlayers;

    public GameSettings(String gameName, int currentPlayers) {
        this.gameName = gameName;
        this.currentPlayers = currentPlayers;
    }

    public static void setMaxPlayers(int n) { GameSettings.maxPlayers = n;}

    public void addPlayer() {
        if (GameSettings.maxPlayers > this.currentPlayers) this.currentPlayers++;
    }

    @Override
    public String toString() {
        return "GameSettings{" +
                "max players=" + GameSettings.maxPlayers +
                "gameName='" + gameName + '\'' +
                ", currentPlayers=" + currentPlayers +
                '}';
    }

    public void printGameStatus() {
        System.out.println(this.toString());
    }
}
