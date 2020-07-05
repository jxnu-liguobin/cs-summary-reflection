/* All Contributors (C) 2020 */
package cn.edu.jxnu.examples.reflect;

import static org.objectweb.asm.Opcodes.*;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Remi Forax
 * @author Eric Bruneton
 */
public class IndyCompile extends ClassLoader {

    public static void main(final String[] args) throws Throwable {
        // creates the expression tree corresponding to
        // exp(i) = i > 3 && 6 > i
        Exp exp = new And(new GT(new Var(0), new Cst(3)), new GT(new Cst(6), new Var(0)));

        // compiles this expression into a generic static method in a class
        IndyCompile main = new IndyCompile();
        byte[] b = exp.compile("Example");
        FileOutputStream fos = new FileOutputStream("Example.class");
        fos.write(b);
        fos.close();
        Class<?> expClass = main.defineClass("Example", b, 0, b.length);
        Method expMethod = expClass.getDeclaredMethods()[0];

        // ... and use it to evaluate exp(0) to exp(9)
        for (int i = 0; i < 10; ++i) {
            boolean val = (Boolean) expMethod.invoke(null, i);
            System.out.println(i + " > 3 && " + i + " < 6 = " + val);
        }

        // ... more fun, test with strings !!!
        for (int i = 0; i < 10; ++i) {
            boolean val = (Boolean) expMethod.invoke(null, Integer.toString(i));
            System.out.println("\"" + i + "\" > 3 && 6 > \"" + i + "\" = " + val);
        }
    }

    /** An abstract expression. */
    abstract static class Exp {

        static final Handle CST = getHandle("cst", "Ljava/lang/Object;");

        static final Handle UNARY = getHandle("unary", "");

        static final Handle BINARY = getHandle("binary", "");

        /**
         * Returns the maximum variable index used in this expression.
         *
         * @return the maximum variable index used in this expression, or -1 if no variable is used.
         */
        int getMaxVarIndex() {
            return -1;
        }

        /*
         * Returns the byte code of a class corresponding to this expression.
         */
        byte[] compile(final String name) {
            // class header
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            cw.visit(V1_7, ACC_PUBLIC, name, null, "java/lang/Object", null);

            // eval method type
            StringBuilder desc = new StringBuilder("(");
            for (int i = 0; i <= getMaxVarIndex(); ++i) {
                desc.append("Ljava/lang/Object;");
            }
            desc.append(")Ljava/lang/Object;");

            // eval method
            MethodVisitor mv =
                    cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "eval", desc.toString(), null, null);
            compile(mv);
            mv.visitInsn(ARETURN);
            // max stack and max locals automatically computed
            mv.visitMaxs(0, 0);
            mv.visitEnd();

            return cw.toByteArray();
        }

        /*
         * Compile this expression. This method must append to the given code
         * writer the byte code that evaluates and pushes on the stack the value
         * of this expression.
         */
        abstract void compile(MethodVisitor mv);

