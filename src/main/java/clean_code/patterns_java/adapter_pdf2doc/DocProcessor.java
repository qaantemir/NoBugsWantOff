package clean_code.patterns_java.adapter_pdf2doc;

public class DocProcessor implements DocumentProcessor {
    @Override
    public void process(Document document) {
        System.out.println("doc processor");
    }
}
