package com.upgrad.quora.service.business;


import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;


@Service
public class SignoutService {

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity logoutuser(final String accessToken) throws  SignOutRestrictedException {
        UserAuthEntity userAuthEntity = userDao.getAuthToken(accessToken);

        if(userAuthEntity.getAccessToken().equals(accessToken)){
            final ZonedDateTime now = ZonedDateTime.now();
            userAuthEntity.setLogoutAt(now);
            userDao.updateuserAuthentity(userAuthEntity);
           UserEntity userEntity =  userAuthEntity.getUserEntity();
           return userEntity;
        }
        else {
            throw new SignOutRestrictedException("SGR-001","User is not Signed in");
        }
    }
}
