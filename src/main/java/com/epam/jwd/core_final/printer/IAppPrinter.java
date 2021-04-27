package com.epam.jwd.core_final.printer;

public interface IAppPrinter {

    abstract void print(String message);

    abstract void print(String... messages);

    abstract IAppPrinter print(IAppPrinter nextPrinter, String message);

    abstract IAppPrinter print(IAppPrinter nextPrinter, String... messages);
}
