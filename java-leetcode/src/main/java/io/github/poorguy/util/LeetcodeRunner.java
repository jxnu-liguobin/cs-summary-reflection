/* All Contributors (C) 2021 */
package io.github.poorguy.util;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import io.github.poorguy.annotation.NotNull;
import io.github.poorguy.annotation.Nullable;
import io.github.poorguy.explore.learn.hashtable.DesignHashMap;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LeetcodeRunner<
                Invoker, F extends List<?>, P extends List<? extends List<?>>, E extends List<?>>
        implements AbstractLeetcodeRunner<Invoker, F, P, E> {

    private static final Gson gson = new Gson();
    public Object[] resultFlag = null;
    public Object[][] result = null;

    public Invoker run(
            Class<Invoker> invokerClass,
            @NotNull String functionNamesJSON,
            @NotNull String paramsJSON,
            @NotNull String expectedJSON)
            throws Exception {
        List<String> functionNames =
                gson.fromJson(functionNamesJSON, new TypeToken<List<String>>() {}.getType());
        List<List<Integer>> params =
                gson.fromJson(paramsJSON, new TypeToken<List<List<Integer>>>() {}.getType());
        List<Integer> expected =
                gson.fromJson(expectedJSON, new TypeToken<List<Integer>>() {}.getType());
        LeetcodeRunner defaultRunner =
                new LeetcodeRunner<
                        DesignHashMap, List<String>, List<List<Integer>>, List<Integer>>();
        Invoker res = (Invoker) defaultRunner.run(invokerClass, functionNames, params, expected);
        return res;
    }

    public Invoker run(
            @NotNull Class<Invoker> invokerClass, @NotNull F functionNames, @NotNull P params)
            throws Exception {
        return run(invokerClass, functionNames, params, (E) new ArrayList<>(params.size()));
    }

    public Invoker run(
            @NotNull Class<Invoker> invokerClass,
            @NotNull F functionNames,
            @NotNull P params,
            @Nullable E expected)
            throws Exception {
        if (functionNames.size() != params.size()) {
            throw new Exception("参数数目错误");
        }
        Method[] methods =
                Arrays.stream(invokerClass.getDeclaredMethods())
                        .filter(x -> !Modifier.isPrivate(x.getModifiers()))
                        .toArray(Method[]::new);
        Constructor<?>[] constructors = invokerClass.getConstructors();
        Invoker invokerObj = null;
        Boolean[] resultFlag = new Boolean[params.size()];
        Object[][] result =
                new Object[params.size()][2]; // result[?][0] = result, result[?][1] = expected
        for (int i = 0; i < functionNames.size(); i++) {
            if (functionNames.get(i).equals(invokerClass.getSimpleName())) {
                // init object
                List<?> param = params.get(i);
                if (param.size() == 0) {
                    invokerObj = invokerClass.newInstance();
                } else {
                    for (Constructor<?> constructor : constructors) {
                        if (constructor.getParameterCount() == param.size()) {
                            Class<?>[] parameterTypes = constructor.getParameterTypes();
                            for (Object b : param) {
                                if (!b.getClass().equals(parameterTypes[i])) {
                                    break;
                                }
                            }
                            invokerObj = (Invoker) constructor.newInstance(param);
                            result[i][0] = null;
                            result[i][1] = expected.get(i);
                            resultFlag[i] = result[i][0] == result[i][1];
                        }
                    }
                }
            } else {
                // call instance method
                int index = i;
                Optional<Method> method =
                        Arrays.stream(methods)
                                .filter(
                                        x -> {
                                            if (!x.getName().equals(functionNames.get(index))) {
                                                return false;
                                            }
                                            Class<?>[] parameterTypes = x.getParameterTypes();
                                            for (int j = 0; j < params.get(index).size(); j++) {
                                                Class<?> parameterType = parameterTypes[j];
                                                if (parameterType.isPrimitive()) {
                                                    parameterType = Primitives.wrap(parameterType);
                                                }
                                                String typeName =
                                                        params.get(index)
                                                                .get(j)
                                                                .getClass()
                                                                .getTypeName();
                                                if (!parameterType.getTypeName().equals(typeName)) {
                                                    return false;
                                                }
                                            }
                                            return true;
                                        })
                                .findFirst();
                if (!method.isPresent()) {
                    throw new Exception("传递给函数的参数类型错误");
                }
                result[i][0] =
                        method.get()
                                .invoke(
                                        invokerObj,
                                        params.get(i).toArray(new Object[params.get(i).size()]));
                result[i][1] = expected.get(i);
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
