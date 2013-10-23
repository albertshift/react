package com.shvid.react.packedobject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class ClassReflection<T> {

	private final FieldDescription[] fields;
	
	private static final class FieldDescription {
		
		Field field;
		Constructor<?> constructor;
		
		
		FieldDescription(Field field, Constructor<?> constructor) {
			this.field = field;
			this.constructor = constructor;
		}
	}
	
	
	public ClassReflection(Class<T> clazz) {
		try {
			Field[] declaredFields = clazz.getDeclaredFields();
			List<FieldDescription> collectList = new ArrayList<FieldDescription>(declaredFields.length);
			for (Field field : declaredFields) {
				Class<?> fieldClass = field.getType();
				if (PackedObject.class.isAssignableFrom(fieldClass)) {
					field.setAccessible(true);
					Constructor<?> offsetConstructor = fieldClass.getDeclaredConstructor(long.class);
					offsetConstructor.setAccessible(true);
					
					collectList.add(new FieldDescription(field, offsetConstructor));
				}
			}
			
			FieldDescription[] array = new FieldDescription[collectList.size()];
			fields = collectList.toArray(array);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> ClassReflection<T> create(Class<T> clazz) {
		return new ClassReflection<T>(clazz);
	}
	
	public PackedObject[] constructFields(T instance, long offset) {
		try {
			PackedObject[] result = new PackedObject[fields.length];
			for (int i = 0; i != fields.length; ++i) {
				FieldDescription fieldDescription = fields[i];
				PackedObject fieldInstance = (PackedObject) fieldDescription.constructor.newInstance(offset);
				result[i] = fieldInstance;
				fieldDescription.field.set(instance, fieldInstance);
			}
			return result;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}

