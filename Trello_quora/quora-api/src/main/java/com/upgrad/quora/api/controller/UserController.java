package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SigninResponse;
import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.business.SigninService;
import com.upgrad.quora.service.business.SignupBusinessSevice;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private SignupBusinessSevice signupBusinessService;

    @Autowired
    private SigninService signinService;
/*This is Post Method , mapped to "/signup", it recieves SignupUserRequest and returns SignupUserResponse. */
    @RequestMapping(method= RequestMethod.POST, path="user/signup", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(final SignupUserRequest signupUserRequest) throws SignUpRestrictedException {
        final UserEntity userEntity = new UserEntity();
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(signupUserRequest.getFirstName());
        userEntity.setLastName(signupUserRequest.getLastName());
        userEntity.setUserName(signupUserRequest.getUserName());
        userEntity.setEmail(signupUserRequest.getEmailAddress());
        userEntity.setPassword(signupUserRequest.getPassword());
        userEntity.setSalt("1234abc");
        userEntity.setCountry(signupUserRequest.getCountry());
        userEntity.setAboutMe(signupUserRequest.getAboutMe());
        userEntity.setDob(signupUserRequest.getDob());
       userEntity.setContactNumber(signupUserRequest.getContactNumber());
       userEntity.setRole("nonadmin");

        final UserEntity createdUserEntity = signupBusinessService.createUser(userEntity);
        SignupUserResponse userResponse = new SignupUserResponse().id(createdUserEntity.getUuid()).status("REGISTERED");
        return new ResponseEntity<SignupUserResponse>(userResponse, HttpStatus.CREATED);
    }

    /*This is a Post metod, that accepts "authorization" Request Header and returns Respones with id, Message.
    There is also Token provided in the header , for further endpoints authenication
     */
    @RequestMapping(method = RequestMethod.POST, path = "/user/sigin",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SigninResponse> sigin(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {

        /*Decoding Base64 Encoded authorization String into Byte[]*/
        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
        String decodedText = new String(decode);
        /*Decoded String contains username:password, which is split in below manner*/
        String[] decodedArray = decodedText.split(":");

        UserAuthEntity userAuthToken = signinService.authenticate(decodedArray[0],decodedArray[1]);

        UserEntity user = userAuthToken.getUserEntity();

        /*UUID is to be passed as String */
        UUID id_user = (UUID.fromString(user.getUuid()));
        String UUID = id_user.toString();

        /*Response Object with Response Body of id, Message and Header of JWT Token*/
        SigninResponse signinResponse = new SigninResponse().id(UUID).message("SIGNED IN SUCCESSFULLY");
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", userAuthToken.getAccessToken());
        return new ResponseEntity<SigninResponse>(signinResponse,headers, HttpStatus.OK);

    }

}
