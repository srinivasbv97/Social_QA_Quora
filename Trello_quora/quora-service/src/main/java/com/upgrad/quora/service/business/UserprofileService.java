package com.upgrad.quora.service.business;


import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserprofileService {

    @Autowired
    private UserDao userDao;

    public UserEntity getUserProfile(final String Uuid, final String authorization) throws UserNotFoundException, AuthorizationFailedException {
        UserEntity userEntity = userDao.getUserById(Uuid);
        UserAuthEntity userAuthEntity = userDao.getAuthToken(authorization);
        if(userEntity == null){
            throw new UserNotFoundException("USR-001","User with entered uuid does not exist");
        }

        else if(userAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }

        else if(userAuthEntity.getLoginAt() != null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
        }
        else{
            return userEntity;
        }
    }
}
