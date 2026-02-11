import java.util.*;

public class PlagiarismDetector {

    private static final int N = 5; // 5-gram
    private HashMap<String, Set<String>> nGramIndex = new HashMap<>();
    private HashMap<String, Integer> docNGramCount = new HashMap<>();

    // Add document to system
    public void indexDocument(String docId, String content) {

        String[] words = content.toLowerCase().split("\\s+");
        int totalNGrams = 0;

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(" ");
            }

            String nGram = sb.toString().trim();

            nGramIndex.putIfAbsent(nGram, new HashSet<>());
            nGramIndex.get(nGram).add(docId);

            totalNGrams++;
        }

        docNGramCount.put(docId, totalNGrams);
    }

    // Check similarity of new document
    public void checkSimilarity(String newDocId, String content) {

        String[] words = content.toLowerCase().split("\\s+");
        HashMap<String, Integer> matchCount = new HashMap<>();

        int totalNewNGrams = 0;

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(" ");
            }

            String nGram = sb.toString().trim();
            totalNewNGrams++;

            if (nGramIndex.containsKey(nGram)) {
                for (String existingDoc : nGramIndex.get(nGram)) {
                    matchCount.put(existingDoc,
                            matchCount.getOrDefault(existingDoc, 0) + 1);
                }
            }
        }

        System.out.println("Similarity Results:\n");

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {

            String existingDoc = entry.getKey();
            int matches = entry.getValue();

            double similarity = (matches * 100.0) / totalNewNGrams;

            System.out.println("Matched with " + existingDoc +
                    " → " + String.format("%.2f", similarity) + "% similarity");

            if (similarity > 60) {
                System.out.println("⚠ PLAGIARISM DETECTED!");
            }
        }
    }

    public static void main(String[] args) {

        PlagiarismDetector detector = new PlagiarismDetector();

        String doc1 = "Java is a programming language widely used in enterprise applications";
        String doc2 = "Java is a programming language widely used in web development";
        String newDoc = "Java is a programming language widely used in enterprise systems";

        detector.indexDocument("doc1", doc1);
        detector.indexDocument("doc2", doc2);

        detector.checkSimilarity("newDoc", newDoc);
    }
}
