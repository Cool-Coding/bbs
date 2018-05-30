package cn.yang.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-6
 */
public class GenericUtil {
  
	/**
	 * 通过反射，得到指定类的泛型父类的实际类型参数的字节码。如  ForumServiceImpl extends DAOSupport<Forum>,得到Forum类的字节码
	 * @param clazz 指定类的字节码
	 * @param index 类型参数的索引，从0开始。
	 * @return 类型参数实例的字节码
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenericType(Class clazz,int index){
		Type superClass=clazz.getGenericSuperclass();//得到泛型父类
		if(!(superClass instanceof ParameterizedType)){//判断父类是否支持泛型
              return Object.class;
		}
		Type[] parameterTypes=((ParameterizedType)superClass).getActualTypeArguments();//得到泛型父类的所有类型参数的实例
		if(index<0 || index>=parameterTypes.length){
			throw new RuntimeException(index<0 ? "索引不能小于零" :"索引超出了范围");
		}
		if(!(parameterTypes[index] instanceof Class)){
			return Object.class;
		}
		return (Class)parameterTypes[index];
	}
	/**
	 * 通过反射，得到指定类的泛型父类的第一个实际类型参数的字节码。如  ForumServiceImpl extends DAOSupport<Forum>,得到Forum类的字节码
	 * @param clazz 指定类的字节码
	 * @param index 类型参数的索引，从0开始。
	 * @return 第一个类型参数实例的字节码
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenericType(Class clazz){
        return getSuperClassGenericType(clazz,0);
	}
}
