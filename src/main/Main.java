package main;


import io.FileManager;
import javafx.application.Platform;
import move.ReorderStrategy;
import move.Strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static File inputFile;
    private static File outputFile;

    private static final Logger logger = Logger.getLogger(Main.class.getName());



    public static void main(String[] args) {
        inputFile = new File(args[0]);
        outputFile = new File(args[1]);

        try {
            Yard yard = FileManager.readFile(inputFile);
            //System.out.println(yard);
            Strategy strategy = new ReorderStrategy();
            strategy.reorderYard(yard);
            //System.out.println(yard);
            FileManager.writeFile(yard, outputFile);


        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Sorry, cannot find file : " + inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

}
