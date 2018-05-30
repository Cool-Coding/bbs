package cn.yang.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate extends Date {

	private static final long serialVersionUID = -304524488310887673L;

	public MyDate(){}
	public MyDate(long date){
		super(date);
	}
	
	@Override
	public String toString() {
		SimpleDateFormat simpleformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleformat.format(this);
	}
}
