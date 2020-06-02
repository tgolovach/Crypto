

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Dan on 18.02.2018.
 */
public class Laba1 {
    public static String filePath = "C:\\Users\\Dan\\Desktop\\Laba1Crypta\\use.txt";
    public static char[] letters = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к',
            'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
    public static boolean withSpace;
    public static boolean isCrossingLetters;
    public static double entrophy1 = 0.0;
    public static double entrophy2 = 0.0;
    public static double[][] FREQUENCY;
    public static ArrayList<String> usedBigrams = new ArrayList<String>();

    public static void main(String args[]) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuffer text = new StringBuffer();
        Scanner in = new Scanner(new File(filePath));
        while (in.hasNext())
            text.append(in.nextLine().toLowerCase()).append("\r\n");
        in.close();
        System.out.println("Use text with spaces? y/n");
        String isSpace = reader.readLine();
        if (isSpace.equals("y")) {
            withSpace = true;
            FREQUENCY = new double[34][34];
            letters = new char[]{'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к',
                    'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', ' '};
        } else {
            FREQUENCY = new double[33][33];
            StringBuffer textNoSpaces = new StringBuffer();
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) != ' ') {
                    textNoSpaces.append(text.charAt(i));
                }
            }
            text = textNoSpaces;
            withSpace = false;
        }
        System.out.println("1 - Monograma");
        System.out.println("2 - Bigrama");
        int nGrama = Integer.parseInt(reader.readLine());
        if (nGrama == 2) {
            System.out.println("Letters will cross(y) or letter will not cross(n)");
            String isCrossing = reader.readLine();
            if (isCrossing.equals("y")) {
                isCrossingLetters = true;
            } else {
                isCrossingLetters = false;
            }
        }
        nGramaFunction(nGrama, text);
    }

    public static void nGramaFunction(int nGrama, StringBuffer text) {
        System.out.println("Do text have spaces? " + withSpace);
        System.out.println();
        System.out.println("Ngrama:" + nGrama);
        System.out.println();
        switch (nGrama) {
            case 1: {
                for (int i = 0; i < letters.length; i++) {
                    double counter = 0;
                    for (int j = 0; j < text.length(); j++) {
                        if (letters[i] == text.charAt(j)) {
                            counter++;
                        }
                    }
                    if (counter != 0) {
                        entrophy1 += (counter / text.length()) * (Math.log(counter / text.length()) / (Math.log(2)));
                        System.out.println(letters[i] + ": " + (counter / text.length()));
                    }
                }
                System.out.println("Monogram entrophy: " + Math.abs(entrophy1));
                break;
            }
            case 2: {
                for (int i = 0; i < letters.length; i++) {
                    for (int j = 0; j < letters.length; j++) {
                        double counter = 0;
                        for (int k = 0; k < text.length() - 1; k+=isCrossingLetters ? 1 : 2) {
                            if ((Character.toString(letters[i]) + Character.toString(letters[j])).equals(Character.toString(text.charAt(k)) + Character.toString(text.charAt(k + 1)))) {
                                counter++;
                            }
                        }
                        FREQUENCY[i][j] = counter / (text.length()/(isCrossingLetters ? 1 : 2));
                        if (counter != 0) {
                            entrophy2 += (counter / (text.length()/(isCrossingLetters ? 1 : 2))) * (Math.log(counter / (text.length()/(isCrossingLetters ? 1 : 2))) / (Math.log(2)));
                        }
                    }
                }
                System.out.printf("%-6s ", "");
                for (int i = 0; i < letters.length; i++) {
                    System.out.printf("%-6s ", letters[i]);
                }
                System.out.println();
                for (int i = 0; i < letters.length; i++) {
                    System.out.print(letters[i] + "   ");
                    for (int j = 0; j < letters.length; j++) {
                        System.out.printf("%.4f ", FREQUENCY[i][j]);
                    }
                    System.out.println();
                }
                System.out.println("Bigram entrophy " + (isCrossingLetters ? "with crssing letters: " : "without crossing letters: ") + Math.abs(entrophy2 / 2));
            }
        }
    }
}
