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
import javax.servlet.AsyncContext;

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

    public void play( String id, String name, AsyncContext asyncContext ) throws IOException {
	   lock.lock();
       try {
            Participant participant = new Participant (id, name, asyncContext);

            participants.put(id, participant);
            participant.notify (new Result(currentQuestion, String.format("Welcome %s!", participant.getName())) );
        }
        finally {
            lock.unlock();
        }   
    }


    public void bind( String id, AsyncContext asyncContext ) {
        Participant participant = participants.get(id);
        participant.setAsyncContext(asyncContext);
    }

    public void answer( String id, String answer ) throws IOException {
        lock.lock();        
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
                participant.notify(new Result("Incorreto!! :("));
            }
        }
        finally {
            lock.unlock();
        }
    }

    public void init() {
        this.questions.add( new Question( "Qual a capital dos EUA?", Arrays.asList( new String[]{ "Washington DC", "California", "Nevada" } ), "Washington DC" ) );
        this.questions.add( new Question( "Qual a capital da Russia?", Arrays.asList( new String[]{ "Berlin", "Paris", "Moscou" } ), "Moscou" ) );
        this.questions.add( new Question( "Qual a resposta da vida, do universo e tudo mais?", Arrays.asList( new String[]{ "32", "42", "52" } ), "42" ) );
        this.questions.add( new Question( "Entre as opcoes, qual nao e um sistema operacional?", Arrays.asList( new String[]{ "Windows", "MAC OS", "Linux" } ), "Windows" ) );
        this.questions.add( new Question( "Qual e a funcao do Internet Explorer?", Arrays.asList( new String[]{ "Navegar na internet", "Abrir e-mails", "Baixar o Firefox" } ), "Baixar o Firefox" ) );
        this.currentQuestion = questions.get( 0 );
    }
}
