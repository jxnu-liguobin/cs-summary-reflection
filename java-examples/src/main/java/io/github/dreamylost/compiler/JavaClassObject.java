/* All Contributors (C) 2021 */
package io.github.dreamylost.compiler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import javax.tools.SimpleJavaFileObject;

/**
 * 描述Java字节码
 *
 * @author 梦境迷离
 * @version 1.0, 2021/4/12
 */
public class JavaClassObject extends SimpleJavaFileObject {
    private ByteArrayOutputStream byteArrayOutputStream;

    public JavaClassObject(String className, Kind kind) {
        super(URI.create("string:///" + className.replaceAll("\\.", "/") + kind.extension), kind);
        this.byteArrayOutputStream = new ByteArrayOutputStream();
    }

    /**
     * 覆盖父类SimpleJavaFileObject的方法，该方法提供给编译器结果输出的OutputStream。 编译器完成编译后，会将编译结果输出到该 OutputStream
     * 中，我们随后需要使用它获取编译结果
     */
    @Override
    public OutputStream openOutputStream() {
        return this.byteArrayOutputStream;
    }

    /** FileManager会使用该方法获取编译后的byte，然后将类加载到JVM */
    public byte[] getBytes() {
        return this.byteArrayOutputStream.toByteArray();
    }
}
