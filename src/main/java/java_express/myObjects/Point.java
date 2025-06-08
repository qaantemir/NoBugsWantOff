package java_express.myObjects;

public class Point {
    private int x;
    private int y;

    public Point(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void print() {
        System.out.println("x: " + x + ", y: " + y);
    }
}
