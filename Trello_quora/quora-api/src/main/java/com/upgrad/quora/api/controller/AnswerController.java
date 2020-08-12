package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerDetailsResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")

public class AnswerController {

    @RequestMapping(method = RequestMethod.POST, path = "/answer/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AnswerDetailsResponse> CreateQuestion(final AnswerDetailsResponse answerDetailsResponse) {

        return null;

    }


}
