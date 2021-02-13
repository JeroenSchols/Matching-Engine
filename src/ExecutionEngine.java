import java.util.HashMap;
import java.util.Map;

public class ExecutionEngine {

    private final Map<String, OrderBook> orderBooks = new HashMap<>();

    ExecutionEngine(String[] instruments) {
        for (String instrument : instruments)
            orderBooks.put(instrument, new OrderBook(instrument));
    }

    void addOrder(Order order) {
        OrderBook orderBook = orderBooks.get(order.instrument);
        if (orderBook == null) throw new IllegalArgumentException("Instrument " + order.instrument + " not recognized");
        orderBook.addOrder(order);
    }

    void removeOrder(Order order) {
        OrderBook orderBook = orderBooks.get(order.instrument);
        if (orderBook == null) throw new IllegalArgumentException("Instrument " + order.instrument + " not recognized");
        orderBook.removeOrder(order);
    }

    Float getMaxSellPrice(String instrument) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook == null) throw new IllegalArgumentException("Instrument " + instrument + " not recognized");
        return orderBook.getMaxSellPrice();
    }

    Float getMinSellPrice(String instrument) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook == null) throw new IllegalArgumentException("Instrument " + instrument + " not recognized");
        return orderBook.getMinSellPrice();
    }

    Float getMaxBuyPrice(String instrument) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook == null) throw new IllegalArgumentException("Instrument " + instrument + " not recognized");
        return orderBook.getMaxBuyPrice();
    }

    Float getMinBuyPrice(String instrument) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook == null) throw new IllegalArgumentException("Instrument " + instrument + " not recognized");
        return orderBook.getMinBuyPrice();
    }

    @Override
    public String toString() {
        String str = "";
        for (OrderBook orderBook : orderBooks.values()) str += orderBook;
        return str;
    }
}
