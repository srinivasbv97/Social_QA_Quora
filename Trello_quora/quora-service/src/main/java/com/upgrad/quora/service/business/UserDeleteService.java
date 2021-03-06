package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDeleteService {


    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity deleteUser(final String Uuid, final String authorization) throws UserNotFoundException, AuthorizationFailedException {
        UserEntity userEntity = userDao.getUserById(Uuid);
        UserAuthEntity userAuthEntity = userDao.getAuthToken(authorization);

        if(userEntity == null){
            throw new UserNotFoundException("USR-001","User with entered uuid to be deleted does not exist");
        }

        else if(userAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001","User has not signed in");
        }

        else if(userAuthEntity.getLogoutAt() != null){
            throw new AuthorizationFailedException("ATHR-002","User is signed out.");
        }
        else if (userAuthEntity.getUserEntity().getRole().equals("nonadmin")){
            throw new AuthorizationFailedException("ATHR-003","Unauthorized Access, Entered user is not an admin");
        }
        else{
            UserEntity userEntity1 = userDao.deleteUser(Uuid);
            return userEntity1;
        }
    }
}

