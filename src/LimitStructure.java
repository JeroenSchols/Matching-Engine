import java.util.*;

public class LimitStructure {

    private final TreeMap<Float, OrderList> tree = new TreeMap<>(Float::compareTo);
    private final HashMap<Float, OrderList> priceToList = new HashMap<>();
    private final boolean isBuy;

    LimitStructure(boolean isBuy) {
        this.isBuy = isBuy;
    }

    OrderList getMinPriced() {
        if (tree.isEmpty()) return null;
        return tree.firstEntry().getValue();
    }

    OrderList getMaxPriced() {
        if (tree.isEmpty()) return null;
        return tree.lastEntry().getValue();
    }

    boolean isEmpty() {
        return tree.isEmpty();
    }

    void addOrder(Order order) {
        OrderList orderList = priceToList.get(order.price);
        if (orderList == null) {
            orderList = new OrderList(order.price, isBuy);
            tree.put(order.price, orderList);
            priceToList.put(order.price, orderList);
        }
        orderList.addOrder(order);
    }

    void removeOrder(Order order) {
        OrderList orderList = priceToList.get(order.price);
        if (orderList == null) return;
        orderList.removeOrder(order);
        if (!orderList.isEmpty()) return;
        tree.remove(order.price);
        priceToList.remove(order.price);
    }

    @Override
    public String toString() {
        Collection<OrderList> orderLists = isBuy ? tree.descendingMap().values() : tree.values();
        String str = "";
        for (OrderList orderList : orderLists) str += orderList;
        return str;
    }
}
