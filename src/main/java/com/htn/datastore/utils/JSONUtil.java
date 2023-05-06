package com.htn.datastore.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JSONUtil implements IFileReader, IDataWriter  {
    private Type type;
    private Gson gson;

    public JSONUtil(Type type) {
        this.type = type;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void writeData(String dir, Object data) throws IOException {
        String gson_str = gson.toJson(data, type);

        FileWriter writer = new FileWriter(dir);
        writer.write(gson_str);
        writer.close();
    }

    @Override
    public Object readFile(String dir) throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(dir));
            return gson.fromJson(br, type);
    }
}
