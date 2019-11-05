package service;

import exceptions.ExtensionNotSupportedException;
import parsers.Parser;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class ParsersMediator {

    private List<Parser> parsers = new LinkedList<>();
    public ParsersMediator(){

    }

    public void addParser(Parser parser){
        this.parsers.add(parser);
    }

    public boolean loadFile(String filename) throws FileNotFoundException, ExtensionNotSupportedException {
        int index = filename.lastIndexOf(".");
        if (index == -1)
            throw new ExtensionNotSupportedException("Extension not supported.");
        String extension = filename.substring(index);

        for (Parser it : parsers){
            if (it.getExtensions().contains(extension)){
                it.parseFile(filename);
                return true;
            }
        }
        throw new ExtensionNotSupportedException("Extension [" + extension + "] is not supported.");
    }
}
