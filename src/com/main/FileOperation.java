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


import com.exception.DesktopNotSupportedException;
import com.exception.DirectoryNotExistException;
import com.sorting.StringBinarySearch;
import com.sorting.StringQuickSort;

public class FileOperation {
	private String[] filesNameList;

	public void setFilesNameList(String[] filesNameList) {
		this.filesNameList = filesNameList;
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
			updateFilesNameList(create.getName());

			System.out.println("File Created Successfully");
		}else {
			throw new FileAlreadyExistsException("File already exists.Kindly Enter another Name");
		}

	}
	private void updateFilesNameList(String newFileName) {
		String[] updatedfilesNameList =  new String[filesNameList.length+1];
		for(int index =0 ;index < filesNameList.length;index++) {
			updatedfilesNameList[index] = filesNameList[index];
		}
		updatedfilesNameList[filesNameList.length] = newFileName;
		setFilesNameList(updatedfilesNameList);

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
			deleteFileNameFromList(fileNameToBeDeleted);

		}else {
			throw new NoSuchFileException("File does not Exist");
		}          

		System.out.println("Deletion successful.");
	}

	private void deleteFileNameFromList(String fileNameToBeDeleted) {
	
		String[] updatedFilesNameList = new String[filesNameList.length-1] ;
		boolean foundFile = false;
		int indexToBeDeleted = Arrays.binarySearch(filesNameList, fileNameToBeDeleted);
		
		if(indexToBeDeleted == filesNameList.length-1) {
			for(int index =0;index < filesNameList.length-1 ;index++) {
				updatedFilesNameList[index]=filesNameList[index];
			}
		}else {
			for(int index =0;index < filesNameList.length-1 ;index++) {
				if(foundFile || (filesNameList[index].equals(fileNameToBeDeleted)))  {
					updatedFilesNameList[index] = filesNameList[index+1];
					foundFile = true;
				}else if(!foundFile){
					updatedFilesNameList[index]=filesNameList[index];
				}

			}
		}
		
		setFilesNameList(updatedFilesNameList);

	}


	/**
	 * listFiles method is used to list the files available in the given folder.
	 * @throws NotDirectoryException 
	 * @throws DirectoryNotExistException 
	 *
	 */
	String[] getFileList(){		

		filesNameList = folderName.list();	
		return filesNameList;		
	}
	/**
	 * display method is used to display the files available in the given folder.
	 * 
	 *
	 */
	void display() {
		int index =01;
		if(filesNameList.length == 0) {
			System.out.println("\n \t Folder is Empty !!!!!!!!!");
		}else {
			for(String element:filesNameList) {
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

		boolean fileExist = StringBinarySearch.binarySearch(filesNameList, fileName);

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
		int listLength = filesNameList.length-1;
		if(listLength == -1) {
			display();
		}else
			StringQuickSort.quickSort(filesNameList,0, listLength, "Ascending");

	}

	public void sortByDescending() {
		int listLength = filesNameList.length-1;
		if(listLength == -1) {
			display();
		}else
			StringQuickSort.quickSort(filesNameList,0, listLength, "Descending");

	}


}
