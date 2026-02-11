import java.util.*;

public class DNSCache {

    class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, long ttlSeconds) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private final int MAX_SIZE = 5;
    private int hits = 0, misses = 0;

    private LinkedHashMap<String, DNSEntry> cache =
            new LinkedHashMap<>(16, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > MAX_SIZE;
                }
            };

    public String resolve(String domain) {

        if (cache.containsKey(domain)) {
            DNSEntry entry = cache.get(domain);
            if (!entry.isExpired()) {
                hits++;
                return "Cache HIT → " + entry.ip;
            }
            cache.remove(domain);
        }
        String newIP = "192.168.1." + new Random().nextInt(255);
        cache.put(domain, new DNSEntry(newIP, 5)); // TTL 5 sec
        misses++;
        return "Cache MISS → " + newIP;
    }

    public void getStats() {
        int total = hits + misses;
        System.out.println("Hit Rate: " +
                (total == 0 ? 0 : (hits * 100.0 / total)) + "%");
    }

    public static void main(String[] args) throws Exception {
        DNSCache dns = new DNSCache();

        System.out.println(dns.resolve("poki.com"));
        System.out.println(dns.resolve("google.com"));

        dns.getStats();
    }
}
