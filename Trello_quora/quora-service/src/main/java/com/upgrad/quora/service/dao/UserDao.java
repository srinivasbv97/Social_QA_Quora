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
}
