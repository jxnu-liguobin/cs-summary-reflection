/* All Contributors (C) 2021 */
package io.github.dreamylost.compiler;

import java.security.SecureClassLoader;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

/**
 * 输出字节码到JavaClassFile
 *
 * @author 梦境迷离
 * @version 1.0, 2021/4/12
 */
public class ClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    /** 存储编译后的代码数据 */
    private JavaClassObject classJavaFileObject;

    protected ClassFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    /** 编译后加载类，返回一个匿名的SecureClassLoader，加载由JavaCompiler编译后，保存在ClassJavaFileObject中的byte数组 */
    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name) {
                byte[] bytes = classJavaFileObject.getBytes();
                return super.defineClass(name, bytes, 0, bytes.length);
            }
        };
    }

    /** 给编译器提供JavaClassObject，编译器会将编译结果写进去 */
    @Override
    public JavaFileObject getJavaFileForOutput(
            Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        this.classJavaFileObject = new JavaClassObject(className, kind);
        return this.classJavaFileObject;
    }
}
