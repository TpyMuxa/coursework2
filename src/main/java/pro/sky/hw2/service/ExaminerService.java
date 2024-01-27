package pro.sky.hw2.service;


import pro.sky.hw2.model.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);
}
