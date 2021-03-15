/* All Contributors (C) 2021 */
package io.github.poorguy.util;

import com.google.gson.internal.Primitives;
import io.github.poorguy.annotation.NotNull;
import io.github.poorguy.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

public class LeetcodeRunner<Invoker> {

    public Object[] resultFlag = null;
    public Object[][] result = null;

    /** @param invoker 调用该工具方法的对象 */
    public <C> Invoker run(
            @NotNull C invoker, @NotNull String[] functionNames, @NotNull Invoker[][] params)
            throws Exception {
        return run(invoker.getClass(), functionNames, params);
    }

    public <Param> Invoker run(
            @NotNull Class<Invoker> invokerClass,
            @NotNull String[] functionNames,
            @NotNull Param[][] params)
            throws Exception {
        return run(invokerClass, functionNames, params, new Object[params.length]);
    }

    public <B> Invoker run(
            @NotNull Class<Invoker> invokerClass,
            @NotNull String[] functionNames,
            @NotNull B[][] params,
            @Nullable Object[] expected)
            throws Exception {
        if (functionNames.length != params.length) {
            throw new Exception("参数数目错误");
        }
        Method[] methods =
                Arrays.stream(invokerClass.getDeclaredMethods())
                        .filter(x -> !Modifier.isPrivate(x.getModifiers()))
                        .toArray(Method[]::new);
        Constructor<?>[] constructors = invokerClass.getConstructors();
        Invoker invokerObj = null;
        Boolean[] resultFlag = new Boolean[params.length];
        Object[][] result =
                new Object[params.length][2]; // result[?][0] = result, result[?][1] = expected
        for (int i = 0; i < functionNames.length; i++) {
            if (functionNames[i].equals(invokerClass.getSimpleName())) {
                // init object
                B[] param = params[i];
                if (param.length == 0) {
                    invokerObj = invokerClass.newInstance();
                } else {
                    for (Constructor<?> constructor : constructors) {
                        if (constructor.getParameterCount() == param.length) {
                            Class<?>[] parameterTypes = constructor.getParameterTypes();
                            for (B b : param) {
                                if (!b.getClass().equals(parameterTypes[i])) {
                                    break;
                                }
                            }
                            invokerObj = (Invoker) constructor.newInstance(param);
                            result[i][0] = null;
                            result[i][1] = expected[i];
                            resultFlag[i] = result[i][0] == result[i][1];
                        }
                    }
                }
            } else {
                // call instance method
                int index = i;
                Optional<Method> method =
                        Arrays.stream(methods)
                                .filter(x -> x.getName().equals(functionNames[index]))
                                .filter(
                                        x -> {
                                            Class<?>[] parameterTypes = x.getParameterTypes();
                                            for (int j = 0; j < params[index].length; j++) {
                                                Class<?> parameterType = parameterTypes[j];
                                                if (parameterType.isPrimitive()) {
                                                    parameterType = Primitives.wrap(parameterType);
                                                }
                                                if (!parameterType.equals(
                                                        params[index][j].getClass())) {
                                                    return false;
                                                }
                                            }
                                            return true;
                                        })
                                .findFirst();
                if (!method.isPresent()) {
                    throw new Exception("传递给函数的参数类型错误");
                }
                result[i][0] = method.get().invoke(invokerObj, (Object[]) params[i]);
                result[i][1] = expected[i];
                resultFlag[i] =
                        result[i][1] == null
                                ? result[i][0] == result[i][1]
                                : result[i][1].equals(result[i][0]);
            }
        }
        this.result = result;
        this.resultFlag = resultFlag;
        return invokerObj;
    }
}
