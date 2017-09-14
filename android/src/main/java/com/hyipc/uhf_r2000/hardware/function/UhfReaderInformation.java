package com.hyipc.uhf_r2000.hardware.function;

import java.util.IllegalFormatCodePointException;

import android.widget.Toast;

import com.halio.r2000;
import com.hyipc.uhf_r2000.hardware.assist.UhfMappingRelation;
import com.hyipc.util.Logger;

public class UhfReaderInformation {
	private String version;
	private byte maxFre;
	private byte minFre;
	private byte rfPower;
	private int scanTime;
	private boolean isBeepOn;
	private boolean isAntOn;
	
	public UhfReaderInformation() {

	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public byte getMaxFre() {
		return maxFre;
	}
	public void setMaxFre(byte maxFre) {
		this.maxFre = maxFre;
	}

	public byte getMinFre() {
		return minFre;
	}

	public void setMinFre(byte minFre) {
		this.minFre = minFre;
	}

	public byte getRfPower() {
		return rfPower;
	}
	public void setRfPower(byte rfPower) {
		this.rfPower = rfPower;
	}
	public int getScanTime() {
		return scanTime;
	}
	public void setScanTime(int scanTime) {
		this.scanTime = scanTime;
	}
	public boolean isBeepOn() {
		return isBeepOn;
	}
	public void setBeepOn(boolean isBeepOn) {
		this.isBeepOn = isBeepOn;
	}
	public boolean isAntOn() {
		return isAntOn;
	}
	public void setAntOn(boolean isAntOn) {
		this.isAntOn = isAntOn;
	}
	
	
	public boolean init() {
		byte Version[] = new byte[2];
		byte Model[] = new byte[1];
		byte SupProtocol[] = new byte[1];
		byte dmaxfre[] = new byte[1];
		byte dminfre[] = new byte[1];
		byte power[] = new byte[1];
		byte ScanTime[] = new byte[1];
		byte Ant[] = new byte[1];

		byte BeepEn[] = new byte[1];
		byte OutputRep[] = new byte[1];
		byte CheckAnt[] = new byte[1];
		
		int failCount = 0;
		while (!r2000.GetReaderInformation(UhfComm.sAddr, Version, Model, SupProtocol, dmaxfre, dminfre, power, ScanTime, Ant, BeepEn, OutputRep, CheckAnt)) {
			failCount ++;
			if (failCount > 10) {
				//失败时多次尝试
				return false;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String version = Version[0] + "." + Version[1];
		setVersion(version);
		
		byte maxFrequency = dmaxfre[0];
		setMaxFre(maxFrequency);

		byte minFrequency = dminfre[0];
		setMinFre(minFrequency);
		
		byte powerBm = power[0];
		setRfPower(powerBm);
		
		int scanTime = ScanTime[0];
		Logger.D("init scanTime:"+scanTime);
		setScanTime(scanTime);

		byte beepEn = BeepEn[0];
		if (beepEn == UhfMappingRelation.BEEP_ON) {
			setBeepOn(true);
		}else {
			setBeepOn(false);
		}
		
		byte ant = CheckAnt[0];
		if (ant == 1) {
			setAntOn(true);
		}else {
			setAntOn(false);
		}
		return true;
	}
}
