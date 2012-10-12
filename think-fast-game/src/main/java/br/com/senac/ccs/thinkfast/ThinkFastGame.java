package br.com.senac.ccs.thinkfast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class ThinkFastGame {

    private final ConcurrentHashMap<String, Participant> participants;
    private final Lock lock;
    private final List<Question> questions;
    private Question currentQuestion;

    public ThinkFastGame() {
        this.participants = new ConcurrentHashMap<String, Participant>();
        this.questions = new ArrayList<Question>();
        this.lock = new ReentrantLock();
    }

    public Result play( String id, String name, Screen screen ) {
            lock.lock();
            Result result = null;
       try {
            Participant participant = new Participant (id, name, screen);

            participants.put(id, participant);
            result = new Result(currentQuestion, String.format("Welcome %s!", participant.getName()));
        }
        finally {
            lock.unlock();
       }
       return result;
    }


    public void bind( String id, Screen screen ) {
        Participant participant = participants.get(id);
        participant.setScreen(screen);
    }

    public Result answer( String id, String answer ) {
        lock.lock();        
        Result result = null;
        try {
            if (this.currentQuestion.getAnswer().equals(answer)) 
            {
                Question question = currentQuestion;
                questions.remove (question);
                Collections.shuffle(questions);
                currentQuestion = questions.get(0);
                questions.add(question);
                Participant winner = participants.remove(id);
                winner.notify(new Result(currentQuestion, "Parabeeeens!! :)"));
                for (Participant participant : participants.values()) {
                    participant.notify(new Result(currentQuestion, String.format ("O participante %s respondeu mais rapido, tente novamente", winner.getName())));
                }
                participants.put(id, winner);

            } else {
                Participant participant = participants.get(id);
                result = new Result("Incorreto!! :(");
            }
        }
        finally {
            lock.unlock();
        }
        return result;
    }
    @PostConstruct
    public void init() {
        this.questions.add( new Question( "Qual a capital dos EUA?", Arrays.asList( new String[]{ "Washington DC", "California", "Nevada" } ), "Washington DC" ) );
        this.questions.add( new Question( "Qual a capital da Russia?", Arrays.asList( new String[]{ "Berlin", "Paris", "Moscou" } ), "Moscou" ) );
        this.questions.add( new Question( "Qual a resposta da vida, do universo e tudo mais?", Arrays.asList( new String[]{ "32", "42", "52" } ), "42" ) );
        this.questions.add( new Question( "Entre as opcoes, qual nao e um sistema operacional?", Arrays.asList( new String[]{ "Windows", "MAC OS", "Linux" } ), "Windows" ) );
        this.questions.add( new Question( "Qual e a funcao do Internet Explorer?", Arrays.asList( new String[]{ "Navegar na internet", "Abrir e-mails", "Baixar o Firefox" } ), "Baixar o Firefox" ) );
        this.currentQuestion = questions.get( 0 );
    }
}
