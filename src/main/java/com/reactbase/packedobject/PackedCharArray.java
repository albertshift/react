package com.reactbase.packedobject;


public final class PackedCharArray extends Array<PackedChar> {

	public PackedCharArray(long offset) {
		super(offset);
	}
	
	public char charAt(byte[] blob, long ptr, int index) {
		long elementPtr = elementAt(blob, ptr, index);
		return TypeRegistry.CHAR.getChar(blob, elementPtr);
	}
	
	public char charAt(long address, long ptr, int index) {
		long elementPtr = elementAt(address, ptr, index);
		return TypeRegistry.CHAR.getChar(address, elementPtr);
	}
	
	public void setCharAt(byte[] blob, long ptr, int index, char value) {
		long elementPtr = elementAt(blob, ptr, index);
		TypeRegistry.CHAR.setChar(blob, elementPtr, value);
	}
	
	public void setCharAt(long address, long ptr, int index, char value) {
		long elementPtr = elementAt(address, ptr, index);
		TypeRegistry.CHAR.setChar(address, elementPtr, value);
	}
	
	public CharSequence getChars(byte[] blob, long ptr) {
		int length = getLength(blob, ptr);
		return new HeapCharSequence(this, blob, ptr, 0, length, length);
	}

	public CharSequence getChars(long address, long ptr) {
		int length = getLength(address, ptr);
		return new AddressCharSequence(this, address, ptr, 0, length, length);
	}
	
	public void setChars(byte[] blob, long ptr, CharSequence chars) {
		int len = chars.length();
		int currentLength = getLength(blob, ptr);
		if (len > currentLength) {
			throw new IndexOutOfBoundsException("chars.length" + chars.length() + ", length=" + currentLength);
		}
		for (int i = 0; i != len; ++i) {
			setChar(blob, ptr, i, chars.charAt(i));
		}
	}
	
	public void setChars(long address, long ptr, CharSequence chars) {
		int len = chars.length();
		int currentLength = getLength(address, ptr);
		if (len > currentLength) {
			throw new IndexOutOfBoundsException("chars.length" + chars.length() + ", length=" + currentLength);
		}
		for (int i = 0; i != len; ++i) {
			setChar(address, ptr, i, chars.charAt(i));
		}
	}
		
	public void setChar(byte[] blob, long ptr, int index, char value) {
		long elementPtr = elementAt(blob, ptr, index);
		TypeRegistry.CHAR.setChar(blob, elementPtr, value);
	}	
	
	public void setChar(long address, long ptr, int index, char value) {
		long elementPtr = elementAt(address, ptr, index);
		TypeRegistry.CHAR.setChar(address, elementPtr, value);
	}
	
	private static class HeapCharSequence implements CharSequence {

		PackedCharArray _this;
		byte[] blob;
		long ptr;
		int start;
		int end;
		int length;
		
		public HeapCharSequence(PackedCharArray _this, byte[] blob, long ptr, int start, int end, int length) {
			ensureSubSequence(start, end, length);
			this._this = _this;
			this.blob = blob;
			this.ptr = ptr;
			this.start = start;
			this.end = end;
			this.length = length;
		}
		
		@Override
		public int length() {
			return end - start;
		}

		@Override
		public char charAt(int index) {
			ensureIndex(start, end, index);
			return _this.charAt(blob, ptr, start + index);
		}

		@Override
		public CharSequence subSequence(int substart, int subend) {
			return new HeapCharSequence(_this, blob, ptr, start + substart, start + subend, length);
		}
		
		@Override
		public String toString() {
			int len = length();
			char[] value = new char[len];
			for (int i = 0; i != len; ++i) {
				value[i] = charAt(i);
			}
			return new String(value);
		}
		
	}
	
	private class AddressCharSequence implements CharSequence {

		PackedCharArray _this;
		long address;
		long ptr;
		int start;
		int end;
		int length;
		
		public AddressCharSequence(PackedCharArray _this, long address, long ptr, int start, int end, int length) {
			ensureSubSequence(start, end, length);
			this._this = _this;
			this.address = address;
			this.ptr = ptr;
			this.start = start;
			this.end = end;
			this.length = length;
		}
		
		@Override
		public int length() {
			return end - start;
		}

		@Override
		public char charAt(int index) {
			ensureIndex(start, end, index);
			return _this.charAt(address, ptr, start + index);
		}

		@Override
		public CharSequence subSequence(int substart, int subend) {
			return new AddressCharSequence(_this, address, ptr, start + substart, start + subend, length);
		}
		
		@Override
		public String toString() {
			int len = length();
			char[] value = new char[len];
			for (int i = 0; i != len; ++i) {
				value[i] = charAt(i);
			}
			return new String(value);
		}
		
	}
	
	private static void ensureSubSequence(int start, int end, int length) {
		if (start < 0 || start >= length) {
			throw new IndexOutOfBoundsException("start=" + start + ", length=" + length);
		}
		if (end < 0 || end > length) {
			throw new IndexOutOfBoundsException("end=" + end + ", length=" + length);
		}			
		if (start > end) {
			throw new IndexOutOfBoundsException("start=" + start + ", end=" + end + ", length=" + length);
		}
	}
	
	private static void ensureIndex(int start, int end, int index) {
		if (start + index >= end) {
			throw new IndexOutOfBoundsException("index=" + index + ", start=" + start + ", end=" + end);
		}
	}
	
}
