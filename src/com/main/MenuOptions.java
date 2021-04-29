package com.main;

public class MenuOptions {

	public String[] getFirstLevelOption() {
		String[] firstLevelOption = {"Sorted Files List" ,"File Operation (Add,Delete&Search)","To Change the destination Folder path","Quit"};
		return firstLevelOption;
	}	

	public String[] getSecondLevelListOption() {
		String[]	secondLevelListOption = {"Sorted File List in Ascending Order","Sorted File List in Descending Order","Return to Main Menu"};
		return secondLevelListOption;

	}

	public String[] getSecondLevelFileOprOption() {
		String[] secondLevelFileOprOption = { "Add File","Delete File","Search File","Return to Main Menu"};
		return secondLevelFileOprOption;
	}

	public void loopDisplay(String[] optionMenu) {
		int index = 1;
		for(String option:optionMenu) {
			System.out.println(index+".) "+option);
			index++;
		}
	}
	
	
	
	
}
