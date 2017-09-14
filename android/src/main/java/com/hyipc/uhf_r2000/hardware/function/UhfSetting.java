package com.hyipc.uhf_r2000.hardware.function;

import com.halio.r2000;
import com.hyipc.uhf_r2000.hardware.assist.UhfMappingRelation;
import com.hyipc.uhf_r2000.hardware.assist.UhfSharedPreferenceUtil;
import com.hyipc.util.Logger;

/**
 * 超高频设置类
 */
public class UhfSetting {
	/**	波特率 */
	private byte mBaud;
	/**	最大频点 */
	private byte mMaxFre;
	/**	最小频点 */
	private byte mMinFre;
	/**	功率 */
	private byte mRfPower;
	/**	询查命令响应时间 */
	private int mScanTime;
	/**	蜂鸣器开关 */
	private boolean mIsBeepOn;
	
	public UhfSetting() {
	}
	
	
	/**
	 * @todo  设置波特率
	 * @return
	 */
	public boolean updateBaud(){
		if (!r2000.SetBaudRate(UhfComm.sAddr, mBaud)) {
			return false;
		} else {
			//断开连接
			boolean succ = r2000.DisConnectReader();
			if (succ) {
				//连接
				if (!r2000.ConnectReader(UhfComm.sAddr,mBaud)) {
					return false;
				} else {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * @todo  设置上下频点
	 * @return
	 */
	public boolean updateRegion() {
		return r2000.SetRegion(UhfComm.sAddr, mMaxFre, mMinFre);
	}
	
	/**
	 * @todo  设置读写器功率
	 * @return
	 */
	public boolean updateRfPower() {
		return r2000.SetRfPower(UhfComm.sAddr, mRfPower);
	}
	
	/**
	 * @todo  设置询查命令的最大响应时间
	 * @return
	 */
	public boolean updateInventoryScanTime() {
		return r2000.SetInventoryScanTime(UhfComm.sAddr,(byte)mScanTime);
	}
	
	public boolean updateBeepNotification(){
		if (mIsBeepOn) {
			return openBeep();
		}else {
			return closeBeep();
		}
	}
	
	/**
	 * @todo  打开蜂鸣器
	 * @return
	 */
	private boolean openBeep(){
		return r2000.SetBeepNotification(UhfComm.sAddr, UhfMappingRelation.BEEP_ON);
	}
	
	/**
	 * @todo  关闭蜂鸣器
	 * @return
	 */
	private boolean closeBeep(){
		return r2000.SetBeepNotification(UhfComm.sAddr, UhfMappingRelation.BEEP_OFF);
	}
	
	/**
	 * @todo  设置现有所有属性
	 * @return
	 */
	public boolean updateAll(){
		if (!updateBaud()) {
			return false;
		}
		
		if (!updateRegion()) {
			return false;
		}
		
		if (!updateRfPower()) {
			return false;
		}
		
		if (!updateInventoryScanTime()) {
			return false;
		}
		
		if (!updateBeepNotification()) {
			return false;
		}
		
		return true;
	}
	
	
	
	public byte getmBaud() {
		return mBaud;
	}
	public void setmBaud(byte mBaud) {
		this.mBaud = mBaud;
	}
	public byte getmMaxFre() {
		return mMaxFre;
	}
	public void setmMaxFre(byte mMaxFre) {
		this.mMaxFre = mMaxFre;
	}
	public byte getmMinFre() {
		return mMinFre;
	}
	public void setmMinFre(byte mMinFre) {
		this.mMinFre = mMinFre;
	}
	public byte getmRfPower() {
		return mRfPower;
	}
	public void setmRfPower(byte mRfPower) {
		this.mRfPower = mRfPower;
	}
	public int getmScanTime() {
		return mScanTime;
	}
	public void setmScanTime(int mScanTime) {
		this.mScanTime = mScanTime;
	}
	public boolean ismIsBeepOn() {
		return mIsBeepOn;
	}
	public void setmIsBeepOn(boolean mIsBeepOn) {
		this.mIsBeepOn = mIsBeepOn;
	}
}
