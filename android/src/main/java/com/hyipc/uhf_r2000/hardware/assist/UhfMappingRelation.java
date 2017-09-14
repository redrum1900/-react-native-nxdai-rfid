package com.hyipc.uhf_r2000.hardware.assist;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hyipc.util.Logger;

import android.text.TextUtils;

/**
 * 超高频方法中的参数 与 界面显示内容映射关系
 */
public class UhfMappingRelation {
	public static final byte ERR1 = (byte)-1;
	public static final String ERR2 = "";
	
	public static final byte BEEP_ON = 1;
	public static final byte BEEP_OFF = 0;
	
	private final String UNIT_BAUD = "bps";
	private final String UNIT_FRE = "MHz";
	private final String UNIT_SCAN_TIME = "*100ms";
	
	/** 波特率 */
	private Map<Byte, String> mMapBaud;
	/** 区 */
	private Map<Byte, String> mMapMem;

	
	/**
	 * baudrate	实际波特率
		0	9600bps
		1	19200 bps
		2	38400 bps
		5	57600 bps
		6	115200 bps
	 */
	private void initBaud(){
		mMapBaud = new HashMap<Byte, String>();
		mMapBaud.put((byte) 0, "9600"+UNIT_BAUD);
		mMapBaud.put((byte) 1, "19200"+UNIT_BAUD);
		mMapBaud.put((byte) 2, "38400"+UNIT_BAUD);
		mMapBaud.put((byte) 5, "57600"+UNIT_BAUD);
		mMapBaud.put((byte) 6, "115200"+UNIT_BAUD);
	}
	
	/**
	 * 0x00: 保留区；
	   0x01：EPC存储器；
       0x02：TID存储器；
       0x03：用户存储器。
	 */
	private void initMem(){
		mMapMem = new HashMap<Byte, String>();
		mMapMem.put((byte) 0, "Reservations");
		mMapMem.put((byte) 1, "EPC");
		mMapMem.put((byte) 2, "TID");
		mMapMem.put((byte) 3, "Users");
	}
	
	public String[] getAllBaudValue(){
		if (mMapBaud == null) {
			initBaud();
		}
		
		String[] result = new String[mMapBaud.size()];
		int index = 0;
		for (Entry entry : mMapBaud.entrySet()) {
			result[index++] = (String)(entry.getValue());
		}
		return result;
	}

	public String getBaudValue(byte Key) {
		if (mMapBaud == null) {
			initBaud();
		}
		if (mMapBaud.containsKey(Key)) {
			return mMapBaud.get(Key);
		}
		return ERR2;
	}

	public byte getBaudKey(String value){
		if (TextUtils.isEmpty(value)) {
			return ERR1;
		}
		if (mMapBaud == null) {
			initBaud();
		}
		for (Entry entry : mMapBaud.entrySet()) {
			if(value.equalsIgnoreCase((String)(entry.getValue()))){
				return (Byte)(entry.getKey());
			}
		}
		return ERR1;
	}
	
	
	/**
	 * 1、频点 + 频段 = 频率
	 * 2、最大频点和最小频点取值范围都是0~49
	 * 3、相同频点对应的最大频率和最小频率的值不一样，因为最大频段和最小频段不一样
	 * 4、频段采用 US band 最大频段规则：Bit7:0 Bit6:0      最小频段规则：Bit7:1 Bit6:0
	 * 5、设置频段方法中的参数是频率
	 * 6、最大频率和最小频率在界面显示的MHz是一样的，原因是最大频点和最小频点取值范围都是0~49
	 */
	
	/** Fs = 902.75 + N * 0.5 (MHz) 其中N∈[0,49]
	 * @todo  频点转MHz
	 * @param key N
	 * @return Fs
	 */
	public String getFreValue(byte key){
		if (key > 49 || key < 0) {
			return ERR2;
		}
		return Double.toString(902.75 + (double)key * 0.5)+UNIT_FRE;
	}
	
	public String[] getAllFreValue(){
		//0~49
		String[] result = new String[50];
		for (int i = 0; i < result.length; i++) {
			String freValue = getFreValue((byte)i);
			result[i] = freValue;
		}
		return result;
	}
	
	/**
	 * @todo  获取最终最大频率（频段+频点）
	 * @param value Fs
	 * @return
	 */
	public byte getMaxFreKey(String value){
		byte key = getFreKey(value);
		if (key == ERR1) {
			return ERR1;
		}
		return toMaxFre(key);
	}
	
