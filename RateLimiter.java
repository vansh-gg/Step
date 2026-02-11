import java.util.*;

public class RateLimiter {

    class TokenBucket {
        int tokens;
        long lastRefill;
        final int maxTokens;
        final int refillRate; // tokens per second

        TokenBucket(int maxTokens, int refillRate) {
            this.maxTokens = maxTokens;
            this.refillRate = refillRate;
            this.tokens = maxTokens;
            this.lastRefill = System.currentTimeMillis();
        }

        synchronized boolean allowRequest() {

            long now = System.currentTimeMillis();
            long seconds = (now - lastRefill) / 1000;

            if (seconds > 0) {
                tokens = Math.min(maxTokens, tokens + (int)(seconds * refillRate));
                lastRefill = now;
            }

            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }
    }

    private HashMap<String, TokenBucket> clients = new HashMap<>();

    public boolean checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(5, 1));
        return clients.get(clientId).allowRequest();
    }

    public static void main(String[] args) {

        RateLimiter limiter = new RateLimiter();

        for (int i = 0; i < 7; i++) {
            System.out.println(limiter.checkRateLimit("abc123"));
        }
    }
}
