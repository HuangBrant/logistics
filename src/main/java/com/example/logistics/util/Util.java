package com.example.logistics.util;


public class Util {
    private static String[] colors = {"blue", "green", "orange", "red","purple",
    "dark","teal","pink","gray"};


    public static String[] getColors(Integer size){
        String[] color = new String[size];
        for (int i = 0;i<size;i++){
            color[i] = colors[i % colors.length];
        }
        return color;
    }
}
