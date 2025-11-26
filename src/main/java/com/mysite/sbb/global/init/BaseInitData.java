package com.mysite.sbb.global.init;

import com.mysite.sbb.domain.answer.AnswerRepository;
import com.mysite.sbb.domain.question.Question;
import com.mysite.sbb.domain.question.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    @Autowired
    @Lazy
    private BaseInitData self;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    void work1() {
        if (this.questionRepository.count() > 0) {
            return;
        }

        Question q = new Question();
        q.setSubject("sbb가 무엇인가요?");
        q.setContent("sbb에 대해서 알고 싶습니다.");
        q.setCreateDate(java.time.LocalDateTime.now());
        this.questionRepository.save(q);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(java.time.LocalDateTime.now());
        this.questionRepository.save(q2);

    }
}
