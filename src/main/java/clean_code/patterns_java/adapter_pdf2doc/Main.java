package clean_code.patterns_java.adapter_pdf2doc;

public class Main {
    public static void main(String[] args) {
        Doc doc = new Doc();

        Pdf pdf = DocumentAdapter.convertToPdf(doc);

        DocumentProcessor pdfProcessor = new PdfProcessor();

        pdfProcessor.process(pdf);

        Pdf pdff = new Pdf();

        Doc docc = DocumentAdapter.convertToDoc(pdff);

        DocumentProcessor docProcessor = new DocProcessor();

        docProcessor.process(docc);
    }
}
