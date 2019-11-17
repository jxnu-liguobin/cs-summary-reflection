package cn.edu.jxnu.examples.reflect;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A naive implementation of compiler for Brain**** language.
 * http://www.muppetlabs.com/~breadbox/bf/ *
 * 
 * @author Eugene Kuleshov
 */
public class Jbfc {

    public static void main(final String[] args) throws IOException {
        if (args.length < 2) {
            System.out
                    .println("Usage: jbfc [-v] <bf program file> <java class name>");
            return;
        }

        boolean verbose = false;
        String fileName = null;
        String className = null;
        for (int i = 0; i < args.length; i++) {
            if ("-v".equals(args[i])) {
                verbose = true;
            } else {
                fileName = args[i];
                className = args[i + 1];
                break;
            }
        }

        FileReader r = new FileReader(fileName);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        BFCompiler c = new BFCompiler();
        if (verbose) {
            c.compile(r, className, fileName, new TraceClassVisitor(cw,
                    new PrintWriter(System.out)));
        } else {
            c.compile(r, className, fileName, cw);
        }

        r.close();

        FileOutputStream os = new FileOutputStream(className + ".class");
        os.write(cw.toByteArray());
        os.flush();
        os.close();
    }

}
