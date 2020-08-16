package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
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

  @RequestMapping(method = RequestMethod.POST, path = "/answer/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> createAnswer(@RequestHeader("authorization") final String accessToken, AnswerRequest answerRequest)throws AuthorizationFailedException
    {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAnswer(answerRequest.getAnswer());
        answerEntity = answerService.CreateAnswer(answerEntity);

        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setId(answerEntity.getUuid());
        answerResponse.setStatus("ANSWER CREATED");
        return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.CREATED);
    }

//Edit Answer endpoint.

    @RequestMapping(method = RequestMethod.POST, path = "/answer/edit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerResponse> editAnswerContent (@RequestHeader("authorization") final String accessToken, AnswerEditRequest answerEditRequest)throws AuthorizationFailedException
    {
        AnswerEntity answerEditEntity = new AnswerEntity();
        answerEditEntity.setAnswer(answerEditRequest.getContent());
        answerEditEntity = answerService.CreateAnswer(answerEditEntity);

        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setId(answerEditEntity.getUuid());
        answerResponse.setStatus("ANSWER EDITED");
        return new ResponseEntity<AnswerResponse>(answerResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/answer/all/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AnswerDetailsResponse>> getAllAnswersToQuestion (@RequestHeader("authorization") final String accessToken,
                                                                           @PathVariable("questionId") String questionID)
            throws AuthorizationFailedException, UserNotFoundException
    {
        List<AnswerEntity> answers = answerService.getAllAnswersByQuestion(questionID, accessToken);
        List<AnswerDetailsResponse> ansDetailResponses = new ArrayList<>();
        for (AnswerEntity answerEntity : answers) {
            AnswerDetailsResponse answerDetailResponses = new AnswerDetailsResponse();
            answerDetailResponses.setId(answerEntity.getUuid());
            answerDetailResponses.setAnswerContent (answerEntity.getAnswer());
            ansDetailResponses.add(answerDetailResponses);
        }
        return new ResponseEntity<>(ansDetailResponses, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/answer/delete/{answerId}")
    public ResponseEntity<AnswerDeleteResponse> deleteAnswer(@RequestHeader("authorization") final String accessToken, @PathVariable("answerId") final String answerId) throws AuthorizationFailedException, InvalidQuestionException {
        AnswerEntity questionEntity = answerService.deleteAnswer (accessToken, answerId);
        AnswerDeleteResponse answerDeleteResponse = new AnswerDeleteResponse();
        answerDeleteResponse.setId(questionEntity.getUuid());
        answerDeleteResponse.setStatus("ANSWER DELETED!");
        return new ResponseEntity<AnswerDeleteResponse>(answerDeleteResponse, HttpStatus.OK);
    }


}
