package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerDao answerDao;
    private QuestionDao questionDao;
    public AnswerEntity CreateAnswer (AnswerEntity answerEntity)
    {
        return answerDao.createAnswer(answerEntity);

    }

    public List<AnswerEntity> getAllAnswersByQuestion(final String questionID, final String accessToken)
            throws AuthorizationFailedException, UserNotFoundException {
       // UserAuthEntity userAuthEntity = userAuthDao.getUserAuthByToken(accessToken);

        QuestionEntity question = questionDao.getQuestionById(questionID);
        if (question == null) {
            throw new UserNotFoundException(
                    "USR-001", "User with entered uuid whose question details are to be seen does not exist");
        }
        return answerDao.getAllAnswersByQuestion(question);
    }

}
