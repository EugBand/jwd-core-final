package com.epam.jwd.core_final.printer.impl;


import com.epam.jwd.core_final.printer.IAppPrinter;
import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;

import java.util.Arrays;

import static com.epam.jwd.core_final.util.enums.LogTypes.ERROR;

public class AppConsolePrinter implements IAppPrinter {

    private static IAppPrinter instance;
    private IAppLogger logger = AppLogger.getInstance();


    private AppConsolePrinter() {
    }

    public static IAppPrinter getInstance() {
        if (instance == null) {
            instance = new AppConsolePrinter();
        }
        return instance;
    }

    public IAppPrinter printWaiting(int interval, int amount, char ch) {

        for (int i = 0; i < amount; i++) {
            try {
                Thread.sleep(interval);
                System.out.print(ch);
            } catch (InterruptedException e) {
                logger.log(ERROR, "Can't interrupt thread", e);
            }
        }
        return this;
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void print(String... messages) {
        System.out.println(Arrays.toString(messages));
    }

    @Override
    public IAppPrinter print(IAppPrinter nextPrinter, String message) {
        System.out.println(message);
        return nextPrinter;
    }

    @Override
    public IAppPrinter print(IAppPrinter nextPrinter, String... messages) {
        System.out.println(Arrays.toString(messages));
        return nextPrinter;
    }
}
