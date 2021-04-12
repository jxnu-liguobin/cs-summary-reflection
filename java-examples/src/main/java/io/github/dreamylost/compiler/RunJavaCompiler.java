/* All Contributors (C) 2021 */
package io.github.dreamylost.compiler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * @author 梦境迷离
 * @version 1.0, 2021/4/12
 */
public class RunJavaCompiler {
    public static void main(String[] args)
            throws URISyntaxException, IOException, NoSuchMethodException, IllegalAccessException,
                    InvocationTargetException, InstantiationException, ClassNotFoundException {
        String fullName = "io.github.dreamylost.compiler.HelloWorld";
        String javaFile =
                Objects.requireNonNull(
                                RunJavaCompiler.class
                                        .getClassLoader()
                                        .getResource("HelloWorld.java.tpl"))
                        .getFile();
        DynamicCompiler compiler = new DynamicCompiler();
        Class<?> clz = compiler.compileAndLoad(fullName, Utils.readFile(javaFile));
        System.out.println(clz.getConstructor().newInstance());
        compiler.closeFileManager();
    }
}
