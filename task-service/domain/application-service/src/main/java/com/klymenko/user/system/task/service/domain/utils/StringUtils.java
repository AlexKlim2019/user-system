package com.klymenko.user.system.task.service.domain.utils;


import static com.klymenko.user.system.task.service.domain.constants.DomainConstants.MESSAGE_DELIMITER;

public class StringUtils {
    public static String concatenate(String ... strings) {
        return String.join(MESSAGE_DELIMITER, strings);
    }
}
