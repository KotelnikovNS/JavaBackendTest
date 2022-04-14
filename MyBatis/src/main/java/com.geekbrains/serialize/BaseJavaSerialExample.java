package com.geekbrains.serialize;

import com.geekbrains.spoonaccular.model.RecipesSearchResponseItem;

import java.io.*;

public class BaseJavaSerialExample {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        RecipesSearchResponseItem item;


        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serial_file.txt"));
        item = (RecipesSearchResponseItem) ois.readObject();
        System.out.println(item);
    }
}
