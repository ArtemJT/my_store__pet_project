package com.example.my_store__pet_project.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Logger {

    public static String logResponse(String response) {
        log.info(response);
        return response;
    }

    public static void logInvokedMethod() {
        log.info("METHOD {{}} WAS CALLED", Thread.currentThread().getStackTrace()[2].getMethodName());
    }
}
