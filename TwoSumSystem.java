import java.util.*;

public class TwoSumSystem {

    static class Transaction {
        int id;
        int amount;

        Transaction(int id, int amount) {
            this.id = id;
            this.amount = amount;
        }
    }

    public static void findTwoSum(List<Transaction> transactions, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                System.out.println("Match: "
                        + map.get(complement).id
                        + " + " + t.id);
            }

            map.put(t.amount, t);
        }
    }

    public static void main(String[] args) {

        List<Transaction> list = Arrays.asList(
                new Transaction(1, 500),
                new Transaction(2, 300),
                new Transaction(3, 200)
        );

        findTwoSum(list, 500);
    }
}
