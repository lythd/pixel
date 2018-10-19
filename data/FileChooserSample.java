package data;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public final class FileChooserSample extends Application {
 
    private Desktop desktop = Desktop.getDesktop();
    public String mode;
 
    @Override
    public void start(final Stage stage) {
        stage.setTitle("Pixel");
 
        final FileChooser fileChooser = new FileChooser();
 
        final Button loadButton = new Button("Load File");
 
        loadButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    configureFileChooserLoad(fileChooser);
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        openFile(file);
                    }
                }
            });
 
        final Button saveButton = new Button("Save File");
 
        saveButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    configureFileChooserSave(fileChooser, stage);
                }
            });
 
        final Button browseButton = new Button("...");
        
        browseButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    final DirectoryChooser directoryChooser =
                        new DirectoryChooser();
                    final File selectedDirectory =
                            directoryChooser.showDialog(stage);
                    if (selectedDirectory != null) {
                        selectedDirectory.getAbsolutePath();
                    }
                }
            }
        );
        
        
        
        final GridPane inputGridPane = new GridPane();
 
        GridPane.setConstraints(loadButton, 0, 0);
        GridPane.setConstraints(saveButton, 1, 0);
        inputGridPane.setHgap(100);
        inputGridPane.setVgap(100);
        inputGridPane.getChildren().addAll(loadButton, saveButton);
 
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
 
    public static void main(String[] args) {
        Application.launch(args);
    }
 
    private void openFile(File file) {
        try {
            desktop.edit(file);
        } catch (IOException ex) {
            Logger.getLogger(
                FileChooserSample.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
    
    private static void configureFileChooserLoad(final FileChooser fileChooser) {                           
        fileChooser.setTitle("Load");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );               
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Files", "*.*"),
            new FileChooser.ExtensionFilter("MCFUNCTION File", "*.mcfunction"),
            new FileChooser.ExtensionFilter("ONECOMMAND File", "*.onecommand")
        );
    }
    
    private static void configureFileChooserSave(final FileChooser fileChooser, final Stage stage){
      fileChooser.setTitle("Save");
      fileChooser.setInitialDirectory(
  		  new File(System.getProperty("user.home"))
      );               
      fileChooser.getExtensionFilters().addAll(
  		  new FileChooser.ExtensionFilter("All Files", "*.*"),
  		  new FileChooser.ExtensionFilter("MCFUNCTION File", "*.mcfunction"),
  		  new FileChooser.ExtensionFilter("ONECOMMAND File", "*.onecommand")
      );
      File file = fileChooser.showSaveDialog(stage);
      if (file != null) {
          try {
  			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
  			bw.write("hello");
  			bw.close();
          } catch (IOException ex) {
              System.out.println(ex.getMessage());
          }
      }
  }
}