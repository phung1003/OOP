package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Phonetics {
        String audio = "null";
        String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Definitions {
        String definition;
        String example;

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class Meanings {
        String partOfSpeech;
        List<String> synonyms = new ArrayList<String>();
        List<String> antonyms = new ArrayList<String>();
        List<Definitions> definitions = new ArrayList<Definitions>();

        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        public void setPartOfSpeech(String partOfSpeech) {
            this.partOfSpeech = partOfSpeech;
        }

        public List<String> getSynonyms() {
            return synonyms;
        }

        public void setSynonyms(List<String> synonyms) {
            this.synonyms = synonyms;
        }

        public List<String> getAntonyms() {
            return antonyms;
        }

        public void setAntonyms(List<String> antonyms) {
            this.antonyms = antonyms;
        }

        public List<Definitions> getDefinitions() {
            return definitions;
        }

        public void setDefinitions(List<Definitions> definitions) {
            this.definitions = definitions;
        }
    }
}

