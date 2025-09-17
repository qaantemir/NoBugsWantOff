package clean_code.patterns_java.adapter_pdf2doc;

public class PdfProcessor implements DocumentProcessor {
    @Override
    public void process(Document document) {
        System.out.println("pdf processor");
    }
}
