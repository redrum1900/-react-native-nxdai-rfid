package com.hyipc.uhf_r2000.hardware.assist;

public interface UhfReadListener {
	public void onContentCaughted(Object[] obj);
	public void onErrorCaughted(String error);
}
