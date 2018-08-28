# emailquote

Provides email style quote. You receive some rich text format
email message and when hitting reply you see that either the quotes are non existent
or the lines are not wrapped at all and you have a quote in front of some kilometric
email lines.

This program helps you process raw text to quote and wrap it in order to make it
more readable.

The main class is com.ispnote.tools.quote.Process

The module uses generics - any java compiler from 1.5 up should do it. For your convenience
a maven pom file is provided. The project has no dependencies.

Execute without parameters for a short help:

================

Usage: quote [--cols newcols] [--noquote] [--help] [file1 file2 file3 ...]
 The program gets a number of text files or if missing the console input
   and processes the text to wrap it and add quotes at the beginning. Quote knows
   how to isolate paragraphs so that line wrapping while maintaining the proper quote level

   Options:
   --cols newcols - specify the new wrap column. Default is 50
   --noquote - provides the wrap service only without adding a new level of quote
   --help - prints this message
 and adds quotes if required

 The text is taken from the provided list of files. If this does not exist
 then the info is taken from the console stdin

==================
