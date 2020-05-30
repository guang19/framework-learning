package com.github.guang19.jvm.classloader;

/**
 * @author yangguang
 * @date 2020/3/5
 * @description <p></p>
 */
public class ArrayClassLoaderTest
{

    public static void main(String[] args)
    {
        int[] intArr = {1,2,3,4,5};
        String[] strArr = {"a","b","c"};
        Object[] objArr = {"a","b","c"};

        ArrayClassLoaderTest[] tArr = {new ArrayClassLoaderTest(),new ArrayClassLoaderTest()};


        //数组的类加载器返回数组元素的类型的类加载器
        //bootstrap
        System.out.println(intArr.getClass().getClassLoader());
        //bootstrap
        System.out.println(strArr.getClass().getClassLoader());
        //bootstrap
        System.out.println(objArr.getClass().getClassLoader());
        //app
        System.out.println(tArr.getClass().getClassLoader());


        //int , String ,Object等基础类都是由BootStrapClassLoader加载的
        System.out.println(int.class.getClassLoader());
        System.out.println(Object.class.getClassLoader());
        System.out.println(String.class.getClassLoader());
        //T类是由AppClassLoader加载的
        System.out.println(ArrayClassLoaderTest.class.getClassLoader());

//        System.out.println();
    }
}

