/* All Contributors (C) 2021 */
package io.github.dreamylost.compiler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.tools.*;

/**
 * 运行时编译
 *
 * @author 梦境迷离
 * @version 1.0, 2021/4/12
 */
public class DynamicCompiler {

    private DiagnosticCollector<JavaFileObject> diagnosticCollector;

    private JavaFileManager fileManager;

    public DynamicCompiler() {
        this.fileManager = initManger();
    }

    private JavaFileManager initManger() {
        if (fileManager == null) {
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            diagnosticCollector = new DiagnosticCollector<>();
            fileManager =
                    new ClassFileManager(
                            javaCompiler.getStandardFileManager(
                                    diagnosticCollector, Locale.ENGLISH, StandardCharsets.UTF_8));
        }
        return fileManager;
    }

    /** 编译源码并加载，获取Class对象 */
    public Class<?> compileAndLoad(String fullName, String sourceCode)
            throws ClassNotFoundException, URISyntaxException {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        List<JavaFileObject> javaFileObjectList = new ArrayList<JavaFileObject>();
        javaFileObjectList.add(new JavaCodeObject(fullName, sourceCode));
        boolean result =
                javaCompiler
                        .getTask(null, fileManager, null, null, null, javaFileObjectList)
                        .call();
        List<Diagnostic<? extends JavaFileObject>> diagnostics =
                diagnosticCollector.getDiagnostics();
        Utils.printlnInfo(diagnostics);
        if (result) {
            return this.fileManager.getClassLoader(null).loadClass(fullName);
        } else {
            return Class.forName(fullName);
        }
    }

    /** 关闭fileManager */
    public void closeFileManager() throws IOException {
        this.fileManager.close();
    }
}
