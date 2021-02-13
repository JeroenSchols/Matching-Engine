import java.util.*;

/**
 * Collection of (only buy or only sell) orders.
 * Uses a binary tree on order prices to store lists of orders of that price.
 * Leafs of this tree are stored linked such that the max/min price stored can be easily maintained.
 * Uses a hash map on price to directly access leafs of the tree.
 */
public class LimitStructure {

    private final TreeMap<Float, Entry> tree = new TreeMap<>(Float::compareTo);
    private final HashMap<Float, OrderList> priceToList = new HashMap<>();

    private final Entry head = new Entry();
    private final Entry tail = new Entry();

    private class Entry {
        OrderList orderList;
        Entry next;
        Entry prev;
    }

    LimitStructure() {
        head.next = tail;
        tail.prev = head;
        tree.put(Float.NEGATIVE_INFINITY, head);
        tree.put(Float.POSITIVE_INFINITY, tail);
    }

    /** adds an order to in O(1) time when an order of equal price is being stored, else takes O(log(n)) time */
    void addOrder(Order order) {
        OrderList orderList = priceToList.get(order.price);
        if (orderList == null) {
            orderList = new OrderList();
            Entry entry = new Entry();
            entry.orderList = orderList;
            // get the node in the linked list from the tree after which the new entry needs to be added and add it */
            Entry prev = tree.floorEntry(order.price).getValue();
            entry.next = prev.next;
            prev.next.prev = entry;
            entry.prev = prev;
            prev.next = entry;
            // add this new list to the hash map and tree as well
            tree.put(order.price, entry);
            priceToList.put(order.price, orderList);
        }
        orderList.addOrder(order);
    }

    /** removes an order in O(1) time when after removal an order with equal price remains, else takes O(log(n)) time */
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

    /** @return a list of orders that are of a minimum price in O(1) time */
    OrderList getMinPriced() {
        return head.next.orderList;
    }

    /** @return a list of orders that are of a maximum price in O(1) time */
    OrderList getMaxPriced() {
        return tail.prev.orderList;
    }

    /** @return whether this structure is not storing any orders */
    boolean isEmpty() {
        return head.next == tail;
    }

    @Override
    public String toString() {
        String str = "";
        for (Entry entry = head.next; entry != tail; entry = entry.next) str += entry.orderList;
        return str;
    }
}
