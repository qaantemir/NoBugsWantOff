package java_express.exceptionsAndGenerics;

public class Pair <T, E> {
    private T t;
    private E e;

    public T getT() {
        return this.t;
    }

    public E getE() {
        return this.e;
    }

    public void setT(T t) {
        this.t = t;
    }

    public void setE(E e) {
        this.e = e;
    }

}
