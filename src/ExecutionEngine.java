import java.util.HashMap;
import java.util.Map;

public class ExecutionEngine {

    private final Map<String, OrderBook> instrumentToBook = new HashMap<>();

    /** adds an order to corresponding order book and perform the trades resulting from this addition */
    void handleOrder(Order order) {
        instrumentToBook.putIfAbsent(order.instrument, new OrderBook());
        OrderBook orderBook = instrumentToBook.get(order.instrument);
        orderBook.addOrder(order);
        orderBook.makeTrades();
    }

    /** cancels an order */
    void cancelOrder(Order order) {
        OrderBook orderBook = instrumentToBook.get(order.instrument);
        if (orderBook != null) orderBook.removeOrder(order);
    }

    /** @return the minimum price of a buying order for given instrument, or null in case no such order is present */
    Float getMinBuyPrice(String instrument) {
        OrderBook orderBook = instrumentToBook.get(instrument);
        return orderBook == null ? null : orderBook.getMinBuyPrice();
    }

    /** @return the maximum price of a buying order for given instrument, or null in case no such order is present */
    Float getMaxBuyPrice(String instrument) {
        OrderBook orderBook = instrumentToBook.get(instrument);
        return orderBook == null ? null : orderBook.getMaxBuyPrice();
    }

    /** @return the minimum price of a selling order for given instrument, or null in case no such order is present */
    Float getMinSellPrice(String instrument) {
        OrderBook orderBook = instrumentToBook.get(instrument);
        return orderBook == null ? null : orderBook.getMinSellPrice();
    }

    /** @return the maximum price of a selling order for given instrument, or null in case no such order is present */
    Float getMaxSellPrice(String instrument) {
        OrderBook orderBook = instrumentToBook.get(instrument);
        return orderBook == null ? null : orderBook.getMaxSellPrice();
    }

    @Override
    public String toString() {
        String str = "";
        for (OrderBook orderBook : instrumentToBook.values()) str += orderBook;
        return str;
    }
}
