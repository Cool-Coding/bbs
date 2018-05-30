package cn.yang.util;

import org.apache.commons.beanutils.Converter;

/**
 * 可以转换任何枚举类型。用String型的枚举常量转为相应的枚举对象。
 * 
 * @author tyg
 * 
 */
@SuppressWarnings("unchecked")
public class GenericEnumConverter implements Converter {

	private Class<? extends Enum> enumClass;

	/**
	 * 需要指定所要转换的枚举类型
	 * 
	 * @param enumClass
	 */
	public GenericEnumConverter(Class<? extends Enum> enumClass) {
		this.enumClass = enumClass;
	}

	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		}

		if (value.getClass().equals(enumClass)) {
			return value;
		}

		if (value instanceof String) {
			try {
				return Enum.valueOf(enumClass, (String) value);
			} catch (RuntimeException e) {
				return null;
			}
		}

		throw new UnsupportedOperationException("不能转换的类型：" + value.getClass());
	}
}
