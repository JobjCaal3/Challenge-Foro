package com.foro.api.domain.Answer;

public record DtoCreateAnswer(String message,
                              Long idUser,
                              Long idTopic) {
}
