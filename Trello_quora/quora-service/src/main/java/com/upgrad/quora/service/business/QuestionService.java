package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserAuthDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

  @Autowired private UserAuthDao userAuthDao;

  @Autowired private UserDao userDao;

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

  /**
   * Gets all the questions posted by a specific user.
   *
   * @param userId userId of the user whose posted questions have to be retrieved
   * @param accessToken accessToken of the user for valid authentication.
   * @return List of QuestionEntity
   * @throws AuthorizationFailedException ATHR-001 - if user token is not present in DB. ATHR-002 if
   *     the user has already signed out.
   */
  public List<QuestionEntity> getAllQuestionsByUser(final String userId, final String accessToken)
          throws AuthorizationFailedException, UserNotFoundException {
    UserAuthEntity userAuthEntity = userAuthDao.getUserAuthByToken(accessToken);
    if (userAuthEntity == null) {
      throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
    } else if (userAuthEntity.getLogoutAt() != null) {
      throw new AuthorizationFailedException(
              "ATHR-002",
              "User is signed out.Sign in first to get all questions posted by a specific user");
    }
    UserEntity user = userDao.getUserById(userId);
    if (user == null) {
      throw new UserNotFoundException(
              "USR-001", "User with entered uuid whose question details are to be seen does not exist");
    }
    return questionDao.getAllQuestionsByUser(user);
  }

}