/* All Contributors (C) 2021 */
package io.github.dreamylost.compiler;

import java.net.URI;
import javax.tools.SimpleJavaFileObject;

/**
 * 描述Java源代码
 *
 * @author 梦境迷离
 * @version 1.0, 2021/4/12
 */
public class JavaCodeObject extends SimpleJavaFileObject {
    private CharSequence content;

    public JavaCodeObject(String className, String content) {
        super(
                URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.content = content;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return content;
    }
}
