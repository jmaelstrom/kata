package com.kata.helper;

import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
/*
 * Decorator to aid mostly in testing but could easily be expanded to add logging, pre- / post- 
 * processing, etc.
 */
public class SystemInScanner {
	private Scanner scanner = new Scanner(System.in);
	
	public String next() {
		return scanner.next();
	}
	
	public String nextLine() {
		return scanner.nextLine();
	}

}
