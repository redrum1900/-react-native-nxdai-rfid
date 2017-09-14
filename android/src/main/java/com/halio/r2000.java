package com.halio;

public class r2000 {
	public static native boolean doTest();
	
	public static native void ModulePowerOn();
	public static native void ModulePowerOff();
	public static native boolean ConnectReader(byte[] ComAdr,byte baud);
	public static native boolean DisConnectReader();
	
	public static native boolean SetAddress(byte[] ComAdr, byte ComAdrData);
	public static native boolean SetInventoryScanTime(byte[] ComAdr, byte ScanTime);
	public static native boolean SetRfPower (byte[] ComAdr,byte powerDbm);
	public static native boolean SetRegion(byte[] ComAdr, byte dmaxfre, byte dminfre);
	public static native boolean SetBaudRate (byte[] ComAdr, byte baud);
	public static native boolean SetBeepNotification(byte[] ComAdr, byte BeepEn);
	public static native boolean SetCheckAnt(byte[] ComAdr, byte CheckAnt);
	public static native boolean GetReaderInformation(byte[] ComAdr,byte[] VersionInfo,byte[] ReaderType, byte[] TrType,
			byte[] dmaxfre, byte[] dminfre,byte[] powerdBm, byte[] ScanTime,byte[] Ant,byte[] BeepEn,byte[] OutputRep, byte[] CheckAnt);


	public static native boolean InventoryG2(byte[] ComAdr,byte QValue,byte Session,byte MaskMem,byte[] MaskAdr,byte MaskLen,byte[] MaskData,byte MaskFlag, byte AdrTID, byte LenTID, byte TIDFlag,byte[] EPClenandEPC, byte[] Ant, int[] Totallen, int[] CardNum);
	public static native boolean ReadDataG2 (byte[] ComAdr,byte[] EPC, byte Enum,byte Mem, byte WordPtr, byte Num,byte[] Password,byte MaskMem,byte[] MaskAdr,byte MaskLen, byte[] MaskData, byte[] Data ,int[] errorcode);
	public static native boolean WriteDataG2(byte[] ComAdr, byte[] EPC,byte Wnum, byte Enum,byte Mem,byte WordPtr,byte[] Writedata,byte[] Password, byte  MaskMem,byte[] MaskAdr,byte MaskLen,byte[] MaskData,int[] errorcode);
	public static native boolean BlockEraseG2 (byte[] ComAdr, byte[] EPC, byte Enum,byte Mem, byte WordPtr, byte Num, byte[] Password,byte MaskMem,byte[] MaskAdr, byte MaskLen,byte[] MaskData, int[] errorcode);
	public static native boolean LockG2 (byte[] ComAdr, byte[] EPC,byte Enum, byte select,byte setprotect, byte[] Password,byte MaskMem,byte[] MaskAdr, byte  MaskLen,byte[] MaskData, int[] errorcode);
	public static native boolean KillTagG2 (byte[] ComAdr,byte[]EPC, byte Enum,byte[] Password,byte MaskMem,byte[] MaskAdr, byte MaskLen,byte[] MaskData, int[] errorcode);
	public static native boolean SetPrivacyByEPCG2 (byte[] ComAdr,byte[] EPC, byte Enum,byte[] Password, byte MaskMem,byte[] MaskAdr,byte MaskLen,byte[] MaskData,int[] errorcode);
	public static native boolean SetPrivacyWithoutEPCG2 (byte[] ComAdr,byte[] Password, int[] errorcode);
	public static native boolean ResetPrivacyG2(byte[] ComAdr, byte[] Password, int[] errorcode);
	public static native boolean CheckPrivacyG2 (byte[] ComAdr, byte[] Readpro ,int[] errorcode);
	public static native boolean EASConfigureG2 (byte[] ComAdr,byte[] EPC, byte Enum,byte[] Password,byte EAS,byte MaskMem,byte[] MaskAdr,byte MaskLen,byte[] MaskData, int[] errorcode);
	public static native boolean EASAlarmG2 (byte[] ComAdr, int[] errorcode);
	public static native boolean BlockLockG2 (byte[] ComAdr,byte[] EPC, byte Enum,byte[] Password,byte WrdPointer,byte MaskMem,byte[] MaskAdr,byte MaskLen,byte[] MaskData,int[] errorcode);
	public static native boolean WriteEPCG2(byte[] ComAdr,byte[] Password,byte[] WriteEPC,byte ENum,int[] Errorcode);
	
	static {
		System.loadLibrary("hyio_gpio_api");
		System.loadLibrary("hyio_uart_api");
		System.loadLibrary("halio_uhf_r2000"); 
	}
}
