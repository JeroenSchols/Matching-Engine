public class OrderBook {

    private final String instrument;
    private final LimitStructure buyTree = new LimitStructure(true);
    private final LimitStructure sellTree = new LimitStructure(false);

    OrderBook(String instrument) {
        this.instrument = instrument;
    }

    void addOrder(Order order) {
        if (order.isBuy) {
            buyTree.addOrder(order);
        } else {
            sellTree.addOrder(order);
        }
        match();
    }

    void removeOrder(Order order) {
        if (order.isBuy) {
            buyTree.removeOrder(order);
        } else {
            sellTree.removeOrder(order);
        }
    }

    private void match() {
        for (Float minSellPrice = getMinSellPrice(), maxBuyPrice = getMaxBuyPrice();
             minSellPrice != null && maxBuyPrice != null && minSellPrice < maxBuyPrice;
             minSellPrice = getMinSellPrice(), maxBuyPrice = getMaxBuyPrice())
        {
            Order buyOrder = buyTree.getMaxPriced().getOldest();
            Order sellOrder = sellTree.getMinPriced().getOldest();
            int quantity = Math.min(buyOrder.quantity, sellOrder.quantity);
            float price = buyOrder.orderId > sellOrder.orderId ? buyOrder.price : sellOrder.price;
            buyOrder.quantity -= quantity;
            sellOrder.quantity -= quantity;
            if (buyOrder.quantity == 0) buyTree.removeOrder(buyOrder);
            if (sellOrder.quantity == 0) sellTree.removeOrder(sellOrder);
            Main.publishTrade(buyOrder.orderId, sellOrder.orderId, quantity, this.instrument, price);
        }
    }

    Float getMaxSellPrice() {
        if (sellTree.isEmpty()) return null;
        return sellTree.getMaxPriced().price;
    }

    Float getMinSellPrice() {
        if (sellTree.isEmpty()) return null;
        return sellTree.getMinPriced().price;
    }

    Float getMaxBuyPrice() {
        if (buyTree.isEmpty()) return null;
        return buyTree.getMaxPriced().price;
    }

    Float getMinBuyPrice() {
        if (buyTree.isEmpty()) return null;
        return buyTree.getMinPriced().price;
    }

    @Override
    public String toString() {
        return "Book for instrument: " + instrument + "\n" + buyTree + sellTree;
    }
}
