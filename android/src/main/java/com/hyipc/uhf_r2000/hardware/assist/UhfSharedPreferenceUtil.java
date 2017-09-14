package com.hyipc.uhf_r2000.hardware.assist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UhfSharedPreferenceUtil {
	private static UhfSharedPreferenceUtil sInstance = null;
	private SharedPreferences mPreferences = null;
	private static final String BAUD = "baud";
	private static final String MEM_READ = "mem_read";
	private static final String START_ADDR_READ = "startAddr_read";
	private static final String NUM_READ = "num_read";
	private static final String VOICE = "voice";
	
	public static final int VOICE_SYSTEM = 1;
	public static final int VOICE_CUSTOM = 2;
	
	
	public static UhfSharedPreferenceUtil getInstance(Context ctx){
		if (sInstance == null) {
			sInstance = new UhfSharedPreferenceUtil(ctx);
		}
		return sInstance;
	}
	
	public UhfSharedPreferenceUtil(Context ctx) {
		mPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
	}
	
	public void saveBaud(int baud){
		mPreferences.edit().putInt(BAUD, baud).commit();
	}
	
	public int getBaud(){
		return mPreferences.getInt(BAUD, UhfDefaultConfig.BAUD);
	}
	
	public void saveMem_read(int mem){
		mPreferences.edit().putInt(MEM_READ, mem).commit();
	}
	
	public int getMem_read(){
		return mPreferences.getInt(MEM_READ, UhfDefaultConfig.MEM_READ);
	}
	
	public void saveStartAddr_read(int startAddr){
		mPreferences.edit().putInt(START_ADDR_READ, startAddr).commit();
	}
	
	public int getStartAddr_read(){
		return mPreferences.getInt(START_ADDR_READ, UhfDefaultConfig.START_ADDR_READ);
	}
	
	public void saveNum_read(int num){
		mPreferences.edit().putInt(NUM_READ, num).commit();
	}
	
	public int getNum_read(){
		return mPreferences.getInt(NUM_READ, UhfDefaultConfig.NUM_READ);
	}
	
	public void saveVoice(int voice){
		mPreferences.edit().putInt(VOICE, voice).commit();
	}
	
	public int getVoice(){
		return mPreferences.getInt(VOICE, UhfDefaultConfig.VOICE);
	}
	
}
