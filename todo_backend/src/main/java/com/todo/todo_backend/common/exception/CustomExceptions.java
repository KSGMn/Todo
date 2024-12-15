package com.todo.todo_backend.common.exception;

public class CustomExceptions {

    // 모든 예외를 위한 클래스 작성
    public static class AllException extends RuntimeException {

        public AllException(String message) {
            super(message);
        }

    }

    // BadRequest를 위한 클래스 생성
    public static class BadRequestException extends RuntimeException {

        public BadRequestException(String message) {
            super(message);
        }

    }

    // ConflictException을 위한 클래스 생성
    public static class ConflictException extends RuntimeException {

        public ConflictException(String message) {
            super(message);
        }

    }

    // NotFoundException을 위한 클래스 생성
    public static class NotFoundException extends RuntimeException {

        public NotFoundException(String message) {
            super(message);
        }

    }

    // UnauthorizedException 위한 클래스 생성
    public static class UnauthorizedException extends RuntimeException {

        public UnauthorizedException(String message) {
            super(message);
        }

    }

}
