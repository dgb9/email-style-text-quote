package com.ispnote.tools.quote;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class Process
{

    public static void main(String[] args){
		try {
			CommandLine command = new CommandLine();
			command.processCommandLine(args);
			
			new Process().start(command);
		}
		catch(Exception e){
			System.out.println("ERROR: " + e.getMessage());

            new Process().printHelp();
		}
	}

	private void start(CommandLine command) throws IOException {
		if(command.isHelp()){
			printHelp();
		}
		else {
			boolean noquote = command.isNoquote();
			int cols = command.getColumns();

            if (command.getFiles() == null || command.getFiles().size() == 0) {
                processFile(noquote, cols, System.in);
            }
            else {
                for (String fl : command.getFiles()) {
                    File file = new File(fl);

                    if (file.exists()) {
                        InputStream stream = new FileInputStream(fl);

                        processFile(noquote, cols, stream);

                        stream.close();
                    }
                }
            }

		}
	}

	private void printHelp() {
		println("Usage: quote [--cols newcols] [--noquote] [--help] [file1 file2 file3 ...]");
		println(" The program gets a number of text files or if missing the console input");
		println("   and processes the text to wrap it and add quotes at the beginning. Quote knows");
		println("   how to isolate paragraphs so that line wrapping while maintaining the proper quote level");
		println("   ");
		println("   Options: ");
		println("   --cols newcols - specify the new wrap column. Default is 50");
		println("   --noquote - provides the wrap service only without adding a new level of quote");
		println("   --help - prints this message");
		println(" and adds quotes if required");
		println(" ");
		println(" The text is taken from the provided list of files. If this does not exist ");
		println(" then the info is taken from the console stdin");
	}

	private void processFile(boolean noQuote, int cols, InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		ArrayList<String> lines = new ArrayList<String>();
		String line = null;
		
		
		do {
			line = reader.readLine();
			
			if(line != null){
				lines.add(line);
			}
		}
		while(line != null);
		
		while(lines.size() > 0){
			Paragraph par = getParagraph(lines);

			printParapgraph(par, 50, noQuote);
		}
	}

	private void printParapgraph(Paragraph par, int columns, boolean noQuote) {
		ArrayList<String> wrapped = getWrapped(par.getLines(), columns);
		
		Iterator<String> iter = wrapped.iterator();
		while(iter.hasNext()){
			int prefixIncrease = (noQuote ? 0 : 1);
			int totalQuotes = par.getQuotes() + prefixIncrease;

			printPrefix(totalQuotes);

            String spacer = totalQuotes > 0 ? " " : "";
            String line = iter.next();

			println(spacer  + line);
		}
		
		println(" ");
	}

	private void printPrefix(int quotes) {
		for(int count = 0; count < quotes; count ++){
			print(">");
		}
	}

	private ArrayList<String> getWrapped(ArrayList<String> lines, int length) {
		ArrayList<String> res = new ArrayList<String>();
		
		StringBuilder buffer = new StringBuilder();
		
		Iterator<String> iter = lines.iterator();
		boolean first = true;
		while(iter.hasNext()){
			if(first){
				first = false;
			}
			else {
				buffer.append(" ");
			}
			
			String line = iter.next();
			buffer.append(line.toString());
		}
		
		String total = buffer.toString();
		String[] items = total.split("\\s");
		
		StringBuilder bld = new StringBuilder();
		
		for(int count = 0; count < items.length; count ++){
			bld.append(" "); 
			bld.append(items[count]); 
			
			if(bld.length() > length){
				res.add(bld.toString().trim());
				
				bld = new StringBuilder();
			}
		}
		
		if(bld.length() > 0){
			res.add(bld.toString().trim());
		}
		
		return res; 
	}

	private Paragraph getParagraph(ArrayList<String> lines) {
		Paragraph res = new Paragraph();
		ArrayList<String> lns = new ArrayList<String>();
		int offset = 0;
		
		// jump over empty lines
		boolean cont = true; 
		while(cont){
			if(lines.size() > 0){
				String line = lines.get(0);
				
				if(isEmpty(line)){
					lines.remove(0);
				}
				else {
					cont = false;
				}
			}
			else {
				cont = false;
			}
		}
		
		// peek the latest line
		if(lines.size() > 0){
			String line = lines.get(0);
            line = removeLeadingBlanks(line);

			offset = getOffset(line);
			
			cont = true; 
			while(cont){
				if(lines.size() > 0){
					String ln = lines.get(0);
                    ln = removeLeadingBlanks(ln);
					
					if(isFilled(ln) && getOffset(ln) == offset){
						lines.remove(0);
						
						lns.add(ln.substring(offset));
					}
					else {
						cont = false;
					}
				}
				else {
					cont = false;
				}
			}
		}
		
		res.setLines(lns);
		res.setQuotes(offset);
		
		return res;
	}

    public String removeLeadingBlanks(String ln) {
		String res = ln == null ? "" : ln.trim();
		String res2 = null;

		do {
			res2 = res.replaceAll("(^>+)(\\s+)(.*)", "$1$3");

            if (res2.equals(res)) {
                break;
            }

            res = res2;
		}
		while (true);

		return res;
    }

    private boolean isFilled(String ln) {
		return ! isEmpty(ln);
	}

	private int getOffset(String line) {
		int nr = 0;

		int length = line.length();
		
		for(int count = 0; count < length; count ++){
			String nextCharacter = line.substring(count, count + 1);
			if(Constants.QUOTE_STRING.equals(nextCharacter)){
				nr ++;
			}
			else {
				break;
			}
		}
		
		return nr; 
	}

    private boolean isEmpty(String line) {
		return line == null || line.trim().length() == 0;
	}

	private void println(Object val) {
		System.out.println(val == null ? "" : val.toString());
	}

	private void print(Object val){
		System.out.print(val == null ? "" : val.toString());
	}
}
