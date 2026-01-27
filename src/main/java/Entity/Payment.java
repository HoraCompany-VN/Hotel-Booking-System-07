package Entity;

import java.sql.Date;
import java.time.LocalDate;

import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.processor.QueryTransactionStatus;
import com.mservice.models.QueryStatusTransactionResponse;

//Nguyen Quan works
public abstract class Payment {
    protected int bookingID;
    protected double amount;
    protected Date paymentDate;
    protected boolean completed;

    protected Payment(int bookingID, double amount) {
        this.bookingID = bookingID;
        this.amount = amount;
        this.paymentDate = Date.valueOf(LocalDate.now());
        this.completed = false;
    }

    public abstract boolean processPayment();
    public abstract String getPaymentType();

    public int getBookingID() { return bookingID; }
    public double getAmount() { return amount; }
    public Date getPaymentDate() { return paymentDate; }
    public boolean isCompleted() { return completed; }

    public double getTotalWithTax() {
        return amount * 1.1;
    }

    protected void updatePaymentStatus(boolean status) {
        this.completed = status;
        String sql = "UPDATE bookings SET paymentStatus=" + status + 
                     " WHERE bookingID=" + bookingID;
        DatabaseControl.updateTable(sql);
    }

    // ==================== MOMO PAYMENT ====================
    public static class MoMoPayment extends Payment {
        private String orderId;
        private String payUrl;
        private String qrCodeUrl;
        private int resultCode;
        
        private static final String RETURN_URL = "https://yourapp.com/payment/success";
        private static final String NOTIFY_URL = "https://yourapp.com/payment/notify";

        public MoMoPayment(int bookingID, double amount) {
            super(bookingID, amount);
            this.orderId = "BOOKING_" + bookingID + "_" + System.currentTimeMillis();
        }

        @Override
        public boolean processPayment() {
            try {
                Environment environment = Environment.selectEnv("dev");
                
                String requestId = String.valueOf(System.currentTimeMillis());
                long momoAmount = (long) getTotalWithTax();
                String orderInfo = "Thanh toan dat phong #" + bookingID;
                
                PaymentResponse response = CreateOrderMoMo.process(
                    environment,
                    orderId,
                    requestId,
                    String.valueOf(momoAmount),
                    orderInfo,
                    RETURN_URL,
                    NOTIFY_URL,
                    "",
                    RequestType.CAPTURE_WALLET,
                    Boolean.TRUE
                );
                
                if (response != null && response.getResultCode() == 0) {
                    this.payUrl = response.getPayUrl();
                    this.qrCodeUrl = response.getQrCodeUrl();
                    this.resultCode = response.getResultCode();
                    
                    java.awt.Desktop.getDesktop().browse(new java.net.URI(payUrl));
                    
                    System.out.println("MoMo Payment URL: " + payUrl);
                    return true;
                } else {
                    System.err.println("MoMo Error: " + (response != null ? response.getMessage() : "null"));
                    return false;
                }
                
            } catch (Exception e) {
                System.err.println("MoMo Exception: " + e.getMessage());
                return false;
            }
        }

        public boolean checkPaymentStatus() {
            try {
                Environment environment = Environment.selectEnv("dev");
                String requestId = String.valueOf(System.currentTimeMillis());
                
                QueryStatusTransactionResponse response = 
                    QueryTransactionStatus.process(environment, orderId, requestId);
                
                if (response != null && response.getResultCode() == 0) {
                    updatePaymentStatus(true);
                    return true;
                }
            } catch (Exception e) {
                System.err.println("Query Error: " + e.getMessage());
            }
            return false;
        }

        @Override
        public String getPaymentType() { return "MOMO"; }

        public String getOrderId() { return orderId; }
        public String getPayUrl() { return payUrl; }
        public String getQrCodeUrl() { return qrCodeUrl; }
        public int getResultCode() { return resultCode; }
    }

    // ==================== BANK PAYMENT ====================
    public static class BankPayment extends Payment {
        private String cardHolderName;
        private String cardNumber;
        private String expirationDate;

        public BankPayment(int bookingID, double amount, String cardHolderName,
                          String cardNumber, String expirationDate, String cvc) {
            super(bookingID, amount);
            this.cardHolderName = cardHolderName.trim();
            this.cardNumber = maskCard(cardNumber);
            this.expirationDate = expirationDate;
        }

        @Override
        public boolean processPayment() {
            if (cardHolderName == null || cardHolderName.isEmpty()) {
                return false;
            }
            updatePaymentStatus(true);
            System.out.println("Bank payment completed: " + cardNumber);
            return true;
        }

        @Override
        public String getPaymentType() { return "BANK"; }

        public String getCardHolderName() { return cardHolderName; }
        public String getCardNumber() { return cardNumber; }
        public String getExpirationDate() { return expirationDate; }

        private String maskCard(String card) {
            String cleaned = card.replaceAll("[\\s-]", "");
            if (cleaned.length() < 4) return "****";
            return "**** **** **** " + cleaned.substring(cleaned.length() - 4);
        }

        public static boolean isValidCard(String cardNumber) {
            String cleaned = cardNumber.replaceAll("[\\s-]", "");
            if (!cleaned.matches("\\d{13,19}")) return false;

            int sum = 0;
            boolean alt = false;
            for (int i = cleaned.length() - 1; i >= 0; i--) {
                int d = cleaned.charAt(i) - '0';
                if (alt) { d *= 2; if (d > 9) d -= 9; }
                sum += d;
                alt = !alt;
            }
            return sum % 10 == 0;
        }
    }

    // ==================== CASH PAYMENT ====================
    public static class CashPayment extends Payment {
        private double receivedAmount;

        public CashPayment(int bookingID, double amount, double receivedAmount) {
            super(bookingID, amount);
            this.receivedAmount = receivedAmount;
        }

        @Override
        public boolean processPayment() {
            if (receivedAmount < getTotalWithTax()) {
                return false;
            }
            updatePaymentStatus(true);
            System.out.println("Cash payment completed. Change: " + getChange());
            return true;
        }

        @Override
        public String getPaymentType() { return "CASH"; }

        public double getReceivedAmount() { return receivedAmount; }
        
        public double getChange() {
            return Math.max(0, receivedAmount - getTotalWithTax());
        }
    }
}
