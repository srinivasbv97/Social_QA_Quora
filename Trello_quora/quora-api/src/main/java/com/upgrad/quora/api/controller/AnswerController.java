package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.AnswerDetailsResponse;
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.business.QuestionService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/")

public class AnswerController {
   @Autowired
   private AnswerService answerService;

    private QuestionService questionService;
    @RequestMapping(method = RequestMethod.POST, path = "/createanswer/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDetailsResponse> CreateQuestion(final AnswerDetailsResponse answerDetailsResponse, final UserEntity userEntity, final QuestionEntity questionEntity) {

        final AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setUuid(UUID.randomUUID().toString());
        answerEntity.setAnswer(answerDetailsResponse.getAnswerContent());
        answerEntity.setDate(ZonedDateTime.now());
        answerEntity.setUserEntity(userEntity);
        answerEntity.setQuestionEntity(questionEntity);

        answerService.CreateAnswer(answerEntity);

        return new ResponseEntity(HttpStatus.OK);
    }


}
