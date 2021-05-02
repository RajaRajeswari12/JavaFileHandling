package com.main;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;


import com.exception.DirectoryNotExistException;
import com.service.FileOperation;
import com.exception.CannotDeleteFolderException;

import com.service.FileOperation;


public class Invoker {


	MenuOptions ovb = new MenuOptions();
	FileOperation fileOpr = new FileOperation();
	LinkedList<String> filesList;
	String linebreak = "\n *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* \n";
	Scanner getUserInput;

	/**
	 * Invoker() constructor to set the Scanner class to read input from the console.
	 *
	 */
	public Invoker() {
		getUserInput = new Scanner(System.in);
	}

	/**
	 * fileListSorting() method is used to list the options given below. And do the required actions.Calls Method recursively.
	 * 1.Sorted File List in Ascending Order
	 * 2.Sorted File List in Descending Order
	 * 3.Return to Main Menu
	 *
	 */
	public void fileListSorting() {

		System.out.println("Enter No.[1-3] to select options given below: ");
		ovb.loopDisplay(ovb.getSecondLevelListOption());
		int orderingOption  = getOption(2);

		switch(orderingOption) {
		case 1:{
			fileOpr.sortByAscending();
			fileOpr.display();
			System.out.println(linebreak);
			fileListSorting();
			break;
		}
		case 2:{
			fileOpr.sortByDescending();
			fileOpr.display();
			System.out.println(linebreak);
			fileListSorting();
			break;
		}
		case 3:{
			mainMenu();
			break;
		}
		default:{
			fileListSorting();
			break;
		}
		}

	}

	/**
	 * openFileOption() method is used to get input from the user whether to open the
	 * added/searched file in the desktop for (updating or cross checking) the file content.  
	 *
	 */
	public void openFileOption(String fileName) {
		String userInput = getUserInput.next();

		while(!userInput.matches("[yn]?")) {
			System.out.println("Enter y for yes or n for no : ");
			userInput = getUserInput.next();
		}
		if(userInput.equals("y")){
			try {
				fileOpr.openFileInDesktop(fileName);
			} catch (com.exception.DesktopNotSupportedException e) {
				System.err.println(e.getMessage() );
				fileOperationMenu();
			}catch(IOException e) {
				System.err.println(e.getMessage() );
				fileOperationMenu();
			}
		}
		fileOperationMenu();
	}

	/**
	 * getValidFileName() method is used to check whether the given fileName
	 * for adding/deleting/searching a file is a valid name or not.
	 */
	private String getValidFileName() {
		String fileNameToBeValidate = getUserInput.next();
		while(fileNameToBeValidate.matches(".*[:/\"?<>\\\\|*]+.*")) {
			System.out.println("Kindly avoid the following characters [: / \" ? < > \\ | * ] in the fileName. Give a valid FileName");
			fileNameToBeValidate = getUserInput.next();
		}

		return fileNameToBeValidate;
	}

	/**
	 * addFile() method is used to add the new file using FileOperation.createFile method.
	 * Once created successfully it ask the user to open the new file or not.
	 * Finally goes back to the fileOperationMenu().[Recursive call]
	 */
	public void addFile() {
		System.out.println("Enter a FileName to be created: ");
		String newFileName = getValidFileName() ;
		try {
			fileOpr.createFile( newFileName);			
			System.out.println("Do you want to open the newly created file? y/n");
			openFileOption(newFileName);

		} catch (AccessDeniedException    e) {
			System.err.println(e.getMessage());
			System.err.println("Goto main menu, change the destination path and try it ");
			fileOperationMenu();
		}catch(FileAlreadyExistsException  e) {
			System.err.println(e.getMessage());
			System.err.println(linebreak);
			addFile();
		}catch(IOException e) {
			System.err.println(e.getMessage()+" Goto the previous menu, change the destination path and try it again ");
			fileOperationMenu();
		}

	}

	/**
	 * fileOperationMenu() method is used to list the options given below. And do the required actions.Calls Method recursively.
	 * 1.Add File
	 * 2.Delete File
	 * 3.Search File
	 * 4.Return to Main Menu
	 *
	 */
	public void fileOperationMenu() {
		System.out.println("Enter No.[1-4] to select options given below: ");
		ovb.loopDisplay(ovb.getSecondLevelFileOprOption());
		int orderingOption = getOption(3);
		switch(orderingOption) {
		case 1:{
			addFile();
			break;
		}
		case 2:
		{
			deleteFile();
			break;
		}
		case 3:{
			searchFile();
			break;
		}
		case 4:
			mainMenu();
			break;
		default:
		{
			fileOperationMenu();
			break;
		}
		}
	}

	/**
	 * searchFile() method is used to search file using FileOperation.searchFile method.
	 * Once searched successfully it ask the user to open the searched file or not.
	 * Finally goes back to the fileOperationMenu().[Recursive call]
	 */
	private void searchFile() {
		System.out.println("Enter the File Name to search : ");
		String fileNm = getValidFileName();
		boolean searchResult =	fileOpr.searchFile(fileNm);
		if(searchResult) {
			System.out.println("Do you want to open the searched file? y/n");			
			openFileOption(fileNm);	
		}
		fileOperationMenu();
	}

