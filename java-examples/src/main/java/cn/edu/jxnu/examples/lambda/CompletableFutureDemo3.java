/* Licensed under Apache-2.0 @梦境迷离 */
package cn.edu.jxnu.examples.lambda;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Copyright © 2018 梦境迷离. All rights reserved.
 *
 * @description:组合多个CompletableFuture @Package: cn.edu.jxnu.lambda
 * @author: 梦境迷离
 * @date: 2018年3月27日 上午11:31:34
 */
public class CompletableFutureDemo3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("*****************方法一*******************");
        // 1、使用thenCompose()方法
        // 2、将第一个CompletableFuture的计算结果传递给第二个CompletableFuture，继续进行计算
        CompletableFuture<Void> fu =
                CompletableFuture.supplyAsync(() -> calu(50))
                        .thenCompose(i -> CompletableFuture.supplyAsync(() -> calu(i)))
                        .thenApply(i -> Integer.toString(i))
                        .thenApply(str -> "\"" + str + "\"")
                        .thenAccept(System.out::println);
        fu.get();
        System.out.println("*****************方法二*******************");
        // 1、使用thenCombine()方法
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> calu(50));
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> calu(25));
        // 将future1与future2的计算结果相加
        CompletableFuture<Void> fu2 =
                future1.thenCombine(future2, (i, j) -> (i + j))
                        .thenApply(str -> "\"" + str + "\"")
                        .thenAccept(System.out::println);
        fu2.get();
    }

    public static Integer calu(Integer param) {
        return param / 2;
    }
}
