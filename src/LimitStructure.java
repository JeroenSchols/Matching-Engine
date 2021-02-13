import java.util.*;

public class LimitStructure {

    private final TreeMap<Float, Entry> tree = new TreeMap<>(Float::compareTo);
    private final HashMap<Float, OrderList> priceToList = new HashMap<>();
    private final boolean isBuy;

    private final Entry head = new Entry();
    private final Entry tail = new Entry();

    private class Entry {
        OrderList orderList;
        Entry next;
        Entry prev;
    }

    LimitStructure(boolean isBuy) {
        this.isBuy = isBuy;
        head.next = tail;
        tail.prev = head;
        tree.put(Float.NEGATIVE_INFINITY, head);
        tree.put(Float.POSITIVE_INFINITY, tail);
    }

    OrderList getMinPriced() {
        return head.next.orderList;
    }

    OrderList getMaxPriced() {
        return tail.prev.orderList;
    }

    boolean isEmpty() {
        return head.next == tail;
    }

    void addOrder(Order order) {
        OrderList orderList = priceToList.get(order.price);
        if (orderList == null) {
            orderList = new OrderList(order.price, isBuy);
            Entry entry = new Entry();
            entry.orderList = orderList;
            Entry pred = tree.floorEntry(order.price).getValue();
            entry.next = pred.next;
            pred.next.prev = entry;
            entry.prev = pred;
            pred.next = entry;
            tree.put(order.price, entry);
            priceToList.put(order.price, orderList);
        }
        orderList.addOrder(order);
    }

    void removeOrder(Order order) {
        OrderList orderList = priceToList.get(order.price);
        if (orderList == null) return;
        orderList.removeOrder(order);
        if (!orderList.isEmpty()) return;
        Entry entry = tree.remove(order.price);
        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
        priceToList.remove(order.price);
    }

    @Override
    public String toString() {
        Collection<Entry> entries = isBuy ? tree.descendingMap().values() : tree.values();
        String str = "";
        for (Entry entry : entries) if (entry != head && entry != tail) str += entry.orderList;
        return str;
    }
}
