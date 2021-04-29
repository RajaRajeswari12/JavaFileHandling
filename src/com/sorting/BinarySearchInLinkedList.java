package com.sorting;

import java.util.LinkedList;

public class BinarySearchInLinkedList {

	public static boolean binarySearch(LinkedList<String> listName,String fileNameToBeSearched) {
		int lowIndex = 0;
		int TopIndex = listName.size() -1;
		while(lowIndex < TopIndex) {
			int midIndex=lowIndex+((TopIndex-lowIndex)/2);
			int stringComparisionResult = fileNameToBeSearched.compareToIgnoreCase(listName.get(midIndex));
			
			if(stringComparisionResult == 0) {
				return true;
			}else if(stringComparisionResult < 0) {
				TopIndex = midIndex-1;
			}else if(stringComparisionResult > 0) {
				lowIndex = midIndex+1;
			}
		}
		return false;
	}
}
