package com.upgrad.quora.service.dao;


import com.upgrad.quora.service.entity.UserAuthEntity;

import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {


    @PersistenceContext
    private EntityManager entityManager;
//Persist the data into DB, using DAO object
    /**
     * This methods stores the user details in the DB. This method receives the object of UserEntity
     * type with its attributes being set.
     */
    public UserEntity createUser(UserEntity userEntity){
        entityManager.persist(userEntity);
        return userEntity;
    }
//Returns null if UserEmail is not present in DB
    public String getUserByEmail(final String email) {
        try {
            UserEntity User_with_same_email = entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email)
                    .getSingleResult();
            return User_with_same_email.getUserName();
        } catch (NoResultException nre) {
            return null;
        }
    }
//Return null if UserName is not present in DB
    public String getUserByUsername(final String username) {
        try {
            UserEntity User_with_same_Username = entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter("userName", username)
                    .getSingleResult();
            return User_with_same_Username.getUserName();

        } catch (NoResultException nre) {
            return null;
        }
    }
    /*This method recieves the Username and returns UserEntity Object
    for authenication of User.
     */
    public UserEntity getUserByUsername_for_auth(final String username) {
        try {
            return entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter("userName", username)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UserAuthEntity createAuthToken(final UserAuthEntity userAuthTokenEntity){
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }
    public void updateUser(final UserEntity updatedUserEntity){
        entityManager.merge(updatedUserEntity);
    }








  /**
   * This methods gets the user details based on the username passed.
   *
   * @param userName username of the user whose information is to be fetched.
   * @return null if the user with given username doesn't exist in DB.
   */
  public UserEntity getUserByUserName(final String userName) {
    try {
      return entityManager
          .createNamedQuery("userByUserName", UserEntity.class)
          .setParameter("userName", userName)
          .getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }




  public void updateUserEntity(final UserEntity updatedUserEntity) {
    entityManager.merge(updatedUserEntity);
  }

  /**
   * Delete a user by given id from the DB.
   *
   * @param userId Id of the user whose information is to be fetched.
   * @return User details which is to be deleted if exist in the DB else null.
   */
  public UserEntity deleteUser(final String userId) {
    UserEntity deleteUser = getUserById(userId);
    if (deleteUser != null) {
      this.entityManager.remove(deleteUser);
    }
    return deleteUser;
  }


    public void updateuserAuthentity(final UserAuthEntity updatedUserAuthEntity){
        entityManager.merge(updatedUserAuthEntity);
    }

    public UserAuthEntity getAuthToken(final String accessToken){
        try {
            return entityManager.createNamedQuery("userAuthByAccessToken", UserAuthEntity.class).setParameter("accessToken", accessToken)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }



    public UserEntity getUserById(final String userId) {
        try {
            return entityManager
                    .createNamedQuery("userByUserId", UserEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
