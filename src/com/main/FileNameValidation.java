package com.main;

public class FileNameValidation {

	public boolean fileNameValidation(String fileName) {
		System.out.println(fileName.matches(".*[:/\"?<>\\\\|*]+.*"));
		if(fileName.matches("^[:]+$")) {
			System.out.println("Not a valid fileName");
		}
		return false;
	}
	
	public static void main(String[] args) {
		FileNameValidation n = new FileNameValidation();
		n.fileNameValidation("sads");
	}
	
}
