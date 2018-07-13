package phlaxyr.largetents.util;

import java.util.ArrayList;

public class Misc {
	

	
    @SuppressWarnings("unchecked")
	public static <T> ArrayList<T> toArrayList(T... a) {
        ArrayList<T> arrayList = new ArrayList<>();
    	for(T t : a) {
        	arrayList.add(t);
        }
    	return arrayList;
    }
}
