package com.epam.jwd.core_final.context.parser;

import com.epam.jwd.core_final.util.AppLogger;
import com.epam.jwd.core_final.util.IAppLogger;

import java.io.File;
import java.util.List;

public interface IEntityParser<T> {
    IAppLogger logger = AppLogger.getInstance();

    void parseEntity(File sourceFile, List<T> entities);
}
