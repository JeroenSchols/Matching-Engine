import java.util.Random;

public class Main {

    static void publishTrade(int buyOrderId, int sellOrderId, int quantity, float price) {
        System.out.println(String.format("buy: %d, sell: %d, quantity: %d, price: %f",
                buyOrderId, sellOrderId, quantity, price));
    }

    public static void main(String[] args) {
        String[] instruments = {"A", "B", "C"};
        ExecutionEngine executionEngine = new ExecutionEngine();
        Random random = new Random();
        int N = 1000;
        Order[] orders = new Order[N];
        for (int step = 0; step < N; step++) {
            String instrument = instruments[random.nextInt(instruments.length)];
            boolean isBuy = random.nextBoolean();
            float price = random.nextInt(100);
            int quantity = Math.abs(random.nextInt()) % 10000 + 1;
            orders[step] = new Order(instrument, isBuy, price, quantity);
            executionEngine.handleOrder(orders[step]);
            if (random.nextBoolean() && step > 0) executionEngine.cancelOrder(orders[random.nextInt(step)]);
        }
        System.out.println(executionEngine);
    }
}
