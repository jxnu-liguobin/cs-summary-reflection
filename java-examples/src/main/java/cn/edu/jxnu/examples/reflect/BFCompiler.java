package cn.edu.jxnu.examples.reflect;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * A naive implementation of compiler for Brain**** language.
 * http://www.muppetlabs.com/~breadbox/bf/ *
 *
 * @author Eugene Kuleshov
 */
public class BFCompiler implements Opcodes {

    private static final int V_IS = 0;

    private static final int V_OS = 1;

    private static final int V_P = 2;

    private static final int V_D = 3;

    @SuppressWarnings("deprecation")
    public void compile(
            final Reader r, final String className, final String sourceName, final ClassVisitor cv)
            throws IOException {
        cv.visit(
                Opcodes.V1_3,
                ACC_PUBLIC,
                className.replace('.', '/'),
                null,
                "java/lang/Object",
                null);
        cv.visitSource(sourceName, null);

        MethodVisitor mv;
        {
            mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        {

            // 初始化本地变量用于BF环境
            mv =
                    cv.visitMethod(
                            ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();

            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
            mv.visitVarInsn(ASTORE, V_IS);

            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(ASTORE, V_OS);

            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, V_P);

            mv.visitIntInsn(SIPUSH, 30000);
            mv.visitIntInsn(NEWARRAY, T_INT);
            mv.visitVarInsn(ASTORE, V_D);

            Stack<Label> labels = new Stack<Label>();

            int d = 0;
            int p = 0;

            int c;
            while ((c = r.read()) != -1) {
                switch (c) {
                    case '>':
                        d = storeD(mv, d);
                        p++;
                        break;

                    case '<':
                        d = storeD(mv, d);
                        p--;
                        break;

                    case '+':
                        p = storeP(mv, p);
                        d++;
                        break;

                    case '-':
                        p = storeP(mv, p);
                        d--;
                        break;

                    case '.':
                        p = storeP(mv, p);
                        d = storeD(mv, d);

                        mv.visitVarInsn(ALOAD, V_OS);
                        mv.visitVarInsn(ALOAD, V_D);
                        mv.visitVarInsn(ILOAD, V_P);
                        mv.visitInsn(IALOAD);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/OutputStream", "write", "(I)V");
                        break;

                    case ',':
                        p = storeP(mv, p);
                        d = storeD(mv, d);

                        mv.visitVarInsn(ALOAD, V_D);
                        mv.visitVarInsn(ILOAD, V_P);
                        mv.visitVarInsn(ALOAD, V_IS);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStream", "read", "()I");
                        mv.visitInsn(IASTORE);
                        break;

                    case '[':
                        p = storeP(mv, p);
                        d = storeD(mv, d);

                        Label ls = new Label();
                        Label le = new Label();
                        labels.push(ls);
                        labels.push(le);
                        mv.visitJumpInsn(GOTO, le);
                        mv.visitLabel(ls);
                        break;

                    case ']':
                        p = storeP(mv, p);
                        d = storeD(mv, d);

                        mv.visitLabel(labels.pop());
                        mv.visitVarInsn(ALOAD, V_D);
                        mv.visitVarInsn(ILOAD, V_P);
                        mv.visitInsn(IALOAD);
                        mv.visitJumpInsn(IFNE, labels.pop());
                        break;
                }
            }

            mv.visitInsn(RETURN);

            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
    }

    private int storeD(final MethodVisitor mv, final int d) {
        if (d != 0) {
            mv.visitVarInsn(ALOAD, V_D);
            mv.visitVarInsn(ILOAD, V_P);
            mv.visitInsn(DUP2);
            mv.visitInsn(IALOAD);
            mv.visitIntInsn(SIPUSH, d);
            mv.visitInsn(IADD);
            mv.visitInsn(IASTORE);
        }
        return 0;
    }

    private int storeP(final MethodVisitor mv, final int p) {
        if (p != 0) {
            mv.visitIincInsn(V_P, p);
        }
        return 0;
    }
}
