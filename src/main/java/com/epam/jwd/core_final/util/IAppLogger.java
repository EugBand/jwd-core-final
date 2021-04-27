package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.util.enums.LogTypes;

public interface IAppLogger {

    void log(LogTypes category, String message, Throwable throwable);

    void log(LogTypes category, String... messages);
}
