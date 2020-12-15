#!/bin/bash
java=/java/executable/name
folder=/folder/where/quote.jar/resides

${java} -cp ${folder}/quote.jar com.ispnote.tools.quote.Process $*
