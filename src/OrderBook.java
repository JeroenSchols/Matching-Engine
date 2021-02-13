public class OrderBook {

    private final LimitStructure buyTree = new LimitStructure();
    private final LimitStructure sellTree = new LimitStructure();

    /** adds an order to this order book and executes trades that are enabled by this addition */
    void addOrder(Order order) {
        if (order.isBuy) {
            buyTree.addOrder(order);
        } else {
            sellTree.addOrder(order);
        }
    }

    /** removes an order from this order book */
    void removeOrder(Order order) {
        if (order.isBuy) {
            buyTree.removeOrder(order);
        } else {
            sellTree.removeOrder(order);
        }
    }

    /** finds the trades that can be made */
    void makeTrades() {
        for (Float minSellPrice = getMinSellPrice(), maxBuyPrice = getMaxBuyPrice();
             minSellPrice != null && maxBuyPrice != null && minSellPrice < maxBuyPrice;
             minSellPrice = getMinSellPrice(), maxBuyPrice = getMaxBuyPrice())
        {
            Order buyOrder = buyTree.getMaxPriced().getOldest();
            Order sellOrder = sellTree.getMinPriced().getOldest();
            // lets the price be determined by the last added order (higher orderIds)
            float price = buyOrder.orderId > sellOrder.orderId ? buyOrder.price : sellOrder.price;
            int quantity = Math.min(buyOrder.quantity, sellOrder.quantity);
            buyOrder.quantity -= quantity;
            sellOrder.quantity -= quantity;
            if (buyOrder.quantity == 0) buyTree.removeOrder(buyOrder);
            if (sellOrder.quantity == 0) sellTree.removeOrder(sellOrder);
            // publishes the trade that can be made (placeholder)
            Main.publishTrade(buyOrder.orderId, sellOrder.orderId, quantity, price);
        }
    }

    /** @return the minimum price of a buying order in this order book, or null in case no order is present */
    Float getMinBuyPrice() {
        if (buyTree.isEmpty()) return null;
        return buyTree.getMinPriced().getOldest().price;
    }

    /** @return the maximum price of a buying order in this order book, or null in case no order is present */
    Float getMaxBuyPrice() {
        if (buyTree.isEmpty()) return null;
        return buyTree.getMaxPriced().getOldest().price;
    }

    /** @return the minimum price of a selling order in this order book, or null in case no order is present */
    Float getMinSellPrice() {
        if (sellTree.isEmpty()) return null;
        return sellTree.getMinPriced().getOldest().price;
    }

    /** @return the maximum price of a selling order in this order book, or null in case no order is present */
    Float getMaxSellPrice() {
        if (sellTree.isEmpty()) return null;
        return sellTree.getMaxPriced().getOldest().price;
    }

    @Override
    public String toString() {
        return buyTree.toString() + sellTree.toString();
    }
}
