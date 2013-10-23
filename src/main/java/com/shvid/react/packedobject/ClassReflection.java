package com.shvid.react.packedobject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class ClassReflection<T> {

	private final FieldDescription[] fields;
	
	private static final class FieldDescription {
		
		final Field field;
		final Constructor<?> constructor;
		final Integer length;
		
		
		FieldDescription(Field field, Constructor<?> constructor) {
			this(field, constructor, null);
		}
		
		FieldDescription(Field field, Constructor<?> constructor, Integer length) {
			this.field = field;
			this.constructor = constructor;
			this.length = length;
		}
		
		PackedObject instantiate(long offset) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
			if (length != null) {
				return (PackedObject) constructor.newInstance(offset, length);
			}
			else {
				return (PackedObject) constructor.newInstance(offset);
			}
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
					
					Length length = findLengthAnnotation(field);
					if (length != null) {
						Constructor<?> offsetConstructor = fieldClass.getDeclaredConstructor(long.class, int.class);
						offsetConstructor.setAccessible(true);
						
						collectList.add(new FieldDescription(field, offsetConstructor, length.value()));
					}
					else {
						Constructor<?> offsetConstructor = fieldClass.getDeclaredConstructor(long.class);
						offsetConstructor.setAccessible(true);
						
						collectList.add(new FieldDescription(field, offsetConstructor));
					}
				}
			}
			
			FieldDescription[] array = new FieldDescription[collectList.size()];
			fields = collectList.toArray(array);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Length findLengthAnnotation(Field field) {
		for (Annotation annotation : field.getDeclaredAnnotations()) {
			if (annotation instanceof Length) {
				return (Length) annotation;
			}
		}
		return null;
	}
	
	public static <T> ClassReflection<T> create(Class<T> clazz) {
		return new ClassReflection<T>(clazz);
	}
	
	public PackedObject[] constructFields(T instance, long offset) {
		try {
			PackedObject[] result = new PackedObject[fields.length];
			for (int i = 0; i != fields.length; ++i) {
				FieldDescription fieldDescription = fields[i];
				PackedObject fieldInstance = fieldDescription.instantiate(offset);
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

