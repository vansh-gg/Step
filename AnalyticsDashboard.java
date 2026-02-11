import java.util.*;

public class AnalyticsDashboard {

    private HashMap<String, Integer> pageViews = new HashMap<>();
    private HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    private HashMap<String, Integer> trafficSource = new HashMap<>();

    // Process event
    public void processEvent(String url, String userId, String source) {

        // Count total views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // Track unique users
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // Track traffic source
        trafficSource.put(source, trafficSource.getOrDefault(source, 0) + 1);
    }

    // Get Top 10 pages
    public void getDashboard() {

        System.out.println("Top Pages:");

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        int count = 0;
        while (!pq.isEmpty() && count < 10) {
            Map.Entry<String, Integer> entry = pq.poll();
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(url + " → " + views +
                    " views (" + unique + " unique)");
            count++;
        }

        System.out.println("\nTraffic Sources:");
        trafficSource.forEach((k, v) ->
                System.out.println(k + " → " + v));
    }

    public static void main(String[] args) {

        AnalyticsDashboard dash = new AnalyticsDashboard();

        dash.processEvent("/news", "u1", "google");
        dash.processEvent("/news", "u2", "facebook");
        dash.processEvent("/sports", "u1", "direct");
        dash.processEvent("/news", "u3", "google");

        dash.getDashboard();
    }
}
