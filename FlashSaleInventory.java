import java.util.*;

public class FlashSaleInventory {

    private HashMap<String, Integer> stock = new HashMap<>();
    private HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    public void addProduct(String productId, int quantity) {
        stock.put(productId, quantity);
        waitingList.put(productId, new LinkedList<>());
    }
    public int checkStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    public synchronized String purchaseItem(String productId, int userId) {

        int currentStock = stock.getOrDefault(productId, 0);

        if (currentStock > 0) {
            stock.put(productId, currentStock - 1);
            return "Success! Remaining: " + (currentStock - 1);
        } else {
            waitingList.get(productId).add(userId);
            return "Out of stock. Added to waiting list. Position: "
                    + waitingList.get(productId).size();
        }
    }

    public static void main(String[] args) {
        FlashSaleInventory manager = new FlashSaleInventory();

        manager.addProduct("IPHONE15", 2);
        manager.addProduct("Banana", 6);

        System.out.println(manager.purchaseItem("IPHONE15", 101));
        System.out.println(manager.purchaseItem("IPHONE15", 102));
        System.out.println(manager.purchaseItem("IPHONE15", 103));
        System.out.println(manager.purchaseItem("Banana", 104));
    }
}
