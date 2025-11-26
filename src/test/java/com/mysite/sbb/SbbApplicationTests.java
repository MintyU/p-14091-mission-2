package com.mysite.sbb;

import com.mysite.sbb.domain.answer.Answer;
import com.mysite.sbb.domain.answer.AnswerRepository;
import com.mysite.sbb.domain.question.Question;
import com.mysite.sbb.domain.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class SbbApplicationTests {

	@Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void t1() {
        List<Question> all = this.questionRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getSubject()).isEqualTo("sbb가 무엇인가요?");
        assertThat(all.get(0).getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");
    }

    @Test
    void t2() {
        Question q = new Question();
        q.setSubject("새로운 질문입니다.");
        q.setContent("테스트가 잘 동작하나요?");
        q.setCreateDate(java.time.LocalDateTime.now());
        this.questionRepository.save(q);

        Question q2 = this.questionRepository.findById(q.getId()).orElse(null);
        assertThat(q2).isNotNull();
        assertThat(q2.getSubject()).isEqualTo("새로운 질문입니다.");
    }

    @Test
    void t3() {
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertThat(q).isNotNull();
        assertThat(q.getId()).isEqualTo(1);
        assertThat(q.getContent()).isEqualTo("sbb에 대해서 알고 싶습니다.");
    }

    @Test
    void t4() {
        Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertThat(q).isNotNull();
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    void t5() {
        List<Question> qList = this.questionRepository.findBySubjectLike("%sbb%");
        Question q = qList.get(0);
        assertThat(q).isNotNull();
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    void t6() {
        assertThat(this.questionRepository.count()).isEqualTo(2);
        Optional<Question> oq = this.questionRepository.findById(1);
        assertThat(oq.isPresent()).isTrue();
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertThat(this.questionRepository.count()).isEqualTo(1);
    }

    @Test
    void t7() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertThat(oq.isPresent()).isTrue();
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setCreateDate(java.time.LocalDateTime.now());
        a.setQuestion(q);
        this.answerRepository.save(a);

        Optional<Answer> oa = this.answerRepository.findById(1);
        assertThat(oa.isPresent()).isTrue();
        Answer a2 = oa.get();
        assertThat(a2.getQuestion().getId()).isEqualTo(2);

        Optional<Question> oq2 = this.questionRepository.findById(2);
        assertThat(oq2.isPresent()).isTrue();
        Question q2 = oq.get();

        List<Answer> answerList = q2.getAnswerList();
        assertThat(answerList.size()).isEqualTo(1);
        assertThat(answerList.get(0).getContent()).isEqualTo("네 자동으로 생성됩니다.");
    }

}
