package com.htn.datastore.utils;

import java.io.IOException;

// PLEASE CAST THE RETURN OF THIS VALUE BACK INTO WHATEVER YOU WROTE
public interface IFileReader {
    Object readFile(String dir) throws IOException;
}
