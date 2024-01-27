package pro.sky.hw2.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.hw2.exception.NotEnoughQuestionsException;
import pro.sky.hw2.model.Question;
import pro.sky.hw2.service.QuestionService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final List<Question> questions = List.of(
            new Question("Вопрос 1", "Ответ 1"),
            new Question("Вопрос 2", "Ответ 2"),
            new Question("Вопрос 3", "Ответ 3"),
            new Question("Вопрос 4", "Ответ 4"),
            new Question("Вопрос 5", "Ответ 5")
    );

    @BeforeEach
    public void beforeEach() {
        when(questionService.getAll()).thenReturn(questions);
    }

    @Test
    public void getQuestionsNegativeTest() {
        assertThatExceptionOfType(NotEnoughQuestionsException.class)
                .isThrownBy(() -> examinerService.getQuestions(questions.size() + 1));
    }

    @Test
    public void getQuestionsPositiveTest() {
        when(questionService.getRandomQuestion())
                .thenReturn(
                        new Question("Вопрос 2", "Ответ 2"),
                        new Question("Вопрос 4", "Ответ 4"),
                        new Question("Вопрос 4", "Ответ 4"),
                        new Question("Вопрос 4", "Ответ 4"),
                        new Question("Вопрос 1", "Ответ 1"),
                        new Question("Вопрос 1", "Ответ 1"),
                        new Question("Вопрос 5", "Ответ 5")
                );

        assertThat(examinerService.getQuestions(4))
                .containsExactlyInAnyOrder(
                        new Question("Вопрос 1", "Ответ 1"),
                        new Question("Вопрос 2", "Ответ 2"),
                        new Question("Вопрос 4", "Ответ 4"),
                        new Question("Вопрос 5", "Ответ 5")
                );
    }
}