package com.hyipc.uhf_r2000.hardware.function;

import android.content.Context;

import com.halio.r2000;
import com.hyipc.util.Logger;

public class UhfComm {
	/**	读写器地址 */
	public static byte[] sAddr = {(byte)0xFF};
	
	public boolean init(){
		r2000.ModulePowerOn();
		return r2000.ConnectReader(sAddr, (byte) 5);
	}
	
	public boolean unInit(){
		if (r2000.DisConnecxtReader()) {
			r2000.ModulePowerOff();
			return true;
		}
		return false;
	}
}
