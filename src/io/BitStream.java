//package io;
//
///*
//* BitStream.java -- 读取位流
//* Copyright (C) 2010
//*
//* This program is free software: you can redistribute it and/or modify
//* it under the terms of the GNU General Public License as published by
//* the Free Software Foundation, either version 3 of the License, or
//* (at your option) any later version.
//*
//* This program is distributed in the hope that it will be useful,
//* but WITHOUT ANY WARRANTY; without even the implied warranty of
//* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//* GNU General Public License for more details.
//*
//* You should have received a copy of the GNU General Public License
//* along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*
//* If you would like to negotiate alternate licensing terms, you may do
//* so by contacting the author: <http://jmp123.sourceforge.net/>
//*/
//package jmp123.decoder;
//import jmp123.instream.IRandomAccess;
// 
//public final class BitStream {
//	private static final int RESELEN = 4096;
//	private static IRandomAccess iraInput;
//	private byte[] bitReservoir;
//	private int bitPos;
//	private int bytePos;
//	private int endPos;		//bitReservoir已填入的字节数
// 
//	public BitStream(IRandomAccess iraIn) {
//		iraInput = iraIn;
//		bitReservoir = new byte[RESELEN + 4];	// 长度不小于512+1732(最大帧长)
//	}
// 
//	public BitStream(int len) {
//		bitReservoir = new byte[len];			// 任意长度,用于帧边信息解码
//	}
// 
//	/**
//	 * 向bitReservoir添加len字节,RESELEN后尾部要空出4字节用于32bit的"2级缓冲".
//	 */
//	public int append(int len) {
//		if (len + endPos > RESELEN) {
//			//将缓冲区bytePos及之后的字节移动到缓冲区首
//			System.arraycopy(bitReservoir, bytePos, bitReservoir, 0, endPos - bytePos);
//			endPos -= bytePos;
//			bitPos = bytePos = 0;
//		}
//		try {
//			if(iraInput.read(bitReservoir, endPos, len) < len)
//				return -1;
//		} catch (Exception e) {
//			return -1;
//		}
//		endPos += len;
//		return len;
//	}
//	public void resetIndex() {
//		bytePos = bitPos = endPos = 0;
//	}
//	/**
//	 * 从缓冲区bitReservoir读取1 bit
//	 */
//	public int get1Bit() {
//		int bit = bitReservoir[bytePos] << bitPos;
//		bit >>= 7;
//		bit &= 0x1;
//		bitPos++;
//		bytePos += bitPos >> 3;
//		bitPos &= 0x7;
//		return bit;
//	}
// 
//	/*
//	 * 2 <= n <= 17
//	 */
//	public int getBits17(int n) {
//		int iret = bitReservoir[bytePos];
//		iret <<= 8;
//		iret |= bitReservoir[bytePos + 1] & 0xff;
//		iret <<= 8;
//		iret |= bitReservoir[bytePos + 2] & 0xff;
//		iret <<= bitPos;
//		iret &= 0xffffff;  // 高8位置0;
// 		iret >>= 24 - n;
//		bitPos += n;
//		bytePos += bitPos >> 3;
//		bitPos &= 0x7;
//		return iret;
//	}
// 
//	/**
//	 * 2<= n <= 9
//	 */
//	public int getBits9(int n) {
//		int iret = bitReservoir[bytePos];
//		iret <<= 8;
//		iret |= bitReservoir[bytePos + 1] & 0xff;
//		iret <<= bitPos;
//		iret &= 0xffff;  // 高16位置0;
//		iret >>= 16 - n;
//		bitPos += n;
//		bytePos += bitPos >> 3;
//		bitPos &= 0x7;
//		return iret;
//	}
// 
//	public int getBytePos() {
//		return bytePos;
//	}
// 
//	public int getBuffBytes() {
//		return endPos;
//	}
// 
//	public int getBitPos() {
//		return bitPos;
//	}
// 
//	/**
//	 * 返回值是0-255的无符号整数
//	 */
//	public int get1Byte() {
//		return bitReservoir[bytePos++] & 0xff;
//	}
// 
//	public void backBits(int n) {
//		bitPos -= n;
//		bytePos += bitPos >> 3;
//		bitPos &= 0x7;
//	}
// 
//	public void skipBytes(int nbytes) {
//		bytePos += nbytes;
//		bitPos = 0;
//	}
//}
