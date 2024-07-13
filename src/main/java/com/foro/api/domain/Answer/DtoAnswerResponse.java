package com.foro.api.domain.Answer;

import com.foro.api.domain.user.DtoUserBriefResponse;
import com.foro.api.domain.user.User;

import java.util.Date;

public record DtoAnswerResponse(String message,
                                DtoUserBriefResponse user,
                                Date creationDate) {
    public DtoAnswerResponse(Answer answer, User user) {
        this(answer.getMessage(),new DtoUserBriefResponse(user),answer.getCreationDate());
    }

    public DtoAnswerResponse(Answer answer) {
        this(answer.getMessage(),new DtoUserBriefResponse(answer.getUser()),answer.getCreationDate());
    }
}
