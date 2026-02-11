import java.util.*;

public class AutocompleteSystem {

    private HashMap<String, Integer> queryFreq = new HashMap<>();

    public void addQuery(String query) {
        queryFreq.put(query,
                queryFreq.getOrDefault(query, 0) + 1);
    }

    public List<String> search(String prefix) {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        for (Map.Entry<String, Integer> entry : queryFreq.entrySet()) {

            if (entry.getKey().startsWith(prefix)) {
                pq.offer(entry);
                if (pq.size() > 10)
                    pq.poll();
            }
        }

        List<String> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll().getKey());
        }

        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {

        AutocompleteSystem auto = new AutocompleteSystem();

        auto.addQuery("java tutorial");
        auto.addQuery("javascript");
        auto.addQuery("java download");
        auto.addQuery("java tutorial");

        System.out.println(auto.search("jav"));
    }
}
