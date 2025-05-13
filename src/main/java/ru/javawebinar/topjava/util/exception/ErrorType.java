package ru.javawebinar.topjava.util.exception;

public enum ErrorType {
    APP_ERROR("Ошибка приложения"),
    DATA_NOT_FOUND("Данные не найдены"),
    DATA_ERROR("Ошибка данных"),
    VALIDATION_ERROR("Ошибка проверки данных");

    public final String title;

    ErrorType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
