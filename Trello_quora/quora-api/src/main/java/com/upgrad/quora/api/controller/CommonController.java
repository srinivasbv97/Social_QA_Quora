package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.UserprofileService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CommonController {

    @Autowired
    private UserprofileService userprofileService;

    @RequestMapping(method = RequestMethod.GET, path = "/users/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("userId") final String userUuid, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, UserNotFoundException {
        String[] bearerToken = authorization.split("Bearer ");
        final UserEntity userEntity  = userprofileService.getUserProfile(userUuid,bearerToken[1]);

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).emailAddress(userEntity.getEmail())
                .country(userEntity.getCountry()).aboutMe(userEntity.getAboutMe()).dob(userEntity.getDob()).contactNumber(userEntity.getContactNumber());



        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }

}
