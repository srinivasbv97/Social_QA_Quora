package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserAuthDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

  @Autowired private UserAuthDao userAuthDao;


  @Autowired private QuestionDao questionDao;


  /**
   * Gets all the questions in the DB.
   *
   * @param accessToken accessToken of the user for valid authentication.
   * @return List of QuestionEntity
   * @throws AuthorizationFailedException ATHR-001 - if user token is not present in DB. ATHR-002 if
   *     the user has already signed out.
   */
  public List<QuestionEntity> getAllQuestions(final String accessToken)
      throws AuthorizationFailedException {
    UserAuthEntity userAuthEntity = userAuthDao.getUserAuthByToken(accessToken);
    if (userAuthEntity == null) {
      throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
    } else if (userAuthEntity.getLogoutAt() != null) {
      throw new AuthorizationFailedException(
          "ATHR-002", "User is signed out.Sign in first to get all questions");
    }
    return questionDao.getAllQuestions();
  }

}
