package Entity;

import java.sql.Date;
import java.time.LocalDate;

//Nguyen Quan
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
    public double getTotalWithTax() { return amount * 1.1; }

    protected void updatePaymentStatus(boolean status) {
        this.completed = status;
        DatabaseControl.updateTable("UPDATE Booking SET paymentStatus=" + status + " WHERE BookingID=" + bookingID);
    }

    // VISA CARD PAYMENT
    public static class CardPayment extends Payment {
        private String cardHolder;
        private String cardNumber;
        private String expiry;

        public CardPayment(int bookingID, double amount, String cardHolder, String cardNumber, String expiry, String cvv) {
            super(bookingID, amount);
            this.cardHolder = cardHolder.trim();
            this.cardNumber = mask(cardNumber);
            this.expiry = expiry;
        }

        @Override
        public boolean processPayment() {
            if (cardHolder == null || cardHolder.isEmpty()) return false;
            updatePaymentStatus(true);
            return true;
        }

        @Override
        public String getPaymentType() { return "VISA"; }

        public String getCardHolder() { return cardHolder; }
        public String getCardNumber() { return cardNumber; }
        public String getExpiry() { return expiry; }

        private String mask(String card) {
            String c = card.replaceAll("[\\s-]", "");
            return c.length() < 4 ? "****" : "**** **** **** " + c.substring(c.length() - 4);
        }

        public static boolean isValid(String card) {
            String c = card.replaceAll("[\\s-]", "");
            if (!c.matches("\\d{13,19}")) return false;
            int sum = 0;
            boolean alt = false;
            for (int i = c.length() - 1; i >= 0; i--) {
                int d = c.charAt(i) - '0';
                if (alt) { d *= 2; if (d > 9) d -= 9; }
                sum += d;
                alt = !alt;
            }
            return sum % 10 == 0;
        }
    }

    // QR PAYMENT
    public static class QRPayment extends Payment {
        private String transactionId;

        public QRPayment(int bookingID, double amount) {
            super(bookingID, amount);
            this.transactionId = "QR" + bookingID + "_" + System.currentTimeMillis();
        }

        @Override
        public boolean processPayment() {
            updatePaymentStatus(true);
            return true;
        }

        public boolean verifyTransaction(String code) {
            if (code != null && code.equals(transactionId)) {
                updatePaymentStatus(true);
                return true;
            }
            return false;
        }

        @Override
        public String getPaymentType() { return "QR"; }

        public String getTransactionId() { return transactionId; }
        public String getQRContent() { return "HOTEL|" + bookingID + "|" + (long)getTotalWithTax() + "|" + transactionId; }
    }

    // CASH PAYMENT
    public static class CashPayment extends Payment {
        private double received;

        public CashPayment(int bookingID, double amount, double received) {
            super(bookingID, amount);
            this.received = received;
        }

        @Override
        public boolean processPayment() {
            if (received < getTotalWithTax()) return false;
            updatePaymentStatus(true);
            return true;
        }

        @Override
        public String getPaymentType() { return "CASH"; }

        public double getReceived() { return received; }
        public double getChange() { return Math.max(0, received - getTotalWithTax()); }
    }
}
