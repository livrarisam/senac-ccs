package br.com.senac.ccs.thinkfast;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonInclude (JsonInclude.Include.NON_NULL)
public class Question {

    private String description;
    private List<String> answers;
    private String answer;

    public Question() {
    }

    public Question( String description, List<String> answers, String answer ) {
        this.description = description;
        this.answers = answers;
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getAnswer() {
        return answer;
    }
}