package com.iksoft.recyclview;

public class Important
{
    private static final String Main_Url = "http://192.168.0.100:8080/androidapp/important/";
    public static final String URL_REGISTER = Main_Url +"registeruser.php";
    public static final String URL_LOGIN = Main_Url +"userlogin.php";

    public static String getMain_Url()
    {
        return Main_Url;
    }
}
