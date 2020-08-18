package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")

public class AnswerController {
   @Autowired
   private AnswerService answerService;

   @Autowired
   private UserDao userDao;

    @Autowired private AnswerDao answerDeo;


   //Create answer
     @RequestMapping(method = RequestMethod.POST, path = "/question/{questionId}/answer/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(@RequestHeader("authorization") final String accessToken, AnswerRequest answerRequest, @PathVariable String questionId) throws AuthorizationFailedException, InvalidQuestionException {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAnswer(answerRequest.getAnswer());
        answerEntity = answerService.CreateAnswer(answerEntity, accessToken,questionId);

        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setId(answerEntity.getUuid());
        answerResponse.setStatus("ANSWER CREATED");
        return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.CREATED);
    }

//Edit Answer endpoint.

    @RequestMapping(method = RequestMethod.POST, path = "/answer/edit/{answerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerEditResponse> editAnswerContent ( @RequestHeader("authorization") final String accessToken,
                                                              @PathVariable("answerId") final String answerId,
                                                              AnswerEditRequest answerEditRequest)
            throws AuthorizationFailedException, AnswerNotFoundException {

        AnswerEntity answerEntity = answerService.editAnswer(accessToken, answerId, answerEditRequest.getContent());
        AnswerEditResponse answerEditResponse = new AnswerEditResponse();
        answerEditResponse.setId(answerEntity.getUuid());
        answerEditResponse.setStatus("ANSWER EDITED");
        return new ResponseEntity<AnswerEditResponse>(answerEditResponse, HttpStatus.OK);

    }


    //Get all answers of question.
    @RequestMapping(method = RequestMethod.GET, path = "/answer/all/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AnswerDetailsResponse>> getAllAnswersToQuestion (@RequestHeader("authorization") final String accessToken,
                                                                           @PathVariable("questionId") String questionId)
            throws AuthorizationFailedException, InvalidQuestionException, AnswerNotFoundException
    {
        List<AnswerEntity> answers = answerService.getAllAnswersByQuestion(questionId, accessToken);
        List<AnswerDetailsResponse> ansDetailResponses = new ArrayList<>();
        for (AnswerEntity answerEntity : answers) {
            AnswerDetailsResponse answerDetailResponses = new AnswerDetailsResponse();
            answerDetailResponses.setId(answerEntity.getUuid());
            answerDetailResponses.setAnswerContent (answerEntity.getAnswer());
            ansDetailResponses.add(answerDetailResponses);
        }
        return new ResponseEntity<>(ansDetailResponses, HttpStatus.OK);

    }

    //Delete answer
    @RequestMapping(method = RequestMethod.DELETE, path = "/answer/delete/{answerId}")
    public ResponseEntity<AnswerDeleteResponse> deleteAnswer(@RequestHeader("authorization") final String accessToken, @PathVariable("answerId") final String answerId) throws AuthorizationFailedException, AnswerNotFoundException {
        AnswerEntity answerEntity = answerService.deleteAnswer (accessToken, answerId);
        AnswerDeleteResponse answerDeleteResponse = new AnswerDeleteResponse();
        answerDeleteResponse.setId(answerEntity.getUuid());
        answerDeleteResponse.setStatus("ANSWER DELETED!");
        return new ResponseEntity<AnswerDeleteResponse>(answerDeleteResponse, HttpStatus.OK);
    }


}
