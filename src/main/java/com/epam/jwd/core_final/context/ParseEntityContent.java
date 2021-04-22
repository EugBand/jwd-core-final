package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.domain.BaseEntity;

import java.io.File;
import java.util.List;

public interface ParseEntityContent<T> {
    void parseEntity(File sourceFile, List<T> entities);
}