	/**
	 * @todo  获取最小频率（频段+频点）
	 * @param value Fs
	 * @return
	 */
	public byte getMinFreKey(String value){
		byte key = getFreKey(value);
		if (key == ERR1) {
			return ERR1;
		}
		return toMinFre(key);
	}
	
	/**Fs = 902.75 + N * 0.5 (MHz) 其中N∈[0,49]
	 * @todo  MHz转频点
	 * @param value Fs
	 * @return N
	 */
	private byte getFreKey(String value){
		if (TextUtils.isEmpty(value)) {
			return ERR1;
		}
		if (value.endsWith(UNIT_FRE)) {
			int index = value.indexOf(UNIT_FRE);
			value = value.substring(0, index);
		}
		
		double dValue = Double.parseDouble(value);
		byte key = (byte)((dValue - 902.75) / 0.5);
		if (key >= 0 & key <= 49) {
			return key;
		}
		return ERR1;
	}
	
	/** 
	 * @todo 最大频点转频率 (频点+频段 = 频率，频段采用 US band Bit7:0 Bit6:0)
	 * @param key 频点
	 * @return 频率
	 */
	private byte toMaxFre(byte key){
		return key;
	}
	
	/**
	 * @todo  最大频率转频点 (频点+频段 = 频率，频段采用 US band Bit7:0 Bit6:0)
	 * @param key 频率
	 * @return 频点
	 */
	public byte reverseMaxFre(byte key){
		return key;
	}
	
	
	/** 
	 * @todo 最小频点转频率 (频点+频段 = 频率，频段采用 US band Bit7:1 Bit6:0)
	 * @param key 频点
	 * @return 频率
	 */
	private byte toMinFre(byte key){
		return (byte)(0x80 | key);
	}
	
	/** 
	 * @todo 最小频率转频点 (频点+频段 = 频率，频段采用 US band Bit7:1 Bit6:0)
	 * @param key 频率
	 * @return 频点
	 */
	public byte reverseMinFre(byte key){
		return (byte)(0x3F & key);
	}
	
	
	public String[] getAllRfPowerValue(){
		//0~30
		String[] result = new String[31];
		for (int i = 0; i < result.length; i++) {
			result[i] = i+"";
		}
		return result;
	}

	
	public String[] getAllScanTimeValue(){
		//3~255
		String[] result = new String[253];
		int index = 0;
		for (int i = 3; i <= 255; i++) {
			result[index++] = getScanTimeValue(i);
		}
		return result;
	}
	
	public String getScanTimeValue(int key){
		Logger.D("key:"+key);
		if (key > 255 || key < 3) {
			return ERR2;
		}
		return key+UNIT_SCAN_TIME;
	}
	
	public int getScanTimeKey(String value){
		if (TextUtils.isEmpty(value)) {
			return ERR1;
		}
		if (value.endsWith(UNIT_SCAN_TIME)) {
			int index = value.indexOf(UNIT_SCAN_TIME);
			value = value.substring(0, index);
		}
		
		int iValue = Integer.parseInt(value);
		if (iValue > 255 || iValue < 3) {
			return ERR1;
		}
		return iValue;
	}
	
	
	
	public boolean isBeepOn(byte key){
		if (key == BEEP_ON) {
			return true;
		}else{
			return false;
		}
	}
	
	public byte getBeepKey(boolean isBeepOn){
		if (isBeepOn) {
			return BEEP_ON;
		}else {
			return BEEP_OFF;
		}
	}
	
	/**
	 * @todo  区块
	 * @param key
	 * @return
	 */
	public String getMemValue(byte key){
		if (mMapMem == null) {
			initMem();
		}
		
		if (mMapMem.containsKey(key)) {
			return mMapMem.get(key);
		}
		return ERR2;
	}
	
	/**
	 * @todo  区块
	 * @param 
	 * @return
	 */
	public byte getMemKey(String value){
		if (TextUtils.isEmpty(value)) {
			return ERR1;
		}
		if (mMapMem == null) {
			initMem();
		}
		for (Entry entry : mMapMem.entrySet()) {
			if (value.equalsIgnoreCase((String)(entry.getValue()))) {
				return (Byte)(entry.getKey());
			}
		}
		return ERR1;
	}
	
	public String[] getAllMen(){
		if (mMapMem == null) {
			initMem();
		}
		String[] result = new String[mMapMem.size()];
		int index = 0;
		for (Entry entry : mMapMem.entrySet()) {
			result[index++] = (String)(entry.getValue());
		}
		return result;
	}
	
}
