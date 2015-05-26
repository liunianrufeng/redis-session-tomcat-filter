/**
 * <b>项目名：</b>redis-session-tomcat-filter<br/>
 * <b>包名：</b>com.xuwei.util<br/>
 * <b>文件名：</b>Expression.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年5月23日-下午1:52:36<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

/**
 * 
 * <b>类名称：</b>Expression<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年5月23日 下午1:52:36<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class Expression {
	/**
	 * 
	 * eval(根据方法名或者字段名反射获取)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param target
	 * @param expr
	 * @return 
	 * Object
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public static Object eval(Object target, String expr)
	  {
	    if ((expr == null) || (!expr.startsWith("."))) throw new RuntimeException("invalid expression: " + expr);

	    StringTokenizer tokenizer = new StringTokenizer(expr, ".");

	    Object ref = target;
	    while (tokenizer.hasMoreTokens()) {
	      String token = tokenizer.nextToken();
	      try
	      {
	        if (token.endsWith("()"))
	          ref = invokeMethod(token.replace("()", ""), ref);
	        else
	          ref = getField(token, ref);
	      }
	      catch (Throwable t) {
	        if ((t instanceof InvocationTargetException)) {
	          t = ((InvocationTargetException)t).getTargetException();
	        }

	        if ((t instanceof Error)) throw ((Error)t);
	        if ((t instanceof RuntimeException)) throw ((RuntimeException)t);
	        throw new RuntimeException(t);
	      }
	    }

	    return ref;
	  }

	  private static Object getField(String fieldName, Object ref)
	    throws IllegalArgumentException, IllegalAccessException
	  {
	    Class c = ref.getClass();
	    while (c != null) {
	      try {
	        Field field = c.getDeclaredField(fieldName);
	        field.setAccessible(true);
	        return field.get(ref);
	      } catch (NoSuchFieldException e) {
	        c = c.getSuperclass();
	      }

	    }

	    throw new NoSuchFieldError("No field named " + fieldName + " found in " + ref.getClass());
	  }

	  private static Object invokeMethod(String methodName, Object ref) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	  {
	    Class c = ref.getClass();
	    while (c != null) {
	      try {
	        Method m = c.getDeclaredMethod(methodName, new Class[0]);
	        m.setAccessible(true);
	        return m.invoke(ref, new Object[0]);
	      } catch (NoSuchMethodException e) {
	        c = c.getSuperclass();
	      }

	    }

	    throw new NoSuchMethodError("No method named " + methodName + " found in " + ref.getClass());
	  }
}
