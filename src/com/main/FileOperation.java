package com.main;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

import com.exception.DesktopNotSupportedException;
import com.exception.DirectoryNotExistException;

import com.sorting.BinarySearchInLinkedList;
import com.sorting.QuickSort;

public class FileOperation {
	private String[] fileNameArray;
	private LinkedList<String> fileNameList;
	
	
	public void setFileNameList(String[] fileNameArray) {
		fileNameList = new LinkedList<>();
		for(String fileName:fileNameArray) {
			fileNameList.add(fileName);
		}
		
	}
	public void setFilesNameList(String[] fileNameArray) {
		this.fileNameArray = fileNameArray;
	}

	private String folderPath;
	private File folderName ;

	public void setFolderPath(String folderPath) throws NotDirectoryException, DirectoryNotExistException {
		folderName = new File(folderPath);
		if(folderName.exists()) {
			if(folderName.isDirectory()) {
				this.folderPath = folderPath;

			}else {
				throw new NotDirectoryException("Enter Correct Folder Path");
			}
		}else{
			throw new DirectoryNotExistException("Directory "+folderPath +" does not exist.");
		}

	}
	/**
	 * createFile Method is used to create the files in the mentioned folder.
	 * If the file already exist throws FileAlreadyExistsException.
	 * @throws FileAlreadyExistsException 
	 * @throws IOException 
	 * @throws DirectoryNotExistException 
	 *
	 */
	void createFile(String fileName) throws  FileAlreadyExistsException, IOException {
		File create= new File(folderPath+"\\"+fileName);

		if(create.createNewFile()) {
			fileNameList.add(create.getName());
//			updateFilesNameList(create.getName());

			System.out.println("File Created Successfully");
		}else {
			throw new FileAlreadyExistsException("File already exists.Kindly Enter another Name");
		}

	}

	/**
	 * deleteFile Method is used to delete the files exist in the mentioned folder.
	 * @throws DirectoryNotExistException 
	 * @throws IOException 
	 * @throws NoSuchFileException 
	 * @throws NotDirectoryException 
	 *
	 */
	void deleteFile(String fileNameToBeDeleted) throws  NoSuchFileException, IOException {

		if( Files.deleteIfExists(Paths.get(folderPath+"\\"+fileNameToBeDeleted))) {
			fileNameList.remove(fileNameToBeDeleted);
		}else {
			throw new NoSuchFileException("File does not Exist");
		}          

		System.out.println("Deletion successful.");
	}


	/**
	 * listFiles method is used to list the files available in the given folder.
	 * @throws NotDirectoryException 
	 * @throws DirectoryNotExistException 
	 *
	 */
	LinkedList<String> getFileList(){		

		fileNameArray = folderName.list();	
		setFileNameList(fileNameArray);
		LinkedList<String> fileNameLinkedList = fileNameList;
		return fileNameLinkedList;		
	}
	/**
	 * display method is used to display the files available in the given folder.
	 * 
	 *
	 */
	void display() {
		int index =01;
		if(fileNameArray.length == 0) {
			System.out.println("\n \t Folder is Empty !!!!!!!!!");
		}else {
			for(String element:fileNameList) {
				System.out.println(index+"--> "+element);
				index++;
			}
		}
	}

	/**
	 * searchFile Method is used to search the files in the mentioned folder using
	 * binarySearch method.
	 *
	 */
	boolean searchFile(String fileName) {

		boolean fileExist = BinarySearchInLinkedList.binarySearch(fileNameList, fileName);

		if(fileExist) {
			System.out.println("File "+fileName +" exists. File-path : "+folderPath+"\\"+fileName);
		}else {
			System.out.println("File "+fileName +" does not exist. \n");
		}

		return fileExist;
	}

	void openFileInDesktop(String fileName) throws IOException, DesktopNotSupportedException {
		File fileToOpen = new File(folderPath+"\\"+fileName);

		if(Desktop.isDesktopSupported()) {
			Desktop openApp  =  Desktop.getDesktop();
			if(fileToOpen.exists()) {
				openApp.open(fileToOpen);
			}else {
				throw new FileNotFoundException();
			}
		}else {
			throw new DesktopNotSupportedException("Desktop Not supported");
		}

	}

	public void sortByAscending() {
		int listLength = fileNameArray.length-1;
		if(listLength == -1) {
			display();
		}else
			QuickSort.quickSort(fileNameList,0, listLength, "Ascending");

	}

	public void sortByDescending() {
		int listLength = fileNameArray.length-1;
		if(listLength == -1) {
			display();
		}else
			QuickSort.quickSort(fileNameList,0, listLength, "Descending");

	}
	
	// --------------------------- Not Used  Methods------------------------------//

	private void deleteFileNameFromList(String fileNameToBeDeleted) {
	
		String[] updatedFilesNameList = new String[fileNameArray.length-1] ;
		boolean foundFile = false;
		int indexToBeDeleted = Arrays.binarySearch(fileNameArray, fileNameToBeDeleted);
		
		if(indexToBeDeleted == fileNameArray.length-1) {
			for(int index =0;index < fileNameArray.length-1 ;index++) {
				updatedFilesNameList[index]=fileNameArray[index];
			}
		}else {
			for(int index =0;index < fileNameArray.length-1 ;index++) {
				if(foundFile || (fileNameArray[index].equals(fileNameToBeDeleted)))  {
					updatedFilesNameList[index] = fileNameArray[index+1];
					foundFile = true;
				}else if(!foundFile){
					updatedFilesNameList[index]=fileNameArray[index];
				}

			}
		}
		
		setFilesNameList(updatedFilesNameList);

	}
	private void updateFilesNameList(String newFileName) {
		String[] updatedfilesNameList =  new String[fileNameArray.length+1];
		for(int index =0 ;index < fileNameArray.length;index++) {
			updatedfilesNameList[index] = fileNameArray[index];
		}
		updatedfilesNameList[fileNameArray.length] = newFileName;
		setFilesNameList(updatedfilesNameList);

	}


}
