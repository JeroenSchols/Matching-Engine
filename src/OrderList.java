import java.util.HashMap;

public class OrderList {

    final float price;
    final boolean isBuy;

    private final HashMap<Order, Entry> orderToEntry = new HashMap<>();
    private final Entry head = new Entry();
    private final Entry tail = new Entry();

    private class Entry {
        Order order;
        Entry prev;
        Entry next;
    }

    OrderList(float price, boolean isBuy) {
        this.price = price;
        this.isBuy = isBuy;
        head.next = tail;
        tail.prev = head;
    }

    Order getOldest() {
        return head.next.order;
    }

    void addOrder(Order order) {
        if (order.price != price) throw new IllegalArgumentException(
                String.format("Adding order with price %f to list for price %f", order.price, price));
        if (order.isBuy != isBuy) throw new IllegalArgumentException(
                order.isBuy ? "Adding buy order to list of sell orders" : "Adding sell order to list of buy orders");

        // do not re-add orders that are already present
        if (orderToEntry.containsKey(order)) return;

        Entry entry = new Entry();
        entry.order = order;
        tail.prev.next = entry;
        entry.prev = tail.prev;
        tail.prev = entry;
        entry.next = tail;
        orderToEntry.put(order, entry);
    }

    void removeOrder(Order order) {
        Entry entry = orderToEntry.remove(order);
        if (entry == null) return;
        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
    }

    boolean isEmpty() {
        return head.next == tail;
    }

    @Override
    public String toString() {
        String str = "";
        for (Entry entry = head.next; entry != tail; entry = entry.next) str += entry.order + "\n";
        return str;
    }
}
