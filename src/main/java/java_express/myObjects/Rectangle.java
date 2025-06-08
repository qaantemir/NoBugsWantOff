package java_express.myObjects;

public class Rectangle {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        if ((width > 0) && (height > 0)) {
            this.width = width;
            this.height = height;
        }
        else {
            System.out.println("Нельзя определить отрицательным или нулевым значением");
        }
    }

    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}

    public void setWidth(int width) {this.width = width;}

    public void setHeight(int height) {this.height = height;}

    public int calculateArea() {
        return width * height;
    }
}
