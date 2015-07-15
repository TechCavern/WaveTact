package com.techcavern.wavetact.objects;

public class CharReplacement {
    private final String letter;
    private final String vowel;

    public CharReplacement(String letter, String vowel) {
        this.letter = letter;
        this.vowel = vowel;
    }

    public String getLetter() {
        return this.letter;
    }

    public String getVowel() {
        return this.vowel;
    }


}
