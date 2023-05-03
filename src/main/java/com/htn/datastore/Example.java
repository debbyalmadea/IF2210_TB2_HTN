package com.htn.datastore;

import com.google.gson.reflect.TypeToken;
import com.htn.data.bill.FixedBill;
import com.htn.data.customer.Customer;
import com.htn.data.item.Item;
import com.htn.datastore.utils.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Example {
//
//    // EXAMPLE USAGE FOR HOW TO USE UTIL. Gausah di uncomment karena ga bakal jalan.
//    // Step 1, siapin Array of object yang mau di simpan.
//    ArrayList<Item> baru = new ArrayList();
//        baru.add(new Item("hi", "bill", 3.3, "jdsklaf"));
//    FixedBill Bill = new FixedBill(new Customer(), 9999999, baru);
//    ArrayList<FixedBill> bills = new ArrayList<FixedBill>();
//        bills.add(Bill);
//    Customer customerN = new Customer();
//        customerN.setBills(bills);
//    FixedBill bill2 = new FixedBill(customerN,3424, baru);
//    ArrayList<FixedBill> temp= new ArrayList<>();
//        temp.add(bill2);
//        temp.add(bill2);
//
//    // Step 1.2, harus ngecast Array of Object ke dalam Type. Ini bagian paling aneh.
//    Type type = new TypeToken<ArrayList<FixedBill>>() {}.getType();
//
//    // Step 2, instantiate Util library. Done!
//    IFileReader XMLreader = new XMLUtil(type);
//    IDataWriter XMLwriter = new XMLUtil(type);
//        XMLwriter.writeData("hifromXML", temp);
//    Object result = XMLreader.readFile("hifromXML");

//    // Step 3: Hasil read masih object, harus di cast ke array list yang sesuai. Jangan lupa!
//    ArrayList<FixedBill> resultCast =(ArrayList<FixedBill>) result;
//        System.out.println(resultCast.get(0).toGSONString());
//
//    IFileReader JSONreader = new JSONUtil(type);
//    IDataWriter JSONwriter = new JSONUtil(type);
//        JSONwriter.writeData("hifromJSON", temp);
//    result = JSONreader.readFile("hifromJSON");
//    resultCast =(ArrayList<FixedBill>) result;
//        System.out.println(resultCast.get(0).toGSONString());
//
//    IFileReader OBJreader = new OBJUtil(type);
//    IDataWriter OBJwriter = new OBJUtil(type);
//        OBJwriter.writeData("hifromOBJJ", temp);
//    result = OBJreader.readFile("hifromOBJJ");
//    resultCast =(ArrayList<FixedBill>) result;
//        System.out.println(resultCast.get(0).toGSONString());

}
