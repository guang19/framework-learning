package com.github.yangguang19.jdk8;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Description : TODO          复习Java8的函数式接口
 * @Author :    yangguang
 * @Date :      2019/11/21
 */
public class FunctionalInterface {
    /**
     * JAVA8提供了4种函数式接口,Stream流式计算,也是基于这四种接口和lambda表达式的:
     *
     * Consumer: 消费型接口,接受参数,但没有返回值
     *
     * Predicate: 判断型接口,接受参数,返回boolean值
     *
     * Function:函数型接口,接受参数,返回值
     *
     * Supplier:供给型接口,不接受参数,但必须返回值
     */

    public static void main(String[] args)
    {
        //消费性接口接受参数没有返回值
        Consumer<String> consumer = System.out::println; //等同于 s -> {System.out.println(s);};

        //判断型接口接受参数,返回boolean类型值
        Predicate<String> predicate = String::isBlank; //等同于 s -> {return s.isBlank();};

        //函数型接口,接受参数,返回值
        Function<Integer,Integer> function = n -> {return n * 3;};

        //供给型接口,不接受参数,但是必须返回值
        Supplier<Integer> supplier = ()->{return 5;};


        consumer.accept("a");
        System.out.println(predicate.test("a"));
        System.out.println(function.apply(5));
        System.out.println(supplier.get());
    }
}
