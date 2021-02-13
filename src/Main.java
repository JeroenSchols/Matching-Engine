import java.util.Random;

public class Main {

    static void publishTrade(int buyOrderId, int sellOrderId, int quantity, String instrument, float price) {
        System.out.println(String.format("buy: %d, sell: %d, quantity: %d, instrument: %s, price: %f",
                buyOrderId, sellOrderId, quantity, instrument, price));
    }

    public static void main(String[] args) {
        String[] instruments = {"A", "B", "C"};
        ExecutionEngine executionEngine = new ExecutionEngine(instruments);
        Random random = new Random();
        int N = 1000;
        Order[] orders = new Order[N];
        for (int step = 0; step < N; step++) {
            String instrument = instruments[random.nextInt(instruments.length)];
            boolean isBuy = random.nextBoolean();
            float price = random.nextFloat();
            int quantity = Math.abs(random.nextInt()) % 10000 + 1;
            orders[step] = new Order(instrument, isBuy, price, quantity);
            executionEngine.addOrder(orders[step]);
            if (random.nextBoolean() && step > 0) executionEngine.removeOrder(orders[random.nextInt(step)]);
        }
        System.out.println(executionEngine);
    }
}
