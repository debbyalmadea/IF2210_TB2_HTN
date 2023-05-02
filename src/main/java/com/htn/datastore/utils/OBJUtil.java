package com.htn.datastore.utils;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class OBJUtil implements IDataWriter, IFileReader {
    private Type type;

    public OBJUtil(Type type) {
        this.type = type;
    }


    @Override
    public Boolean writeData(String dir, Object data) {
        try {
            // Creating a FileOutputStream to hold the serialized object
            FileOutputStream fileOut = new FileOutputStream(dir);

            // Creating an ObjectOutputStream to write the object to the FileOutputStream
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // Serializing the object by writing it to the ObjectOutputStream
            out.writeObject(data);

            // Closing the stream
            out.close();

            // Closing the file output stream
            fileOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Object readFile(String dir) {
        try {
            // Creating a FileInputStream to read the serialized object
            FileInputStream fileIn = new FileInputStream(dir);

            // Creating an ObjectInputStream to read the object from the FileInputStream
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // Deserializing the object by reading it from the ObjectInputStream
            Object deserializedObj = in.readObject();

            // Closing the stream
            in.close();

            // Closing the file input stream
            fileIn.close();

            return deserializedObj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}