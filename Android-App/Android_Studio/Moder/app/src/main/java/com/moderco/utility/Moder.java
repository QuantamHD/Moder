package com.moderco.utility;

/**
 * Created by Ethan on 3/15/2015.
 */
public class Moder {
    private static String userName;
    private static String password;
    private static boolean initialized = false;


    public static void init(){
        if(initialized){//If init has already been called
            return;
        }


        initialized = true;
    }


}
