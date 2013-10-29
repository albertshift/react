package com.reactbase.packedobject;

import java.util.concurrent.atomic.AtomicReference;

public class TypeRegistry {

	public static final int BYTE_ID = 1;
	public static final int CHAR_ID = 2;
	public static final int SHORT_ID = 3;
	public static final int INT_ID = 4;
	public static final int LONG_ID = 5;
	public static final int FLOAT_ID = 6;
	public static final int DOUBLE_ID = 7;

	public static final int REF_ID = 8;
	public static final int ARRAY_ID = 9;
	public static final int STRING_ID = 10;
	public static final int LIST_ID = 11;
	public static final int SET_ID = 12;
	public static final int MAP_ID = 13;
	
	public static final PackedByte BYTE = new PackedByte(0);
	public static final PackedChar CHAR = new PackedChar(0);
	public static final PackedShort SHORT = new PackedShort(0);
	public static final PackedInt INT = new PackedInt(0);
	public static final PackedLong LONG = new PackedLong(0);
	public static final PackedFloat FLOAT = new PackedFloat(0);
	public static final PackedDouble DOUBLE = new PackedDouble(0);

	public static final Ref<PackedObject> REF = new Ref<PackedObject>(0);
	public static final Array<PackedObject> ARRAY = new Array<PackedObject>(0);
	public static final PackedString STRING = new PackedString(0);

	
	private static AtomicReference<PackedObject[]> registry = new AtomicReference<PackedObject[]>();

	static {
		
		PackedObject[] array = new PackedObject[1000];
		
		array[BYTE_ID] = BYTE;
		array[CHAR_ID] = CHAR;
		array[SHORT_ID] = SHORT;
		array[INT_ID] = INT;
		array[LONG_ID] = LONG;
		array[FLOAT_ID] = FLOAT;
		array[DOUBLE_ID] = DOUBLE;
		
		array[REF_ID] = REF;
		array[ARRAY_ID] = ARRAY;

		registry.set(array);
		
	}

	public static void require(int maxId) {
		while(true) {
			PackedObject[] array = registry.get();
			if (array.length > maxId) {
				return;
			}
			PackedObject[] newArray = new PackedObject[maxId];
			System.arraycopy(array, 0, newArray, 0, array.length);
			if (registry.compareAndSet(array, newArray)) {
				return;
			}
		}
	}
	
	public static void register(int typeId, PackedObject pc) {
		require(typeId);
		while(true) {
			PackedObject[] array = registry.get();
			array[typeId] = pc;
			if (array == registry.get()) {
				return;
			}
		}
	}
	
	public static <T extends PackedObject> T resolveType(int typeId) {
		PackedObject[] array = registry.get();
		if (typeId < 0 || typeId >= array.length) {
			throw new IndexOutOfBoundsException();
		}
		PackedObject pc = array[typeId];
		if (pc == null) {
			throw new IllegalStateException();
		}
		return (T) pc;
	}
	
	
}
