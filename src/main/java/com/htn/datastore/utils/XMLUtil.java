package com.htn.datastore.utils;

import java.io.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class XMLUtil implements IDataWriter, IFileReader {
    private XStream xstream;

    public XMLUtil(Type type) {
        xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
    }

    @Override
    public void writeData(String dir, Object data) throws IOException {
        String xmlString = xstream.toXML(data);
        FileWriter writer = new FileWriter(dir);
        writer.write(xmlString);
        writer.close();
    }

    @Override
    public Object readFile(String dir) throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(dir));
            return xstream.fromXML(br);
    }
}



