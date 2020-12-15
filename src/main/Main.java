package main;


import io.FileManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import move.ReorderStrategy;
import move.Strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private File input;
    private File output;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/files"));
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Input files", "*.ysi"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            input = selectedFile;
            output = new File(input.getPath().split("\\.")[0] + ".yso");
        }

        try {


            Yard yard = FileManager.readFile(input);
            Strategy strategy = new ReorderStrategy();
            //strategy.reorderYard(yard);
            System.out.println(yard);
            FileManager.writeFile(yard, output);


        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Sorry, cannot find file : " + input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

}
