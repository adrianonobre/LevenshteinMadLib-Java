package org.adrianonobre;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adriano on 2016-06-28.
 */
public class MadLib {

    private final List<String> dictionary;

    private final LevenshteinCalculator levenshteinCalculator = new LevenshteinCalculator();

    public static void main(String[] args) throws IOException {
        String sentence = args[0];
        int distance = Integer.valueOf(args[1]);

        String newSentence = new MadLib("W").process(sentence, distance);

        System.out.println(newSentence);
    }

    public MadLib(String wordFile) throws IOException {
        dictionary = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(wordFile))) {
            for (String line; (line = br.readLine()) != null; ) {
                dictionary.add(line.toLowerCase());
            }
        }
    }

    public String process(String sentence, int distance) {
        String[] words = sentence.split(" ");
        int random = (int) (System.currentTimeMillis() % words.length);

        String newWord = getReplacement(distance, words[random]);

        words[random] = newWord;

        return String.join(" ", words);
    }

    public String getReplacement(int distance, String str) {
        final int dictionarySize = dictionary.size();
        int startIndex = (int) Math.floor(Math.random() * dictionarySize);
        for (int i = 0; i < dictionarySize; i++) {
            String word = dictionary.get((i + startIndex) % dictionarySize);
            if (levenshteinCalculator.getDistance(str, word) == distance) {
                return word;
            }
        }
        return str;
    }


}
