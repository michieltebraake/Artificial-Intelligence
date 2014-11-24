package ai;

import java.util.Arrays;

public class Tokenizer {
    public static void main(String[] args) {
        String test = "Hello this is a sentence go and tokenize this. Now with punctuation and a weird_ underscore.";
        Tokenizer tokenizer = new Tokenizer();
        System.out.println(Arrays.toString(tokenizer.tokenize(test)));
    }

    private String[] tokenize(String input) {
        return input.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
    }
}
