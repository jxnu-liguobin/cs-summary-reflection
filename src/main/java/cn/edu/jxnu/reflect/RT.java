package cn.edu.jxnu.reflect;

import java.lang.invoke.*;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;

/**
 * There are 3 bootstrap methods: - one for the constant that are initialized
 * once the first time the bootstrap method is called and after always reuse the
 * same constant. It's almost equivalent to an LDC but here the constant are
 * stored in boxed form e.g a java.lang.Integer containing 0 instead of an int
 * containing 0.
 * 
 * - one for the unary operation 'not' and 'asBoolean', here the semantics is
 * hard coded, all primitive value are transformed as object by applying this
 * operation: (v == 0)? false: true
 * 
 * - one for the binary operation 'add', 'mul' and 'gt', here the semantics can
 * be changed by adding more static methods in {@link BinaryOps}. This bootstrap
 * method is a little more complex because it creates an inlining cache to avoid
 * to recompute the binary method to call if the type of the two arguments
 * doesn't change. Also, if the expression is used a lot and trigger the JIT, it
 * will be able to inline the code of the operation directly at callsite.
 * 
 * @author Remi Forax
 */
public class RT {
	/**
	 * 常量引导方法
	 */
	public static CallSite cst(Lookup lookup, String name, MethodType type, Object constant) {
		return new ConstantCallSite(MethodHandles.constant(Object.class, constant));
	}

	/**
	 * 一元操作，asBoolean和not,引导方法，
	 */
	public static CallSite unary(Lookup lookup, String name, MethodType type) {
		MethodHandle target;
		if (name.equals("asBoolean")) {
			target = MethodHandles.explicitCastArguments(MethodHandles.identity(Object.class),
					MethodType.methodType(boolean.class, Object.class));
		} else { // "not"
			target = MethodHandles.explicitCastArguments(NOT, MethodType.methodType(Object.class, Object.class));
		}
		return new ConstantCallSite(target);
	}

	/**
	 * 二进制操作“Add”、“mul”和“GT”的引导方法
	 * 
	 * This bootstrap method doesn't install the target method handle directly,
	 * because we want to install an inlining cache and we can't create an inlining
	 * cache without knowing the class of the arguments. So this method first
	 * installs a method handle that will call
	 * {@link BinaryOpCallSite#fallback(Object, Object)} and the fallback method
	 * will be called with the arguments and thus can install the inlining cache.
	 * Also, the fallback has to be bound to a specific callsite to be able to
	 * change its target after the first call, this part is done in the constructor
	 * of {@link BinaryOpCallSite}.
	 */
	public static CallSite binary(Lookup lookup, String name, MethodType type) {
		BinaryOpCallSite callSite = new BinaryOpCallSite(name, type);
		callSite.setTarget(callSite.fallback);
		return callSite;
	}

	/**
	 * Garbage class containing the method used to apply 'not' on a boolean. See
	 * {@link RT#unary(Lookup, String, MethodType)}
	 */
	public static class UnayOps {

		public static Object not(boolean b) {
			return !b;
		}
	}

	private static final MethodHandle NOT;

	static {
		try {
			NOT = MethodHandles.publicLookup().findStatic(UnayOps.class, "not",
					MethodType.methodType(Object.class, boolean.class));
		} catch (ReflectiveOperationException e) {
			throw new LinkageError(e.getMessage(), e);
		}
	}

	/**
	 * A specific callsite that will install an 'inlining cache'. Because we don't
	 * know until runtime which method handle to call, the lookup depending on the
	 * dynamic type of the argument will be done at runtime when the method
	 * {@link #fallback(Object, Object)} is called. To avoid to do this dynamic
	 * lookup at each call, the fallback install two guards in front of dispatch
	 * call that will check if the arguments class change or not. If the arguments
	 * class don't change, the previously computed method handle will be called
	 * again. Otherwise, a new method handle will be computed and two new guards
	 * will be installed.
	 */
	static class BinaryOpCallSite extends MutableCallSite {

		private final String opName;

		final MethodHandle fallback;

		public BinaryOpCallSite(String opName, MethodType type) {
			super(type);
			this.opName = opName;
			this.fallback = FALLBACK.bindTo(this);
		}

		Object fallback(Object v1, Object v2) throws Throwable {
			// when you debug with this message
			// don't forget that && and || are lazy !!
			// System.out.println("fallback called with
			// "+opName+'('+v1.getClass()+','+v2.getClass()+')');

			Class<? extends Object> class1 = v1.getClass();
			Class<? extends Object> class2 = v2.getClass();
			MethodHandle op = lookupBinaryOp(opName, class1, class2);

			// convert arguments
			MethodType type = type();
			MethodType opType = op.type();
			if (opType.parameterType(0) == String.class) {
				if (opType.parameterType(1) == String.class) {
					op = MethodHandles.filterArguments(op, 0, TO_STRING, TO_STRING);
				} else {
					op = MethodHandles.filterArguments(op, 0, TO_STRING);
					op = MethodHandles.explicitCastArguments(op, type);
				}
			} else {
				if (opType.parameterType(1) == String.class) {
					op = MethodHandles.filterArguments(op, 1, TO_STRING);
				}
				op = MethodHandles.explicitCastArguments(op, type);
			}

			// prepare guard
			MethodHandle guard = MethodHandles.guardWithTest(TEST1.bindTo(class1),
					MethodHandles.guardWithTest(TEST2.bindTo(class2), op, fallback), fallback);

			// install the inlining cache
			setTarget(guard);
			return op.invokeWithArguments(v1, v2);
		}

