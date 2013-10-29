package com.shvid.react;

/**
 * Unsafe mechanics
 * 
 * @author ashvid
 *
 */

public final class UnsafeHolder {

	static final long UNSAFE_COPY_THRESHOLD = 1024L * 1024L;
	
    public static final sun.misc.Unsafe UNSAFE;
	public static final long byteArrayBaseOffset;
	public static final long byteArrayIndexScale;
	
    static {
        try {
            UNSAFE = getUnsafe();
            byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);
            byteArrayIndexScale = UNSAFE.arrayIndexScale(byte[].class);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

	public static void copyMemory(long srcAddr, long dstAddr, long length) {
		while (length > 0) {
			long size = (length > UNSAFE_COPY_THRESHOLD) ? UNSAFE_COPY_THRESHOLD : length;
			UNSAFE.copyMemory(srcAddr, dstAddr, size);
			length -= size;
			srcAddr += size;
			dstAddr += size;
		}
	}

	public static void copyMemory(Object src, long srcAddr, Object dst,
			long dstAddr, long length) {
		while (length > 0) {
			long size = (length > UNSAFE_COPY_THRESHOLD) ? UNSAFE_COPY_THRESHOLD : length;
			UNSAFE.copyMemory(src, srcAddr, dst, dstAddr, size);
			length -= size;
			srcAddr += size;
			dstAddr += size;
		}
	}
    
    private final static sun.misc.Unsafe getUnsafe() {
        try {
            return sun.misc.Unsafe.getUnsafe();
        } catch (SecurityException tryReflectionInstead) {}
        try {
            return java.security.AccessController.doPrivileged
            (new java.security.PrivilegedExceptionAction<sun.misc.Unsafe>() {
                public sun.misc.Unsafe run() throws Exception {
                    Class<sun.misc.Unsafe> k = sun.misc.Unsafe.class;
                    for (java.lang.reflect.Field f : k.getDeclaredFields()) {
                        f.setAccessible(true);
                        Object x = f.get(null);
                        if (k.isInstance(x))
                            return k.cast(x);
                    }
                    throw new NoSuchFieldError("the Unsafe");
                }});
        } catch (java.security.PrivilegedActionException e) {
            throw new RuntimeException("Could not initialize intrinsics",
                                       e.getCause());
        }
    }
    
}
