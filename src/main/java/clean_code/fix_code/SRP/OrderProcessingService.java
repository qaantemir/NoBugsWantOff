package clean_code.fix_code.SRP;

public class OrderProcessingService {
    ConfirmationService confirmationService = new ConfirmationService();
    InvoiceGenerateService invoiceGenerateService = new InvoiceGenerateService();

    public void processOrder(Order order) {
        System.out.println("Обрабатываем заказ...");

        confirmationService.sendEmailConfirmation();
        invoiceGenerateService.generateInvoice();
    }

}
