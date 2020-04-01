package org.wzl.yanyan;

import java.util.Arrays;
import java.util.Random;

/**
 * Random
 */
public class Randomtest {

    public final static int  ALL = 7000;

    public static void main(String[] args) {

        int [] a =  new int[15];
        
         Random a1 = new Random();
        for (int i = 0; i < a.length; i++) {
            int d1 = a1.nextInt(ALL);
            a[i] = d1;
        }
        System.out.println(Arrays.toString(a));
    }
}