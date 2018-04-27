package com.netcracker.ibublig.catalog.client;

import com.netcracker.ibublig.catalog.model.User;

/**
 * Hello world!
 *
 */
public class App 
{
    private static User user = new User();
    public static void main( String[] args )
    {
        user.setFirstName("Илья");
        System.out.println(user.getFirstName());
    }
}
