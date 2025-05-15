package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";
    private static final String EXCEPTION_DUPLICATE_DATETIME = "Meal with this datetime already exists";

    private static Map<String, String> CONSTRAINTS_MAP = Map.of(
            "users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL,
            "meal_unique_user_datetime_idx", EXCEPTION_DUPLICATE_DATETIME
    );

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("Exception at request " + req.getRequestURL(), e);
        Throwable rootCause = ValidationUtil.getRootCause(e);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ModelAndView mav = new ModelAndView("exception",
                Map.of("exception", rootCause, "message", rootCause.toString(), "status", httpStatus));
        mav.setStatus(httpStatus);

        // Interceptor is not invoked, put userTo
        addAuthorizedUser(mav);
        return mav;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView conflictErrorHandler(HttpServletRequest req, DataIntegrityViolationException e) throws Exception {
        log.error("Exception at request " + req.getRequestURL(), e);
        Throwable rootCause = ValidationUtil.getRootCause(e);
        String rootMsg = rootCause.getMessage();
        ModelAndView mav;
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            for (Map.Entry<String, String> entry : CONSTRAINTS_MAP.entrySet()) {
                if (lowerCaseMsg.contains(entry.getKey())) {
                    mav = new ModelAndView("exception",
                            Map.of("exception", e, "message", entry.getValue(), "status", httpStatus));
                    mav.setStatus(httpStatus);
                    addAuthorizedUser(mav);
                    return mav;
                }
            }
        }

        mav = new ModelAndView("exception",
                Map.of("exception", e, "message", rootCause.toString(), "status", httpStatus));

        mav.setStatus(httpStatus);
        addAuthorizedUser(mav);
        return mav;
    }

    private static void addAuthorizedUser(ModelAndView mav) {
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
    }
}
