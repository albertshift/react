package com.reactbase.packedobject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class ClassDefinition<T extends PackedObject> {

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
		
		PackedObject instantiate(long offset) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
			if (length != null) {
				return (PackedObject) constructor.newInstance(offset, length);
			}
			else {
				return (PackedObject) constructor.newInstance(offset);
			}
		}
	}
	
	
	public ClassDefinition(Class<T> clazz) {
		try {
			Field[] declaredFields = clazz.getDeclaredFields();
			List<FieldDefinition> collectList = new ArrayList<FieldDefinition>(declaredFields.length);
			for (Field field : declaredFields) {
				Class<?> fieldClass = field.getType();
				if (PackedObject.class.isAssignableFrom(fieldClass)) {
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
	
	public static <T extends PackedObject> ClassDefinition<T> create(Class<T> clazz) {
		return new ClassDefinition<T>(clazz);
	}
	
	public PackedObject[] constructFields(T instance, long offset) {
		try {
			PackedObject[] result = new PackedObject[fields.length];
			for (int i = 0; i != fields.length; ++i) {
				FieldDefinition fieldDescription = fields[i];
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

