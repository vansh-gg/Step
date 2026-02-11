import java.util.*;

public class MultiLevelCache {

    private final int L1_SIZE = 3;

    private LinkedHashMap<String, String> L1 =
            new LinkedHashMap<>(16, 0.75f, true) {
                protected boolean removeEldestEntry(
                        Map.Entry<String, String> eldest) {
                    return size() > L1_SIZE;
                }
            };

    private HashMap<String, String> L2 = new HashMap<>();
    private HashMap<String, String> L3 = new HashMap<>();

    public MultiLevelCache() {
        // simulate database
        L3.put("video1", "Data1");
        L3.put("video2", "Data2");
        L3.put("video3", "Data3");
        L3.put("video4", "Data4");
    }

    public String getVideo(String id) {

        if (L1.containsKey(id)) {
            System.out.println("L1 HIT");
            return L1.get(id);
        }

        if (L2.containsKey(id)) {
            System.out.println("L2 HIT → Promoted to L1");
            String data = L2.get(id);
            L1.put(id, data);
            return data;
        }

        if (L3.containsKey(id)) {
            System.out.println("L3 HIT → Added to L2");
            String data = L3.get(id);
            L2.put(id, data);
            return data;
        }

        return null;
    }

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        cache.getVideo("video1");
        cache.getVideo("video1");
        cache.getVideo("video2");
        cache.getVideo("video3");
        cache.getVideo("video4");
    }
}
