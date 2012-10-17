package br.com.senac.ccs.thinkfast;

import java.util.List;

public class Result {

    private Question question;
    private String message;
    private List<Participant> Participants;

    public Result() {
        System.out.println("Result");
    }

    public Result( Question question ) {
        this.question = question;
    }
    
    public Result( String message, List<Participant> Participants ) {
        this.message = message;
        this.Participants = Participants;
    }
    
    public Result( Question question, String message ) {
        this.question = question;
        this.message = message;
    }

    public Result( Question question, String message, List<Participant> Participants ) {
        this.question = question;
        this.message = message;
        this.Participants = Participants;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion( Question question ) {
        this.question = question;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public List<Participant> getParticipants() {
        return Participants;
    }

    public void setParticipants( List<Participant> Participants ) {
        this.Participants = Participants;
    }
    
}
