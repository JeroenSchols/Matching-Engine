import java.util.HashMap;

/**
 * A linked list of orders using hashing for direct removal of orders
 */
public class OrderList {

    private final HashMap<Order, Entry> orderToEntry = new HashMap<>();
    private final Entry head = new Entry();
    private final Entry tail = new Entry();

    private class Entry {
        Order order;
        Entry prev;
        Entry next;
    }

    OrderList() {
        head.next = tail;
        tail.prev = head;
    }

    /** @return the first order inserted into this list in O(1) time */
    Order getOldest() {
        return head.next.order;
    }

    /** adds a new order to this list in O(1) time */
    void addOrder(Order order) {
        if (orderToEntry.containsKey(order)) return;
        Entry entry = new Entry();
        entry.order = order;
        tail.prev.next = entry;
        entry.prev = tail.prev;
        tail.prev = entry;
        entry.next = tail;
        orderToEntry.put(order, entry);
    }

    /** removes an order from this list in O(1) time */
    void removeOrder(Order order) {
        Entry entry = orderToEntry.remove(order);
        if (entry == null) return;
        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
    }

    /** @return whether this list is empty */
    boolean isEmpty() {
        return head.next == tail;
    }

    @Override
    public String toString() {
        String str = "";
        for (Entry entry = head.next; entry != tail; entry = entry.next) str += entry.order;
        return str;
    }
}
