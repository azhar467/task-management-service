package com.azhar.taskmanagement.exception;

public class UserNotFoundException extends EntityNotFoundException {
  public UserNotFoundException(Long userId) {
    super("User", userId);
  }
}