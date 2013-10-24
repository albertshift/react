package com.shvid.react.packedobject;

import com.shvid.react.UnsafeHolder;

public class PackedCharArray extends FixedPackedClass {

	final int length;
	final char defaultValue;
	
	public PackedCharArray(long offset, int length) {
		this(offset, length, PackedConstants.DEFAULT_CHAR);
	}
	
	public PackedCharArray(long offset, int length, char defaultValue) {
		super(offset);
		this.length = length;
		this.defaultValue = defaultValue;
	}
	
	public void format(byte[] blob, long ptr) {
		for (int i = 0; i != length; ++i) {
			setChar(blob, ptr, i, defaultValue);
		}
	}
	
	public void format(long address, long ptr) {
		for (int i = 0; i != length; ++i) {
			setChar(address, ptr, i, defaultValue);
		}
	}
	
	public CharSequence getChars(HeapPackedObject<?> po) {
		return getChars(po.blob, po.ptr);
	}

	public CharSequence getChars(byte[] blob, long ptr) {
		return new HeapCharSequence(blob, ptr, 0, length);
	}
	
	public char getChar(HeapPackedObject<?> po, int index) {
		return getChar(po.blob, po.ptr, index);
	}
	
	public char getChar(byte[] blob, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.CHAR_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getChar(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale);
	}
	
	public CharSequence getChars(AddressPackedObject<?> po) {
		return getChars(po.address, po.ptr);
	}

	public CharSequence getChars(long address, long ptr) {
		return new AddressCharSequence(address, ptr, 0, length);
	}
	
	public char getChar(AddressPackedObject<?> po, int pos) {
		return getChar(po.address, po.ptr, pos);
	}
	
	public char getChar(long address, long ptr, int pos) {
		ensurePosition(pos);
		pos <<= PackedConstants.CHAR_SHIFT_BITS;
		return UnsafeHolder.UNSAFE.getChar(address + offset + ptr + pos);
	}
	
	public void setChars(HeapPackedObject<?> po, CharSequence chars) {
		setChars(po.blob, po.ptr, chars);
	}
	
	public void setChars(byte[] blob, long ptr, CharSequence chars) {
		int len = chars.length();
		if (len > length) {
			throw new IndexOutOfBoundsException("chars.length" + chars.length() + ", length=" + length);
		}
		for (int i = 0; i != len; ++i) {
			setChar(blob, ptr, i, chars.charAt(i));
		}
	}
	
	public void setChars(AddressPackedObject<?> po, CharSequence chars) {
		setChars(po.address, po.ptr, chars);
	}
	
	public void setChars(long address, long ptr, CharSequence chars) {
		int len = chars.length();
		if (len > length) {
			throw new IndexOutOfBoundsException("chars.length" + chars.length() + ", length=" + length);
		}
		for (int i = 0; i != len; ++i) {
			setChar(address, ptr, i, chars.charAt(i));
		}
	}
		
	public void setChar(HeapPackedObject<?> po, int pos, char value) {
		setChar(po.blob, po.ptr, pos, value);
	}
	
	public void setChar(byte[] blob, long ptr, int pos, char value) {
		ensurePosition(pos);
		pos <<= PackedConstants.CHAR_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putChar(blob, offset + ptr + UnsafeHolder.byteArrayBaseOffset + pos * UnsafeHolder.byteArrayIndexScale, value);
	}	
	
	public void setChar(AddressPackedObject<?> po, int pos, char value) {
		setChar(po.address, po.ptr, pos, value);
	}
	
	public void setChar(long address, long ptr, int pos, char value) {
		ensurePosition(pos);
		pos <<= PackedConstants.CHAR_SHIFT_BITS;
		UnsafeHolder.UNSAFE.putChar(address + offset + ptr + pos, value);
	}
	
	public int getFixedSize() {
		return length << PackedConstants.CHAR_SHIFT_BITS;
	}

	public int getInitCapacity() {
		return 0;
	}
	
	private void ensurePosition(int pos) {
		if (pos < 0 || pos >= length) {
			throw new IndexOutOfBoundsException("pos=" + pos + ", length=" + length);
		}
	}
	
	private class HeapCharSequence implements CharSequence {

		byte[] blob;
		long ptr;
		int start;
		int end;
		
		public HeapCharSequence(byte[] blob, long ptr, int start, int end) {
			ensureSubSequence(start, end);
			this.blob = blob;
			this.ptr = ptr;
			this.start = start;
			this.end = end;
		}
		
		@Override
		public int length() {
			return end - start;
		}

		@Override
		public char charAt(int index) {
			ensureIndex(start, end, index);
			return getChar(blob, ptr, start + index);
		}

		@Override
		public CharSequence subSequence(int substart, int subend) {
			return new HeapCharSequence(blob, ptr, start + substart, start + subend);
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

		long address;
		long ptr;
		int start;
		int end;
		
		public AddressCharSequence(long address, long ptr, int start, int end) {
			ensureSubSequence(start, end);
			this.address = address;
			this.ptr = ptr;
			this.start = start;
			this.end = end;
		}
		
		@Override
		public int length() {
			return end - start;
		}

		@Override
		public char charAt(int index) {
			ensureIndex(start, end, index);
			return getChar(address, ptr, start + index);
		}

		@Override
		public CharSequence subSequence(int substart, int subend) {
			return new AddressCharSequence(address, ptr, start + substart, start + subend);
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
	
	private void ensureSubSequence(int start, int end) {
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
	
	private void ensureIndex(int start, int end, int index) {
		if (start + index >= end) {
			throw new IndexOutOfBoundsException("index=" + index + ", start=" + start + ", end=" + end);
		}
	}
	
}
