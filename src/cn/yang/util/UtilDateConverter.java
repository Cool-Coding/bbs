package cn.yang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

/**
 * 
 * @author 汤阳光 Nov 28, 2008
 */
public class UtilDateConverter implements Converter {
	private SimpleDateFormat sdf;

	/**
	 * 默认日期的格式为：yyyy-MM-dd
	 */
	public UtilDateConverter() {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
	}

	/**
	 * 
	 * @param pattern 日期的格式
	 */
	public UtilDateConverter(String pattern) {
		sdf = new SimpleDateFormat(pattern);
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof java.util.Date) {
			return value;
		}

		if (value instanceof String) {
			try {
				return sdf.parse((String) value);
			} catch (ParseException e) {
				return null;
			}
		}

		throw new UnsupportedOperationException("不支持的类型：" + value.getClass());
	}

}
