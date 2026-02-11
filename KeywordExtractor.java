import java.util.HashMap;
import java.util.Map;

public class KeywordExtractor {
    public static Map<String, Integer> extractKeywords(String document) {
        Map<String, Integer> wordFreq = new HashMap<>();
        String[] words = document.toLowerCase().split("\\s+");
        for (String word : words) {
// Clean punctuation
            word = word.replaceAll("[^a-z]", "");
            if (!word.isEmpty()) {
                wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
            }
        }
        return wordFreq;
    }
    public static void main(String[] args) {
        String article = "Java is a programming language. " +
                "Java is widely used for enterprise applications. " +
                "Programming in Java requires understanding of OOP.";
        Map<String, Integer> keywords = extractKeywords(article);
// Sort by frequency
        keywords.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }
}
