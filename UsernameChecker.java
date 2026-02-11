import java.util.*;

public class UsernameChecker {

    private HashMap<String, Integer> users = new HashMap<>();
    private HashMap<String, Integer> attempts = new HashMap<>();
    private int userCounter = 1;

    public void registerUser(String username) {
        users.put(username, userCounter++);
    }

    public boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String suggestion = username + i;
            if (!users.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        suggestions.add(username.replace("_", "."));
        return suggestions;
    }

    public String getMostAttempted() {
        String maxUser = "";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : attempts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxUser = entry.getKey();
            }
        }
        return maxUser + " (" + maxCount + " attempts)";
    }

    public static void main(String[] args) {
        UsernameChecker checker = new UsernameChecker();

        checker.registerUser("Vansh");

        System.out.println(checker.checkAvailability("Vansh"));
        System.out.println(checker.checkAvailability("Vansh8"));
        System.out.println(checker.getMostAttempted());
        System.out.println(checker.suggestAlternatives("Vansh"));

    }
}
