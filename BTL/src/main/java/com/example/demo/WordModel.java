package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.Nulls;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class WordModel {
    String word;
    String phonetic;
    List<Phonetics> phonetics = new ArrayList<Phonetics>();
    List<Meanings> meanings = new ArrayList<Meanings>();

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public List<Phonetics> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetics> phonetics) {
        this.phonetics = phonetics;
    }

    public List<Meanings> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meanings> meanings) {
        this.meanings = meanings;
    }

    public void removeDuplicates()
    {
        ArrayList<Phonetics> newList = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();


        for (Phonetics element : phonetics) {
            if (!temp.contains(element.text)) {
                newList.add(element);
                temp.add(element.text);
            }
        }
        phonetics = newList;
    }

    @Override
    public String toString() {
        return "WordModel{" +
                "word='" + word + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", phonetics=" + phonetics +
                ", meanings=" + meanings +
                '}';
    }
}

