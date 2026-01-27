package Entity;

import java.sql.Date;
import java.time.LocalDate;

//Nguyen Quan works
public abstract class Payment {
    protected int bookingID;
    protected double amount;
    protected Date paymentDate;
    protected boolean completed;

    //Nguyen Quan - Constructor
    protected Payment(int bookingID, double amount) {
        this.bookingID = bookingID;
        this.amount = amount;
        this.paymentDate = Date.valueOf(LocalDate.now());
        this.completed = false;
    }

    //Nguyen Quan - Abstract methods
    public abstract boolean processPayment();
    public abstract String getPaymentType();

    //Nguyen Quan - Getters
    public int getBookingID() { return bookingID; }
    public double getAmount() { return amount; }
    public Date getPaymentDate() { return paymentDate; }
    public boolean isCompleted() { return completed; }

    //Nguyen Quan - Tinh tong tien voi 10% VAT
    public double getTotalWithTax() {
        return amount * 1.1;
    }

    //Nguyen Quan - Cap nhat trang thai thanh toan trong database
    protected void updatePaymentStatus(boolean status) {
        this.completed = status;
        String sql = "UPDATE bookings SET paymentStatus=" + status + 
                     " WHERE bookingID=" + bookingID;
        DatabaseControl.updateTable(sql);
    }

    // ==================== BANK PAYMENT (MOMO) ====================
    //Nguyen Quan - Thanh toan qua MoMo
    public static class BankPayment extends Payment {
        private String cardHolderName;
        private String cardNumber;
        private String expirationDate;

        /*
         * ===== CAU HINH MOMO (BO COMMENT KHI SU DUNG) =====
         * 
         * private static final String MOMO_ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create";
         * private static final String MOMO_PARTNER_CODE = "YOUR_PARTNER_CODE";
         * private static final String MOMO_ACCESS_KEY = "YOUR_ACCESS_KEY";
         * private static final String MOMO_SECRET_KEY = "YOUR_SECRET_KEY";
         * private static final String MOMO_RETURN_URL = "http://localhost:8080/payment/momo-callback";
         * 
         * ===================================================
         */

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

            /*
             * ===== TICH HOP MOMO (BO COMMENT KHI SU DUNG) =====
             * 
             * try {
             *     String orderId = "BOOKING_" + bookingID + "_" + System.currentTimeMillis();
             *     String orderInfo = "Thanh toan booking #" + bookingID;
             *     long amount = (long) getTotalWithTax();
             *     
             *     String rawSignature = "accessKey=" + MOMO_ACCESS_KEY +
             *         "&amount=" + amount +
             *         "&extraData=" +
             *         "&ipnUrl=" + MOMO_RETURN_URL +
             *         "&orderId=" + orderId +
             *         "&orderInfo=" + orderInfo +
             *         "&partnerCode=" + MOMO_PARTNER_CODE +
             *         "&redirectUrl=" + MOMO_RETURN_URL +
             *         "&requestId=" + orderId +
             *         "&requestType=captureWallet";
             *     
             *     String signature = hmacSHA256(MOMO_SECRET_KEY, rawSignature);
             *     
             *     // Gui request toi MoMo API, nhan payUrl va mo browser
             *     // java.awt.Desktop.getDesktop().browse(new java.net.URI(payUrl));
             *     
             *     return true;
             * } catch (Exception e) {
             *     System.err.println("MoMo Error: " + e.getMessage());
             *     return false;
             * }
             * 
             * ===================================================
             */

            // Hien tai: Thanh toan offline (demo)
            updatePaymentStatus(true);
            System.out.println("Bank payment completed: " + cardNumber);
            return true;
        }

        /*
         * ===== HAM HO TRO MOMO (BO COMMENT KHI SU DUNG) =====
         * 
         * private String hmacSHA256(String key, String data) throws Exception {
         *     javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
         *     mac.init(new javax.crypto.spec.SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256"));
         *     byte[] hash = mac.doFinal(data.getBytes("UTF-8"));
         *     StringBuilder sb = new StringBuilder();
         *     for (byte b : hash) sb.append(String.format("%02x", b));
         *     return sb.toString();
         * }
         * 
         * ===================================================
         */

        @Override
        public String getPaymentType() { return "BANK"; }

        //Nguyen Quan - Getters
        public String getCardHolderName() { return cardHolderName; }
        public String getCardNumber() { return cardNumber; }
        public String getExpirationDate() { return expirationDate; }

        //Nguyen Quan - An so the chi hien 4 so cuoi
        private String maskCard(String card) {
            String cleaned = card.replaceAll("[\\s-]", "");
            if (cleaned.length() < 4) return "****";
            return "**** **** **** " + cleaned.substring(cleaned.length() - 4);
        }

        //Nguyen Quan - Kiem tra so the bang thuat toan Luhn
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
    //Nguyen Quan - Thanh toan tien mat
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

        //Nguyen Quan - Getters
        public double getReceivedAmount() { return receivedAmount; }
        
        //Nguyen Quan - Tinh tien thua
        public double getChange() {
            return Math.max(0, receivedAmount - getTotalWithTax());
        }
    }
}
