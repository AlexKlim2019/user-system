package com.klymenko.user.system.task.service.domain.utils;


import java.util.Locale;

import static com.klymenko.user.system.task.service.domain.constants.DomainConstants.MESSAGE_DELIMITER;

public class StringUtils {
    public static String concatenate(String ... strings) {
        return String.join(MESSAGE_DELIMITER, strings);
    }

    public static String normalizeEmail(String email) {
        return email.toLowerCase(Locale.getDefault()).trim();
    }
}
