package com.sorting;

public class StringQuickSort {
	/**
	 * Swap Method is used to swap the files in the given List.
	 *
	 */
	static void swap(String[] fileList,int low,int high) {
		String temp = fileList[low];
		fileList[low] = fileList[high];
		fileList[high] = temp;
	}

	/**
	 * quickSort method is used to sort the given array List in either
	 * ascending order or descending order depending on the ordering input.
	 *
	 */
	public static String[] quickSort(String[] filesList,int low,int high,String ordering) {
		if(low >= high) {
			return filesList;
		}
		int pivotIndex = 0;
		if(ordering.equalsIgnoreCase("ascending")) {
			pivotIndex = listPartitionAscending(filesList,low,high);
			
		}else if(ordering.equalsIgnoreCase("descending")) {
			pivotIndex = listPartitionDescending(filesList,low,high);			
		}
		
		quickSort(filesList,low, pivotIndex-1,ordering);
		quickSort(filesList,pivotIndex+1,high,ordering);

		return filesList;

	}
	
	/**
	 * listPartitionDescending Method is used to partition the given 
	 * list by finding the pivot element and helps to arrange the
	 * list in descending order .
	 *
	 */
	static private int listPartitionDescending(String[] filesList, int low, int high) {
		String pivot = filesList[low];
		int partitionLow = low;
		int partitionHigh = high;
		while(partitionLow < partitionHigh) {

			while((pivot.compareToIgnoreCase(filesList[partitionLow]) <= 0) &&(partitionLow < partitionHigh) ) {
				partitionLow++;
			}
			while(pivot.compareToIgnoreCase(filesList[partitionHigh]) > 0) {
				partitionHigh--;
			}

			if(partitionLow < partitionHigh) {
				swap(filesList, partitionLow, partitionHigh);
			}
		}

		swap(filesList,low,partitionHigh);
		return partitionHigh;
	}

	/**
	 * listPartitionAscending Method is used to partition the given 
	 * list by finding the pivot element and helps to arrange the
	 * list in ascending order .
	 *
	 */
	static private int listPartitionAscending(String[] filesList, int low, int high) {
		String pivot = filesList[low];
		int partitionLow = low;
		int partitionHigh = high;
		while(partitionLow < partitionHigh) {

			while((pivot.compareToIgnoreCase(filesList[partitionLow]) >= 0) &&(partitionLow < partitionHigh) ) {
				partitionLow++;
			}
			while(pivot.compareToIgnoreCase(filesList[partitionHigh]) < 0) {
				partitionHigh--;
			}

			if(partitionLow < partitionHigh) {
				swap(filesList, partitionLow, partitionHigh);
			}
		}

		swap(filesList,low,partitionHigh);
		return partitionHigh;
	}
	

}
