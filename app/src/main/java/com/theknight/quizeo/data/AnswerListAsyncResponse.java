package com.theknight.quizeo.data;

import com.theknight.quizeo.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
