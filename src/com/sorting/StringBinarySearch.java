package com.sorting;

public class StringBinarySearch {
	/**
	 * binarySearch Method is used to find whether the given file is exist
	 * in the given list or not. Used binary search technique(i.e by finding the 
	 * mid Index and comparing). 
	 *
	 */
	public static boolean binarySearch(String[] fileNameList,String fileName) {
		int minIndex = 0;
		int maxIndex = fileNameList.length-1;
		while(minIndex <= maxIndex) {
			int midIndex = minIndex+(maxIndex-minIndex)/2;
			
			int comparisionResult = fileName.compareToIgnoreCase(fileNameList[midIndex]);
			
			if(comparisionResult == 0) {
				return true;
			}else if(comparisionResult > 0) {
				minIndex =  midIndex +1;
			}else if(comparisionResult < 0){
				maxIndex = midIndex-1;
			}

		}
		return false;
	}

}
