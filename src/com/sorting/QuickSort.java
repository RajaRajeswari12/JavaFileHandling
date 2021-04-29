package com.sorting;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class QuickSort {

	public static void quickSort(LinkedList<String> fileNameList,int lowIndex,int topIndex,String ordering) {
		if(lowIndex >= topIndex) {
			return;
		}
		int pivotIndex = 0;
		if(ordering.equalsIgnoreCase("Ascending")) {
			pivotIndex = sortPartitionInAscending(fileNameList, lowIndex, topIndex);
		}else if(ordering.equalsIgnoreCase("Descending")) {
			pivotIndex = sortPartitionInDescending(fileNameList, lowIndex, topIndex);
		}

		quickSort(fileNameList,0,pivotIndex,ordering);
		quickSort(fileNameList,pivotIndex+1,topIndex,ordering);
	}

		public static int sortPartitionInAscending(LinkedList<String> fileNameList,int lowIndex,int topIndex) {
		int partitionLowIndex = lowIndex;
		int partitionTopIndex = topIndex;
		String pivot = fileNameList.get(partitionLowIndex);
		while(partitionLowIndex < partitionTopIndex) {
			
			while(pivot.compareToIgnoreCase(fileNameList.get(partitionLowIndex)) >= 0 && partitionLowIndex < partitionTopIndex) {
				partitionLowIndex++;
			}
			while(pivot.compareToIgnoreCase(fileNameList.get(partitionTopIndex)) < 0) {
				partitionTopIndex--;
			}
			
			if(partitionLowIndex < partitionTopIndex) {
				swap(fileNameList, partitionLowIndex, partitionTopIndex);
			}
			
		}
		
		swap(fileNameList,0,partitionTopIndex);
		return partitionTopIndex;
	}

	public static int sortPartitionInDescending(LinkedList<String> fileNameList,int lowIndex,int topIndex) {
		int partitionLowIndex = lowIndex;
		int partitionTopIndex = topIndex;
	
		String pivot = fileNameList.get(partitionLowIndex);

		while(partitionLowIndex < partitionTopIndex) {
			while(pivot.compareToIgnoreCase(fileNameList.get(partitionLowIndex))<=0 && partitionLowIndex < partitionTopIndex){
				partitionLowIndex++;
			}

			while((pivot.compareToIgnoreCase(fileNameList.get(partitionTopIndex))) > 0) {
				partitionTopIndex--;
			}
			if(partitionLowIndex < partitionTopIndex) {
				swap(fileNameList,partitionLowIndex,partitionTopIndex);
			}
		}

		swap(fileNameList,0,partitionTopIndex);
		
		
		return partitionTopIndex;

	}

	private static void swap(LinkedList<String> fileNameList, int partitionLowIndex, int partitionTopIndex) {
		String topIndexValue = fileNameList.get(partitionTopIndex);
		String lowIndexValue = fileNameList.get(partitionLowIndex);
		fileNameList.set(partitionLowIndex, topIndexValue);
		fileNameList.set(partitionTopIndex, lowIndexValue);

	}


	public static void main(String[] args) {
		LinkedList<String> fileNameList = new LinkedList<>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter folder path");
		String folderPath = sc.next();
		File f = new File(folderPath);
		String[] fileNmArray = f.list();
		for(String fileName:fileNmArray) {
			fileNameList.add(fileName);
		}
		System.out.println("Before Sorting ");
		System.out.println(fileNameList);
		quickSort(fileNameList, 0, fileNameList.size()-1,"Descending");
		System.out.println("After Descending Sorting ");
		System.out.println(fileNameList);
		quickSort(fileNameList, 0, fileNameList.size()-1,"Ascending");
		System.out.println("After Ascending Sorting ");
		System.out.println(fileNameList);
	}
}
