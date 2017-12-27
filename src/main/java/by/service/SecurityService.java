package by.service;

/*
 * Сервис используется для автологина пользователя после регистрации
 */

public interface SecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
