package br.com.senac.ccs.thinkfast;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
