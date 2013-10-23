package com.shvid.react.packedobject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class ClassDefinition<T extends PackedClass> {

	private final FieldDefinition[] fields;
	
	private static final class FieldDefinition {
		
		final Field field;
		final Constructor<?> constructor;
		final Integer length;
		
		
		FieldDefinition(Field field, Constructor<?> constructor) {
			this(field, constructor, null);
		}
		
		FieldDefinition(Field field, Constructor<?> constructor, Integer length) {
			this.field = field;
			this.constructor = constructor;
			this.length = length;
		}
		
		PackedClass instantiate(long offset) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
			if (length != null) {
				return (PackedClass) constructor.newInstance(offset, length);
			}
			else {
				return (PackedClass) constructor.newInstance(offset);
			}
		}
	}
	
	
	public ClassDefinition(Class<T> clazz) {
		try {
			Field[] declaredFields = clazz.getDeclaredFields();
			List<FieldDefinition> collectList = new ArrayList<FieldDefinition>(declaredFields.length);
			for (Field field : declaredFields) {
				Class<?> fieldClass = field.getType();
				if (PackedClass.class.isAssignableFrom(fieldClass)) {
					field.setAccessible(true);
					
					Length length = findLengthAnnotation(field);
					if (length != null) {
						Constructor<?> offsetConstructor = fieldClass.getDeclaredConstructor(long.class, int.class);
						offsetConstructor.setAccessible(true);
						
						collectList.add(new FieldDefinition(field, offsetConstructor, length.value()));
					}
					else {
						Constructor<?> offsetConstructor = fieldClass.getDeclaredConstructor(long.class);
						offsetConstructor.setAccessible(true);
						
						collectList.add(new FieldDefinition(field, offsetConstructor));
					}
				}
			}
			
			FieldDefinition[] array = new FieldDefinition[collectList.size()];
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
	
	public static <T extends PackedClass> ClassDefinition<T> create(Class<T> clazz) {
		return new ClassDefinition<T>(clazz);
	}
	
	public PackedClass[] constructFields(T instance, long offset) {
		try {
			PackedClass[] result = new PackedClass[fields.length];
			for (int i = 0; i != fields.length; ++i) {
				FieldDefinition fieldDescription = fields[i];
				PackedClass fieldInstance = fieldDescription.instantiate(offset);
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

