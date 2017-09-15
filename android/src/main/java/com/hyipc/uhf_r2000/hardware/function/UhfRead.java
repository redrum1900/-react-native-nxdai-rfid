package com.hyipc.uhf_r2000.hardware.function;

import android.content.Context;
import android.text.TextUtils;

import com.hyipc.uhf_r2000.hardware.assist.UhfReadListener;
import com.hyipc.util.Logger;

/**
 * 读标签类
 */
public class UhfRead extends Thread {
	/** 起始地址 */
	private byte mWordPtr;
	/** 读取的字的个数 */
	private byte mNum;
	/** 读取的存储区 */
	private byte mMem;
	/** 监听器，接收标签信息和错误信息 */
	private UhfReadListener mListener;

	private int mIntervalTime = 5;// ms

	private boolean mIsLoop = true;
	private boolean mIsPause = false;

	public UhfRead(UhfReadListener listener) {
		mListener = listener;
	}
	
	@Override
	public void run() {
		try {
			byte[] ComAdr = new byte[1];
			ComAdr[0] = (byte) 0x00;
			// === InventoryG2 的参数 start ====
			byte QValue = 4;// how many cards scan for one time
			byte Session = 0;
			byte MaskMem = 1;// te:must be 1
			byte[] MaskAdr = new byte[2];
			MaskAdr[0] = MaskAdr[1] = 0;
			byte MaskLen = 8;
			byte[] MaskData = new byte[1];
			MaskData[0] = (byte) 0;
			byte MaskFlag = 0;
			byte AdrTID = 0;
			byte LenTID = 15;
			byte TIDFlag = 0;// te:must be zero
			byte[] pEPCList = new byte[4000];
			byte[] Ant1 = new byte[1];
			int[] Totallen = new int[1];
			int[] CardNum = new int[1];
			// === InventoryG2 的参数 end ====

			StringBuffer sbContent = new StringBuffer();

			// ==== ReadDataG2的参数 start ====
			int totalLength;
			byte[] pepList;
			byte[] EPC2;
			byte Enum2 = (byte) 6; // EPC字长度
			byte[] Password2 = { (byte) 0, (byte) 0, (byte) 0, (byte) 0 };// 4字节访问密码
			byte MaskMem2 = (byte) 1; // 掩码区,0x01：EPC存储区；0x02：TID存储区；0x03：用户存储区。
			byte[] MaskAdr2 = { (byte) 0, (byte) 0 }; // 掩码的起始位地址
			byte MaskLen2 = (byte) 8; // 掩码的位长度
			byte[] MaskData2 = { (byte) 0 }; // 掩码数据。MaskData数据字节长度是MaskLen/8。

			byte[] Data2 = new byte[256];
			int[] errorcode2 = new int[1];
			int dataLength;
			String strnum;
			String[] contents;
			// ==== ReadDataG2的参数 end ====
			
			while (mIsLoop) {
                boolean Inventoried = com.halio.r2000.InventoryG2(UhfComm.sAddr, QValue, Session, MaskMem, MaskAdr, MaskLen, MaskData, MaskFlag, AdrTID, LenTID, TIDFlag, pEPCList, Ant1, Totallen, CardNum);
				if (!Inventoried) {
					showErr("InventoryG2 fail");
					Logger.D("fail========");
				} else {
					if (Totallen[0] != 0 && !mIsPause) {
						if (mMem == 1) {
							// 读 EPC
							Logger.D("succ");
							totalLength = Totallen[0];
							contents = new String[CardNum[0]];
							int index_contents = 0;
							int index = 0;
							while (totalLength > index && index < pEPCList.length) {
								int length = pEPCList[index];
								Logger.D("len:"+length);
								for (int i = index + 1; i < index + length + 1; i++) {
									if (i >= pEPCList.length) {
										Logger.D("break");
										break;
									}
									strnum = Integer.toHexString(pEPCList[i] & 0xFF);
									if (strnum.length() == 1) {
										sbContent.append("0");
									}
									sbContent.append(strnum);
									sbContent.append("");
								}
								if (!TextUtils.isEmpty(sbContent.toString())) {
									contents[index_contents++] = sbContent.toString();
								}
								Logger.D("content:"+sbContent.toString());
								index += (length + 2);

								sbContent.setLength(0);
							}
							showContent(contents);
						} else {
							// 非 EPC 区内容
							totalLength = Totallen[0];
							contents = new String[1];
							pepList = new byte[totalLength - 2];
							for (int i = 0; i < totalLength - 2; i++) {
								pepList[i] = pEPCList[i + 1];
							}

							EPC2 = pepList;

							if (!com.halio.r2000.ReadDataG2(UhfComm.sAddr, EPC2, Enum2, mMem, mWordPtr, mNum, Password2, MaskMem2, MaskAdr2, MaskLen2, MaskData2, Data2, errorcode2)) {
								showErr("ReadData fail");
							} else {
								dataLength = mNum * 2;
								for (int i = 0; i < dataLength; i++) {
									strnum = Integer.toHexString(Data2[i] & 0xFF);
									if (strnum.length() == 1) {
										sbContent.append("0");
									}
									sbContent.append(strnum);
									sbContent.append("");
								}
								contents[0] = sbContent.toString();
								showContent(contents);
								sbContent.setLength(0);
							}

						}
					}
					try {
						Thread.sleep(mIntervalTime);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (mIsPause) {
					try {
						synchronized (this) {
							this.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.D("e:"+e.getMessage());
		}
	}

	public synchronized void pause() {
		mIsPause = true;
	}

	public synchronized void reStart() {
		mIsPause = false;
		this.notify();
	}

	public synchronized void destroy() {
		mIsLoop = false;
		mIsPause = false;
		this.notify();
	}

	private void showContent(Object[] obj) {

		if (mListener != null) {
			mListener.onContentCaughted(obj);
		}
	}

	private void showErr(String err) {
		if (mListener != null) {
			mListener.onErrorCaughted(err);
		}
	}

	public byte getmWordPtr() {
		return mWordPtr;
	}

	public void setmWordPtr(byte mWordPtr) {
		this.mWordPtr = mWordPtr;
	}

	public byte getmNum() {
		return mNum;
	}

	public void setmNum(byte mNum) {
		this.mNum = mNum;
	}

	public byte getmMem() {
		return mMem;
	}

	public void setmMem(byte mMem) {
		this.mMem = mMem;
	}

	public void setmIntervalTime(int time) {
		this.mIntervalTime = time;
	}

}
