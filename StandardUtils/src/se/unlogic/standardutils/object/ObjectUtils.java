package se.unlogic.standardutils.object;


public class ObjectUtils {

	public static boolean compare(Object o1, Object o2) {
	    
		return (o1 == null ? o2 == null : o1.equals(o2));
	}
}
