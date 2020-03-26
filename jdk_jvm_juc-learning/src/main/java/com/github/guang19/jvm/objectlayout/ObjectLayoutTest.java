package com.github.guang19.jvm.objectlayout;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author yangguang
 * @date 2020/3/26
 * @description <p></p>
 */
public class ObjectLayoutTest
{

    private static final ObjectLayoutTest obj = new ObjectLayoutTest();

    public static void main(String[] args)
    {


        System.out.println(obj.hashCode()); //hashcode = 288665596

        System.out.println(Integer.toHexString(obj.hashCode())); // hashcode>>16进制 = 1134affc = 11 34 af fc

        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

    }
}
