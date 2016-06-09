package com.ispnote.tools.quote;

import java.util.ArrayList;

public class Paragraph
{
	private ArrayList<String> lines; 
	private int quotes; 
	
	public Paragraph(){}

	public ArrayList<String> getLines() {
		return lines;
	}

	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}

	public int getQuotes() {
		return quotes;
	}

	public void setQuotes(int quotes) {
		this.quotes = quotes;
	}
}
	