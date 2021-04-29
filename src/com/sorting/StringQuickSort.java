package com.sorting;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	/**
	 * print Method is used to print the given list.
	 *
	 */
/*	void print(String[] filesList) {
		int index =01;
		for(String element:filesList) {
			System.out.println(index+"--> "+element);
			index++;
		}
	}*/
	public static void main(String[] args) {
		ArrayList<String> fileNameList = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter folder path");
		String folderPath = sc.next();
		File f = new File(folderPath);
		String[] fileNmArray = f.list();
/*		for(String fileName:fileNmArray) {
			fileNameList.add(fileName);
		}*/
		System.out.println("Before Sorting ");
		for(String fileName:fileNmArray) {
			System.out.print(fileName);
		}
		System.out.println("___________________________");
		quickSort(fileNmArray, 0, fileNmArray.length-1,"Descending");
		System.out.println("After Descending Sorting ");
		for(String fileName:fileNmArray) {
			System.out.print(fileName);
		}
		System.out.println("___________________________");
		quickSort(fileNmArray, 0, fileNmArray.length-1,"Ascending");
		System.out.println("After Ascending Sorting ");
		for(String fileName:fileNmArray) {
			System.out.print(fileName);
		}
		System.out.println("___________________________");
	}

}
