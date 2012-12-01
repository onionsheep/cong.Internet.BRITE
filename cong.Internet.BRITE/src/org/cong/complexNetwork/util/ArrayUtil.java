package org.cong.complexNetwork.util;

public class ArrayUtil {

	public static double getMiddle(double[] a, double target) {
		int low = 0;
		int high = a.length - 1;
		int mid = (low + high) / 2;;
		
		while(low < high){
			if(target < a[mid]){
				high = mid;
			}else if (target > a[mid]){
				low = mid;
			}else{
				break;
			}
			mid = (low + high) / 2;
		}
		
		
		return a[mid];
	}
}
