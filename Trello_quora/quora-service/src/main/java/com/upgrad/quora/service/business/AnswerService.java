package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserAuthDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
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

    @Autowired
    private UserAuthDao userAuthDao;

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
//Delete Answer method.
//@Transactional(propagation = Propagation.REQUIRED)
public AnswerEntity deleteAnswer(final String accessToken, final String answerId) throws AuthorizationFailedException, AnswerNotFoundException {
        UserAuthEntity userAuthEntity = userAuthDao.getUserAuthByToken(accessToken);
        if (userAuthEntity == null) {
            throw new AuthorizationFailedException("ANS-001", "User has not signed in");
        } else if (userAuthEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException(
                    "ANS-002", "Sign in to delete the question");
        }
        AnswerEntity answerEntity = answerDao.getAnswerById(answerId);
        if (answerEntity == null) {
            throw new AnswerNotFoundException("ANS-003", "Entered answer with uuid does not exist");
        }
        if (!answerEntity.getUserEntity().getUuid().equals(userAuthEntity.getUserEntity().getUuid())
                && !userAuthEntity.getUserEntity().getRole().equals("admin")) {
            throw new AuthorizationFailedException(
                    "ANS-004", "Only answer owner or Admin can delete this answer!");
        }

        answerDao.deleteAnswer(answerEntity);
        return answerEntity;
    }

}
