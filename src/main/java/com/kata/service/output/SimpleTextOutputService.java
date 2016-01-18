package com.kata.service.output;

import java.io.PrintWriter;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
/**
 * Service that allows for simple text output via System.out
 * 
 * @author jason
 *
 */
public class SimpleTextOutputService implements OutputService {

	private PrintWriter printWriter;
	
	@PostConstruct
	protected void init() {
		printWriter = new PrintWriter(System.out, true);
	}
	
	@Override
	public void sendMessage(String message) {
		getPrintWriter().println(message);
		
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public void setPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	
}