        @SuppressWarnings("deprecation")
        private static Handle getHandle(final String name, final String optArgs) {
            return new Handle(
                    H_INVOKESTATIC,
                    "RT",
                    name,
                    "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;"
                            + optArgs
                            + ")Ljava/lang/invoke/CallSite;");
        }
    }

    /** A constant expression. */
    static class Cst extends Exp {

        private final Object value;

        Cst(final Object value) {
            this.value = value;
        }

        @Override
        void compile(final MethodVisitor mv) {
            if (value instanceof String) {
                mv.visitLdcInsn(value);
                return;
            }

            // instead of pushing the constant and then box it, we use
            // invokedynamic with the constant as bootstrap argument. The
            // boxing will be performed by the VM when calling the bootstrap
            // method
            mv.visitInvokeDynamicInsn("cst", "()Ljava/lang/Object;", CST, value);
        }
    }

    /** A variable reference expression. */
    static class Var extends Exp {

        final int index;

        Var(final int index) {
            this.index = index;
        }

        @Override
        int getMaxVarIndex() {
            return index;
        }

        @Override
        void compile(final MethodVisitor mv) {
            // pushes the 'index' local variable onto the stack
            mv.visitVarInsn(ALOAD, index);
        }
    }

    /** An abstract binary expression. */
    abstract static class BinaryExp extends Exp {

        final Exp e1;

        final Exp e2;

        @Override
        int getMaxVarIndex() {
            return Math.max(e1.getMaxVarIndex(), e2.getMaxVarIndex());
        }

        BinaryExp(final Exp e1, final Exp e2) {
            this.e1 = e1;
            this.e2 = e2;
        }
    }

    /** An addition expression. */
    static class Add extends BinaryExp {

        Add(final Exp e1, final Exp e2) {
            super(e1, e2);
        }

        @Override
        void compile(final MethodVisitor mv) {
            // compiles e1, e2, and adds an instruction to add the two values
            e1.compile(mv);
            e2.compile(mv);
            mv.visitInvokeDynamicInsn(
                    "add", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", BINARY);
        }
    }

    /** A multiplication expression. */
    static class Mul extends BinaryExp {

        Mul(final Exp e1, final Exp e2) {
            super(e1, e2);
        }

        @Override
        void compile(final MethodVisitor mv) {
            // compiles e1, e2, and adds an instruction to multiply the two
            // values
            e1.compile(mv);
            e2.compile(mv);
            mv.visitInvokeDynamicInsn(
                    "mul", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", BINARY);
        }
    }

    /** A "greater than" expression. */
    static class GT extends BinaryExp {

        GT(final Exp e1, final Exp e2) {
            super(e1, e2);
        }

        @Override
        void compile(final MethodVisitor mv) {
            // compiles e1, e2, and adds the instructions to compare the two
            // values
            e1.compile(mv);
            e2.compile(mv);
            mv.visitInvokeDynamicInsn(
                    "gt", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", BINARY);
        }
    }

    /** A logical "and" expression. */
    static class And extends BinaryExp {

        And(final Exp e1, final Exp e2) {
            super(e1, e2);
        }

        @Override
        void compile(final MethodVisitor mv) {
            // compiles e1
            e1.compile(mv);
            // tests if e1 is false
            mv.visitInsn(DUP);
            // convert to a boolean
            mv.visitInvokeDynamicInsn("asBoolean", "(Ljava/lang/Object;)Z", UNARY);

            Label end = new Label();
            mv.visitJumpInsn(IFEQ, end);
            // case where e1 is true : e1 && e2 is equal to e2
            mv.visitInsn(POP);
            e2.compile(mv);

            // if e1 is false, e1 && e2 is equal to e1:
            // we jump directly to this label, without evaluating e2
            mv.visitLabel(end);
        }
    }

    /** A logical "or" expression. */
    static class Or extends BinaryExp {

        Or(final Exp e1, final Exp e2) {
            super(e1, e2);
        }

        @Override
        void compile(final MethodVisitor mv) {
            // compiles e1
            e1.compile(mv);
            // tests if e1 is true
            mv.visitInsn(DUP);
            // convert to a boolean
            mv.visitInvokeDynamicInsn("asBoolean", "(Ljava/lang/Object;)Z", UNARY);
            Label end = new Label();
            mv.visitJumpInsn(IFNE, end);
            // case where e1 is false : e1 || e2 is equal to e2
            mv.visitInsn(POP);
            e2.compile(mv);
            // if e1 is true, e1 || e2 is equal to e1:
            // we jump directly to this label, without evaluating e2
            mv.visitLabel(end);
        }
    }

    /** A logical "not" expression. */
    static class Not extends Exp {

        private final Exp e;

        Not(final Exp e) {
            this.e = e;
        }

        @Override
        int getMaxVarIndex() {
            return e.getMaxVarIndex();
        }

        @Override
        void compile(final MethodVisitor mv) {
            // compiles e, and applies 'not'
            e.compile(mv);
            mv.visitInvokeDynamicInsn("not", "(Ljava/lang/Object;)Ljava/lang/Object;", UNARY);
        }
    }
}
