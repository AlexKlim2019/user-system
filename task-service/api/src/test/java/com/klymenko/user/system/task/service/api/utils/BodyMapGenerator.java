package com.klymenko.user.system.task.service.api.utils;

import java.util.Map;

import static com.klymenko.user.system.task.service.api.utils.TestConstants.*;

public class BodyMapGenerator {
    public static class CreateUserPayloadGenerator {

        public static Map<String, Object> generateSignUpUserValidBodyMap() {
            return Map.of(
                    "email", VALID_EMAIL,
                    "password", VALID_PASSWORD,
                    "firstName", FIRST_NAME,
                    "lastName", LAST_NAME
            );
        }

        public static Map<String, Object> generateCreateUserBodyMapWithoutEmail() {
            return Map.of(
                    "password", VALID_PASSWORD,
                    "firstName", FIRST_NAME,
                    "lastName", LAST_NAME
            );
        }

        public static Map<String, Object> generateCreateUserBodyMapWithInvalidEmail(String email) {
            return Map.of(
                    "email", email,
                    "password", VALID_PASSWORD,
                    "firstName", FIRST_NAME,
                    "lastName", LAST_NAME
            );
        }

        public static Map<String, Object> generateCreateUserBodyMapWithoutPassword() {
            return Map.of(
                    "email", VALID_EMAIL,
                    "firstName", FIRST_NAME,
                    "lastName", LAST_NAME
            );
        }

        public static Map<String, Object> generateCreateUserBodyMapWithInvalidPassword(String password) {
            return Map.of(
                    "email", VALID_EMAIL,
                    "password", password,
                    "firstName", FIRST_NAME,
                    "lastName", LAST_NAME
            );
        }

        public static Map<String, Object> generateCreateUserBodyMapWithoutFirstName() {
            return Map.of(
                    "email", VALID_EMAIL,
                    "password", VALID_PASSWORD,
                    "lastName", LAST_NAME
            );
        }

        public static Map<String, Object> generateCreateUserBodyMapWithoutLastName() {
            return Map.of(
                    "email", VALID_EMAIL,
                    "password", VALID_PASSWORD,
                    "firstName", FIRST_NAME
            );
        }

    }

    public static class CreateTaskPayloadGenerator {

        public static Map<String, Object> generateCreateTaskValidBodyMap() {
            return Map.of(
                    "title", VALID_TITLE,
                    "description", VALID_DESCRIPTION,
                    "userId", USER_ID
            );
        }

        public static Map<String, Object> generateCreateTaskBodyMapWithoutTitle() {
            return Map.of(
                    "description", VALID_DESCRIPTION,
                    "userId", USER_ID
            );
        }

        public static Map<String, Object> generateCreateTaskBodyMapWithoutDescription() {
            return Map.of(
                    "title", VALID_TITLE,
                    "userId", USER_ID
            );
        }

        public static Map<String, Object> generateCreateTaskBodyMapWithoutUserId() {
            return Map.of(
                    "title", VALID_TITLE,
                    "description", VALID_DESCRIPTION
            );
        }

        public static Map<String, Object> generateSearchTasksValidBodyMap() {
            return Map.of("userId", USER_ID);
        }

        public static Map<String, Object> generatePatchTaskValidBodyMap() {
            return Map.of(
                    "id", TASK_ID,
                    "title", UPDATED_TITLE,
                    "description", UPDATED_DESCRIPTION
            );
        }

        public static Map<String, Object> generateDeleteTaskValidBodyMap() {
            return Map.of("id", TASK_ID);
        }
    }
}
