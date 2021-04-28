package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.printer.IAppPrinter;
import com.epam.jwd.core_final.printer.impl.AppConsolePrinter;

import java.util.InputMismatchException;
import java.util.Scanner;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {
    AppConsolePrinter printer = (AppConsolePrinter) AppConsolePrinter.getInstance();

    void getApplicationContext();

    default void handleOutput(String message, IAppPrinter... printers) {
        for (IAppPrinter printer : printers){
            printer.print(message);
        }
    }

    default <T> Object handleInput(Class<T> type) {
        Object value;
        while (true) {
            try {
                Scanner input = new Scanner(System.in);
                switch (type.getSimpleName()) {
                    case "Integer":
                        value = input.nextInt();
                        break;
                    case "Boolean":
                        value = input.nextBoolean();
                        break;
                    default:
                        throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException | NumberFormatException ex) {
                handleOutput("Invalid Number, Please try again", this.printer);
            }
        }
        return value;
    }
}
