public class Order {

    private static int ORDER_ID_COUNTER = -1;

    final int orderId;
    final String instrument;
    final boolean isBuy;
    final float price;
    int quantity;

    Order(String instrument, boolean isBuy, float price, int quantity) {
        this.orderId = ORDER_ID_COUNTER++;
        this.instrument = instrument;
        this.isBuy = isBuy;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("orderId: %d, %s, instrument: %s, quantity: %d, price: %f",
                orderId, isBuy ? "buy" : "sell", instrument, quantity, price);
    }
}
