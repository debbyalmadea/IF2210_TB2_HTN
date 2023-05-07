package com.htn.datastore.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class IOUtilFactory {
    public static @Nullable IDataWriter getWriter(@NotNull String extension, Type type) {
        if (extension.equalsIgnoreCase(".json")) return new JSONUtil(type);
        if (extension.equalsIgnoreCase(".xml")) return new XMLUtil(type);
        if (extension.equalsIgnoreCase(".obj")) return new OBJUtil(type);

        return null;
    }
    public static @Nullable IFileReader getReader(@NotNull String extension, Type type) {
        if (extension.equalsIgnoreCase(".json")) return new JSONUtil(type);
        if (extension.equalsIgnoreCase(".xml")) return new XMLUtil(type);
        if (extension.equalsIgnoreCase(".obj")) return new OBJUtil(type);

        return null;
    }
}
