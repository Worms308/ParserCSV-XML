package service;

import exceptions.ExtensionNotSupportedException;
import interpreters.Interpreter;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class FileInterpreter {

    private List<Interpreter> interpreters = new LinkedList<>();
    public FileInterpreter(){

    }

    public void addInterpreter(Interpreter interpreter){
        this.interpreters.add(interpreter);
    }

    public boolean loadFile(String filename) throws FileNotFoundException, ExtensionNotSupportedException {
        String extension = filename.substring(filename.lastIndexOf("."));

        for (Interpreter it : interpreters){
            if (it.getExtensions().contains(extension)){
                it.parseFile(filename);
                return true;
            }
        }
        throw new ExtensionNotSupportedException("Extension [" + extension + "] is not supported.");
    }
}
