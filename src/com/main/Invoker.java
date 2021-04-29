package com.main;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.util.LinkedList;
import java.util.Scanner;


import com.exception.DirectoryNotExistException;

public class Invoker {


	MenuOptions ovb = new MenuOptions();
	FileOperation fileOpr = new FileOperation();
	LinkedList<String> filesList;
	String linebreak = "\n *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* \n";
	Scanner getUserInput;
	public Invoker() {
		getUserInput = new Scanner(System.in);
	}
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

	public void addFile() {
		System.out.println("Enter a FileName to be created: ");
		String newFileName = getUserInput.next();
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

	private void searchFile() {
		System.out.println("Enter the File Name to search : ");
		String fileNm = getUserInput.next();
		boolean searchResult =	fileOpr.searchFile(fileNm);
		if(searchResult) {
			System.out.println("Do you want to open the searched file? y/n");			
			openFileOption(fileNm);	
		}
		fileOperationMenu();
	}

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

	private void deleteFile() {
		System.out.println("Enter File Name to be deleted : ");
		String fileNameToBeDeleted = getUserInput.next();
		try {
			fileOpr.deleteFile(fileNameToBeDeleted);
		} catch (NoSuchFileException e) {
			System.err.println(e.getMessage());
			jumpToMainMenu();
		}catch(IOException e) {
			System.err.println(e.getMessage() + "Inside IO");
			jumpToMainMenu();
		}
		fileOperationMenu();

	}

	private  int getOption(int methodCalled)  {
		int option = 0;
		try{
			option = getUserInput.nextInt();
		}catch(NumberFormatException e) {
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
		return option;
	}

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


	private void endFunction() {
		System.out.println("Thanks for using this File Handling Application ");
	}

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

	public static void main(String[] args) throws NotDirectoryException, DirectoryNotExistException {

		Invoker invoke = new Invoker();

		String welcomeNote ="\t Welcome to our Company: \"Lockers Pvt. Ltd.\" "
				+ "\n  \t \t"+"Aim of our company is to digitize our products. As a initiative to our aim"+
				"\n"+"We have launched our first prototype named  \"LockedMe.com\" ."+
				"\n \t"+"Developed by Raja Rajeswari Jayachandran.";
		System.out.println(welcomeNote+"\n");


		invoke.getDestFolderPath();		

		invoke.mainMenu();

	}
}