		public static boolean test1(Class<?> v1Class, Object v1, Object v2) {
			return v1.getClass() == v1Class;
		}

		public static boolean test2(Class<?> v2Class, Object v1, Object v2) {
			return v2.getClass() == v2Class;
		}

		private static final MethodHandle TO_STRING;

		private static final MethodHandle TEST1;

		private static final MethodHandle TEST2;

		private static final MethodHandle FALLBACK;

		static {
			Lookup lookup = MethodHandles.lookup();
			try {
				TO_STRING = lookup.findVirtual(Object.class, "toString", MethodType.methodType(String.class));
				MethodType testType = MethodType.methodType(boolean.class, Class.class, Object.class, Object.class);
				TEST1 = lookup.findStatic(BinaryOpCallSite.class, "test1", testType);
				TEST2 = lookup.findStatic(BinaryOpCallSite.class, "test2", testType);
				FALLBACK = lookup.findVirtual(BinaryOpCallSite.class, "fallback", MethodType.genericMethodType(2));
			} catch (ReflectiveOperationException e) {
				throw new LinkageError(e.getMessage(), e);
			}
		}
	}

	/**
	 * Garbage class that contains the raw operations used for binary operations.
	 * All methods must be static returns an Object and takes the same type for the
	 * two parameter types.
	 * 
	 * See {@link RT#lookupBinaryOp(String, Class, Class)} for more info.
	 */
	public static class BinaryOps {

		public static Object add(int v1, int v2) {
			return v1 + v2;
		}

		public static Object add(double v1, double v2) {
			return v1 + v2;
		}

		public static Object add(String v1, String v2) {
			return v1 + v2;
		}

		public static Object mul(int v1, int v2) {
			return v1 * v2;
		}

		public static Object mul(double v1, double v2) {
			return v1 * v2;
		}

		public static Object gt(int v1, int v2) {
			return v1 > v2;
		}

		public static Object gt(double v1, double v2) {
			return v1 > v2;
		}

		public static Object gt(String v1, String v2) {
			return v1.compareTo(v2) > 0;
		}
	}

	/**
	 * Select a most specific method among the ones defined in {@link RT.BinaryOps}.
	 * The algorithm first find the most specific subtype between class1 and class2.
	 * The order of the types is defined in {@link RT#RANK_MAP}: Boolean < Byte <
	 * Short < Character < Integer < Long < Float < Double < String then the
	 * algorithm lookup in {@link RT.BinaryOps} to find a method with the name
	 * opName taking as argument the primitive corresponding to the most specific
	 * subtype. If no such method exist, the algorithm retry but looking for a
	 * method with a more specific type (using the same order). The result of the
	 * lookup is cached in {@link RT#BINARY_CACHE} to avoid to avoid to do a lookup
	 * (a reflective call) on the same method twice.
	 */
	static MethodHandle lookupBinaryOp(String opName, Class<?> class1, Class<?> class2) {
		int rank = Math.max(RANK_MAP.get(class1), RANK_MAP.get(class2));
		String mangledName = opName + rank;
		MethodHandle mh = BINARY_CACHE.get(mangledName);
		if (mh != null) {
			return mh;
		}

		for (; rank < PRIMITIVE_ARRAY.length;) {
			Class<?> primitive = PRIMITIVE_ARRAY[rank];
			try {
				mh = MethodHandles.publicLookup().findStatic(BinaryOps.class, opName,
						MethodType.methodType(Object.class, primitive, primitive));
			} catch (NoSuchMethodException e) {
				rank = rank + 1;
				continue;
			} catch (IllegalAccessException e) {
				throw new LinkageError(e.getMessage(), e);
			}

			BINARY_CACHE.put(mangledName, mh);
			return mh;
		}
		throw new LinkageError("unknown operation " + opName + " (" + class1.getName() + ',' + class2.getName() + ')');
	}

	private static final HashMap<Class<?>, Integer> RANK_MAP;

	private static final Class<?>[] PRIMITIVE_ARRAY;

	private static final HashMap<String, MethodHandle> BINARY_CACHE;

	static {
		Class<?>[] primitives = new Class<?>[] { boolean.class, byte.class, short.class, char.class, int.class,
				long.class, float.class, double.class, String.class };
		Class<?>[] wrappers = new Class<?>[] { Boolean.class, Byte.class, Short.class, Character.class, Integer.class,
				Long.class, Float.class, Double.class, String.class };
		HashMap<Class<?>, Integer> rankMap = new HashMap<Class<?>, Integer>();
		for (int i = 0; i < wrappers.length; i++) {
			rankMap.put(wrappers[i], i);
		}

		RANK_MAP = rankMap;
		PRIMITIVE_ARRAY = primitives;
		BINARY_CACHE = new HashMap<String, MethodHandle>();
	}
}
