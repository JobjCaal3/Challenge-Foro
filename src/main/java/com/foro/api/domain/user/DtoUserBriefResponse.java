package com.foro.api.domain.user;

public record DtoUserBriefResponse(String fullName) {
    public DtoUserBriefResponse(User user) {
        this(extractFullName(user));
    }

    private static String extractFullName(User user){
        if (user.getStudent() != null){
            return user.getStudent().getFullName();
        }
        return user.getTeacher().getFullName();
    }
}
