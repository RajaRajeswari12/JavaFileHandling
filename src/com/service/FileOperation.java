package com.service;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Paths;
import java.util.LinkedList;

/**** User Defined Exception Files****/
import com.exception.DesktopNotSupportedException;
import com.exception.DirectoryNotExistException;
import com.exception.CannotDeleteFolderException;

/***Sorting and Searching files ****/
import com.sorting.StringBinarySearch;
import com.sorting.StringQuickSort;

public class FileOperation {

	private String[] fileNameArray;
	private LinkedList<String> fileNameList;
	private String folderPath;
	private File folderName ;


	/**
	 * setFileNameList Method is used to store the list of files exist in the mentioned folder .
	 */
	public void setFileNameList(String[] fileNameArray) {
		fileNameList = new LinkedList<>();
		for(String fileName:fileNameArray) {
			fileNameList.add(fileName);
		}		
	}

	/**
	 * setFolderPath Method is used to check whether the given folder Path exists or not.
	 * */
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
	 * If created the file, then add the file Name to the linked list (fileNameList).
	 * If the file already exist throws FileAlreadyExistsException.
	 * @throws FileAlreadyExistsException 
	 * @throws IOException 
	 *
	 */
	public void createFile(String fileName) throws  FileAlreadyExistsException, IOException {
		File create= new File(folderPath+"\\"+fileName);

		if(create.createNewFile()) {
			fileNameList.add(create.getName());
			sortByAscending();  //Sort the list after adding New File
			System.out.println("File Created Successfully");
		}else {
			throw new FileAlreadyExistsException("File already exists.Kindly Enter another Name");
		}

	}

	/**
	 * deleteFile Method is used to delete the files exist in the mentioned folder.
	 * If deleted the file, then delete the file Name from the linked list (fileNameList).
	 * @throws IOException 
	 * @throws NoSuchFileException 
	 * @throws CannotDeleteFolderException 
	 *
	 */
	public void deleteFile(String fileNameToBeDeleted) throws  NoSuchFileException, IOException, CannotDeleteFolderException {
		File fileToDelete = new File(folderPath+"\\"+fileNameToBeDeleted);
		if(fileToDelete.exists()) {
			if(fileToDelete.isFile()) {
				if( Files.deleteIfExists(Paths.get(folderPath+"\\"+fileNameToBeDeleted))) {
					fileNameList.remove(fileNameToBeDeleted);
				}else {
					throw new NoSuchFileException("File does not Exist");
				}          
			}else {
				throw new CannotDeleteFolderException("Can't Delete Folder. ");
			}
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
	public LinkedList<String> getFileList(){		
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
	public void display() {
		int index = 1;

		if(fileNameArray.length == 0) {
			System.out.println("\n \t Folder is Empty !!!!!!!!!");
		}else {
			for(String fileName:fileNameArray) {
				System.out.println(index+".) "+fileName);
				index++;
			}
		}
	}

	/**
	 * searchFile Method is used to search the given file in the mentioned folder using
	 * binarySearch method.If file exist in that folder return true and if not then returns
	 * false.
	 */
	public boolean searchFile(String fileName) {
		fileNameArray = getFilesNameArray(fileNameList);
		boolean fileExist = StringBinarySearch.binarySearch(fileNameArray, fileName);

		if(fileExist) {
			System.out.println("File "+fileName +" exists.  File-path : "+folderPath+"\\"+fileName);
		}else {
			System.out.println("File "+fileName +" does not exist. \n");
		}

		return fileExist;
	}

	/**
	 * openFileInDesktop Method is used to open the given file in the Desktop.
	 * @throws IOException 
	 * @throws DesktopNotSupportedException 
	 */
	public void openFileInDesktop(String fileName) throws IOException, DesktopNotSupportedException  {
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
	/**
	 * sortByAscending Method is used to ascend the list of files available in the mentioned folder using
	 * quickSort method.If the folder is empty then "Folder is Empty". 
	 * Otherwise return the files list in A-Z Order.sortByDescending Method takes file List as a array.
	 * As array sorting takes time when compared to sorting a LinkedList.
	 */
	public void sortByAscending() {
		fileNameArray = getFilesNameArray(fileNameList);
		int listLength = fileNameArray.length-1;
		if(listLength != -1) {
			StringQuickSort.quickSort(fileNameArray,0, listLength, "Ascending");

		}
	}

	/**
	 * sortByDescending Method is used to list files available in the mentioned folder in descending order using
	 * quickSort method.If the folder is empty then "Folder is Empty". 
	 * Otherwise return the files list in Z-A Order.  sortByDescending Method takes file List as a array.
	 * As array sorting takes time when compared to sorting a LinkedList.
	 */
	public void sortByDescending() {
		fileNameArray = getFilesNameArray(fileNameList);
		int listLength = fileNameArray.length-1;
		if(listLength != -1) {
			StringQuickSort.quickSort(fileNameArray,0, listLength, "Descending");
		}

	}

	/**
	 * getFilesNameArray Method is used to store the updated linked list(fileNameList) 
	 * fileNames in the array(fileNameArray) before sorting and searching the files.
	 */
	public String[] getFilesNameArray(LinkedList<String> fileNameList) {
		int index = 0;
		String[]	fileNameArray = new String[fileNameList.size()];
		for(String fileName:fileNameList) {
			fileNameArray[index] = fileName; 
			index++;
		}
		return fileNameArray;
	}
}
