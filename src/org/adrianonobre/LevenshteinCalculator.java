package org.adrianonobre;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by adriano on 2016-06-28.
 */
public class LevenshteinCalculator {


    private static Set<String> words;

    public int getDistance(String source, String target) {

        // for all i and j, d[i,j] will hold the Levenshtein distance between
        // the first i characters of s and the first j characters of t
        // note that d has (m+1)*(n+1) values
        int m = source.length();
        int n = target.length();
        int[][] distance = new int[m + 1][];
        for (int i = 0; i <= m; i++) {
            distance[i] = new int[n + 1];
        }

        // source prefixes can be transformed into empty string by
        // dropping all characters
        for (int i = 1; i <= m; i++) {
            distance[i][0] = i;
        }

        // target prefixes can be reached from empty source prefix
        // by inserting every character
        for (int j = 1; j <= n; j++) {
            distance[0][j] = j;
        }

        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                int substitutionCost;
                if (source.charAt(i - 1) == target.charAt(j - 1)) {
                    substitutionCost = 0;
                } else {
                    substitutionCost = 1;
                }
                distance[i][j] = min(distance[i - 1][j] + 1,
                                     distance[i][j - 1] + 1,
                                     distance[i - 1][j - 1] + substitutionCost);
            }
        }

        return distance[m][n];
    }

    private static int min(int e1, int e2, int e3) {
        return Math.min(e1, Math.min(e2, e3));
    }
}
