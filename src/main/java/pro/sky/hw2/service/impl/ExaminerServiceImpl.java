package pro.sky.hw2.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.hw2.exception.NotEnoughQuestionsException;
import pro.sky.hw2.model.Question;
import pro.sky.hw2.service.ExaminerService;
import pro.sky.hw2.service.QuestionService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (questionService.getAll().size() < amount) {
            throw new NotEnoughQuestionsException();
        }

        Set<Question> questions = new HashSet<>();
        while (questions.size() < amount) {
            questions.add(questionService.getRandomQuestion());
        }
        return questions;
    }
}
