/* All Contributors (C) 2021 */
package io.github.poorguy.util;

import io.github.poorguy.annotation.NotNull;
import java.lang.reflect.Method;

public class LeetcodeRunner<T> {
    /** @param invoker 调用该工具方法的对象 */
    public <C> void run(@NotNull C invoker, @NotNull String[] functionNames, @NotNull T[][] params)
            throws Exception {
        if (functionNames.length != params.length) {
            throw new Exception("参数数目错误");
        }
        Method[] methods = invoker.getClass().getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
        for (int i = 0; i < functionNames.length; i++) {}
    }

    public static void main(String[] args) throws Exception {
        LeetcodeRunner<String> runner = new LeetcodeRunner<>();
        runner.run(runner, new String[0], new String[0][0]);
    }
}
