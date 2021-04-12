/* All Contributors (C) 2021 */
package io.github.dreamylost.compiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @author 梦境迷离
 * @version 1.0, 2021/4/12
 */
public final class Utils {
    private Utils() {}

    public static String readFile(String file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            bufferedReader.lines().map(stringBuilder::append).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /** DiagnosticListener用于获取Diagnostic信息，Diagnostic信息包括: 错误，警告和说明性信息 */
    public static void printlnInfo(List<Diagnostic<? extends JavaFileObject>> list) {
        for (Object object : list) {
            Diagnostic<?> diagnostic = (Diagnostic<?>) object;
            System.out.println("line:" + diagnostic.getLineNumber());
            System.out.println("msg:" + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("source:" + diagnostic.getSource());
        }
    }
}