	/**
	 * jumpToMainMenu() method is called from the delete method. It is used to give options to the user
	 * either goes to the MainMenu or fileOperationMenu or deleteFile option itself
	 */
	private void jumpToMainMenu(){
		System.out.println("If you want goto previous menu, Enter 1 or \n If you want to delete other file Enter 2");
		int option = getOption(4);
		switch(option) {
		case 1:{
			fileOperationMenu();
			break;
		}
		case 2:{
			deleteFile();
			break;
		}
		default:{
			jumpToMainMenu();
			break;
		}

		}
	}

	/**
	 * deleteFile() method is used to delete file using FileOperation.deleteFile method.
	 * If file name does not exists then it informs the user and ask whether to 
	 * go to the MainMenu or fileOperationMenu 
	 * or again go to deleteFile option itself to try some other file name.
	 * Finally goes back to the fileOperationMenu().[Recursive call]
	 */
	private void deleteFile() {
		System.out.println("Enter File Name to be deleted : ");
		String fileNameToBeDeleted = getValidFileName();
		try {
			fileOpr.deleteFile(fileNameToBeDeleted);
		} catch (NoSuchFileException e) {
			System.err.println(e.getMessage());
			jumpToMainMenu();
		} catch (CannotDeleteFolderException e) {
			System.err.println(e.getMessage());
			deleteFile();
		}catch(IOException e) {
			System.err.println(e.getMessage());
			jumpToMainMenu();
		}
		fileOperationMenu();
	}

	/**
	 * getOption() method is validate whether the given input is a valid numbers or not.
	 *If the user gives some other input except numbers then it recursively call the respective
	 *methods by using the input methodCalled and with the help of the method switchToMethods.
	 */
	private  int getOption(int methodCalled)  {
		int option = 0;

		String inputValue = getUserInput.next();
		if(inputValue.matches("[0-9]+")) {
			option =Integer.parseInt(inputValue);

		}else {
			switchToMethods(methodCalled);
		}

		return option;
	}

	/**
	 * switchToMethods() method is used to call the respective
	 *methods by using the input methodCalled.
	 */
	private void switchToMethods(int methodCalled) {
		switch(methodCalled) {
		case 1:
			mainMenu();
			break;

		case 2:
			fileListSorting();
			break;

		case 3:
			fileOperationMenu();
			break;
		case 4:
			jumpToMainMenu();
			break;
		}
	}

	/**
	 * mainMenu() method is used to list the options given below. And do the required actions.Calls Method recursively.
	 * 1.Sorted Files List
	 * 2.File Operation (Add,Delete&Search)
	 * 3.To Change the destination Folder path
	 * 3.Quit
	 *
	 */
	public void mainMenu() {
		System.out.println("Enter No.[1-4] to select the options");
		ovb.loopDisplay(ovb.getFirstLevelOption());
		int option = getOption(1);
		switch(option) {
		case 1:{				
			fileListSorting();
			break;	
		}
		case 2:{
			fileOperationMenu();
			break;
		}
		case 3 :{	
			getDestFolderPath();
			mainMenu();
			break;
		}
		case 4:{
			endFunction();
			break;	
		}
		default:{
			mainMenu();
			break;
		}
		}
	}

	/**
	 * endFunction() method is used to Quit the File Handling application.
	 *
	 */
	private void endFunction() {
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("Thanks for using this File Handling Application ");
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.exit(0);
	}

	/**
	 * getDestFolderPath() method is used to get the Folder Path and check whether 
	 * the given path is valid folder or not by calling the method FileOperation.setFolderPath() method
	 *
	 */
	private void getDestFolderPath() {
		System.out.println("Enter the Destination Folder Path : ");

		String folderPath = getUserInput.next();
		while(!folderPath.matches("([A-Za-z]):\\\\.*")) {
			System.out.println("Enter valid Folder Path");
			folderPath = getUserInput.next();
		}
		try {
			fileOpr.setFolderPath(folderPath);
		} catch (NotDirectoryException | DirectoryNotExistException e) {
			System.out.println(e.getMessage());
			getDestFolderPath();
		}
		filesList = fileOpr.getFileList();
}

	/**
	 * main() method is used to run the application.
	 *
	 */
	public static void main(String[] args) throws NotDirectoryException, DirectoryNotExistException {

		Invoker invoke = new Invoker();

		String welcomeNote ="\t Welcome to our Company: \"Lockers Pvt. Ltd.\" "
				+ "\n  \t \t"+"Aim of our company is to digitize our products. As a initiative "+
				"\n"+"We have launched our first prototype named  \"LockedMe.com\" ."+
				"\n \t"+"Developed by Raja Rajeswari Jayachandran.";
	
		
		System.out.println(welcomeNote+"\n");

		invoke.getDestFolderPath();		
		try {
			invoke.mainMenu();
		}catch(InputMismatchException e) {
			System.out.println("Input MisMatch Exception try again with correct input data" );

		}

	}
}
