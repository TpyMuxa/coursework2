package pro.sky.hw2.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.hw2.exception.QuestionAlreadyExistsException;
import pro.sky.hw2.exception.QuestionNotFoundException;
import pro.sky.hw2.exception.QuestionsAreEmptyException;
import pro.sky.hw2.model.Question;
import pro.sky.hw2.service.QuestionService;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    @AfterEach
    public void afterEach() {
        Collection<Question> questions = new ArrayList<>(questionService.getAll());
        for (Question question : questions) {
            questionService.remove(question);
        }
    }

    @BeforeEach
    public void beforeEach() {
        for (int i = 1; i <= 5; i++) {
            questionService.add(new Question("Вопрос " + i, "Ответ " + i));
        }
    }

    @Test
    public void addPositiveTest() {
        int before = questionService.getAll().size();
        Question expected = new Question("Вопрос 6", "Ответ 6");

        assertThat(questionService.add("Вопрос 6", "Ответ 6")).isEqualTo(expected);
        assertThat(questionService.getAll())
                .hasSize(before + 1)
                .contains(expected);

        expected = new Question("Вопрос 7", "Ответ 7");
        assertThat(questionService.add(expected)).isEqualTo(expected);
        assertThat(questionService.getAll())
                .hasSize(before + 2)
                .contains(expected);
    }

    @Test
    public void addNegativeTest() {
        assertThat(questionService.getAll())
                .contains(new Question("Вопрос 5", "Ответ 5"));

        assertThatExceptionOfType(QuestionAlreadyExistsException.class)
                .isThrownBy(() -> questionService.add("Вопрос 5", "Ответ 5"));
        assertThatExceptionOfType(QuestionAlreadyExistsException.class)
                .isThrownBy(() -> questionService.add("Вопрос 5", "Ответ 5"));
    }

    @Test
    public void removePositiveTest() {
        int before = questionService.getAll().size();
        Question expected = new Question("Вопрос 5", "Ответ 5");

        assertThat(questionService.remove(new Question("Вопрос 5", "Ответ 5"))).isEqualTo(expected);
        assertThat(questionService.getAll())
                .hasSize(before - 1)
                .doesNotContain(expected);
    }

    @Test
    public void removeNegativeTest() {
        assertThat(questionService.getAll())
                .doesNotContain(new Question("Вопрос 6", "Ответ 6"));

        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(new Question("Вопрос 6", "Ответ 6")));
    }

    @Test
    public void getAllTest() {
        assertThat(questionService.getAll())
                .hasSize(5)
                .containsExactlyInAnyOrder(
                        new Question("Вопрос 1", "Ответ 1"),
                        new Question("Вопрос 2", "Ответ 2"),
                        new Question("Вопрос 3", "Ответ 3"),
                        new Question("Вопрос 4", "Ответ 4"),
                        new Question("Вопрос 5", "Ответ 5")
                );
    }

    @Test
    public void getRandomQuestionPositiveTest() {
        Question actual = questionService.getRandomQuestion();
        assertThat(actual).isIn(questionService.getAll());
    }

    @Test
    public void getRandomQuestionNegativeTest() {
        afterEach();

        assertThatExceptionOfType(QuestionsAreEmptyException.class)
                .isThrownBy(questionService::getRandomQuestion);
    }
}