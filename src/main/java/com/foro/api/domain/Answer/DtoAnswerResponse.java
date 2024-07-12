package com.foro.api.domain.Answer;

import com.foro.api.domain.user.DtoUserBriefResponse;
import com.foro.api.domain.user.User;

public record DtoAnswerResponse(String message,
                                DtoUserBriefResponse user) {
    public DtoAnswerResponse(Answer answer, User user) {
        this(answer.getMessage(), new DtoUserBriefResponse(user));
    }

}
