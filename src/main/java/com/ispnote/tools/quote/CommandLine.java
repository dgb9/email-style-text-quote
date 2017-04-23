package com.ispnote.tools.quote;

import java.util.ArrayList;
import java.util.List;

/**
 * The class processes the command line parameters
 */
public class CommandLine {
    private static final int COLUMNS = 50;

    boolean noquote;
    boolean help;
    int cols;

    private List<String> files;

    public CommandLine() {
        files = new ArrayList<String>();
        noquote = false;
        help = false;

        cols = COLUMNS;
    }

    public void processCommandLine(String[] args) throws Exception {
        // it is assumed args is not null as it is passed by the system as the parameter collection
        int nr = args.length;

        for(int index = 0; index < nr; index ++) {
            String arg = args[index];

            if (Constants.PARAMETER_NOQUOTE.equals(arg)) {
                noquote = true;
            }
            else if (Constants.PARAMETER_HELP.equals(arg)) {
                help = true;

                break; // does not make sense to keep processing - help was triggered
            }
            else if (Constants.PARAMETER_COLS.equals(arg)) {
                // adjust the number of columns
                if (index + 1 < nr) {
                    index++;

                    String a = args[index];
                    if (isPositiveInteger(a)) {
                        this.cols = Integer.parseInt(a);
                    }
                    else {
                        throw new Exception("The number of columns must be a positive integer");
                    }
                }
                else {
                    throw new Exception("Please insert at least one parameter after --cols specifying the number of columns");
                }
            }
            else {
                files.add(arg);
            }

        }
    }

    private boolean isPositiveInteger(String a) {
        boolean res = false;

        try {
            int c = Integer.parseInt(a);

            if (c > 0) {
                res = true;
            }
        }
        catch (Exception e) {
            // no implementation
        }

        return res;
    }

    public boolean isNoquote() {
        return noquote;
    }

    public boolean isHelp() {
        return help;
    }

    public int getColumns() {
        return this.cols;
    }

    public List<String> getFiles() {
        return this.files;
    }

}
