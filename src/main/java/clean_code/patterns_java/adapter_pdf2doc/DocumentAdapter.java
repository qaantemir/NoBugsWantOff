package clean_code.patterns_java.adapter_pdf2doc;

public class DocumentAdapter {

    public static Doc convertToDoc(Pdf pdf) {
        return new Doc();
    }

    public static Pdf convertToPdf(Doc doc) {
        return new Pdf();
    }
}
