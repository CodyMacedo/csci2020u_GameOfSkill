
package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {

    @FXML
    private Stage window;
    private Canvas canvas;
    private BorderPane layout;
    private int difficulty;
    private Play play;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("PokeMatch");
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New", imageFile("images/new.png"));
        newMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        fileMenu.getItems().add(newMenuItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Open...", imageFile("images/open.png")));
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(new MenuItem("Save", imageFile("images/save.png")));
        fileMenu.getItems().add(new MenuItem("Save As...", imageFile("images/save_as.png")));
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exitMenuItem = new MenuItem("Exit", imageFile("images/exit.png"));
        fileMenu.getItems().add(exitMenuItem);
        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        exitMenuItem.setOnAction( e -> System.exit(0) );


        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(new MenuItem("About...", imageFile("images/about.png")));
        helpMenu.getItems().add(new SeparatorMenuItem());
        MenuItem helpMenuItem = new MenuItem("Help...",imageFile("images/help.png"));
        helpMenu.getItems().add(helpMenuItem);
        helpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String fileName = "HelpInstructions.txt";
                String line = null;

                try {

                    FileReader fileReader =
                            new FileReader(fileName);


                    BufferedReader bufferedReader =
                            new BufferedReader(fileReader);

                    while((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }

                    // Always close files.
                    bufferedReader.close();
                }
                catch(FileNotFoundException ex) {
                    System.out.println(
                            "Unable to open file '" +
                                    fileName + "'");
                }
                catch(IOException ex) {
                    System.out.println(
                            "Error reading file '"
                                    + fileName + "'");
                    // Or we could just do this:
                    // ex.printStackTrace();
                }
            }

        });

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(helpMenu);

        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(140, 140, 140, 140));
        editArea.setVgap(10);
        editArea.setHgap(10);
        Button addButton = new Button("Easy");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                difficulty=1;
                play=new Play(difficulty);
                play.play(difficulty, primaryStage);
            }
        });


        Button newButton = new Button("Medium");
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                difficulty=2;
                play=new Play(difficulty);
                play.play(difficulty, primaryStage);
            }
        });

        Button hardButton = new Button("Hard");
        hardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                difficulty=3;
                play=new Play(difficulty);
                play.play(difficulty, primaryStage);
            }
        });

        Button Instructions = new Button("Guide");
        Instructions.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String fileName = "Instructions.txt";
                String line = null;

                try {

                    FileReader fileReader =
                            new FileReader(fileName);


                    BufferedReader bufferedReader =
                            new BufferedReader(fileReader);

                    while((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }

                    // Always close files.
                    bufferedReader.close();
                }
                catch(FileNotFoundException ex) {
                    System.out.println(
                            "Unable to open file '" +
                                    fileName + "'");
                }
                catch(IOException ex) {
                    System.out.println(
                            "Error reading file '"
                                    + fileName + "'");
                    // Or we could just do this:
                    // ex.printStackTrace();
                }
            }
        });

        editArea.add(addButton, 0, 5);
        editArea.add(newButton, 18,5);
        editArea.add(hardButton,37,5);
        editArea.add(Instructions,18, 14);
        layout = new BorderPane();
        layout.setBottom(editArea);
        layout.setTop(menuBar);
        layout.setCenter(canvas);


        StackPane root = new StackPane();
        //Scene scene = new Scene(root, 800, 600, Color.BLACK);
        //Scene show = new Scene(layout,800,600);

        canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());

        root.getChildren().addAll(canvas,layout);
        //root.getChildren().add(editArea);

        primaryStage.setScene(new Scene(root,800,600,Color.BLUE));
        primaryStage.show();
        //primaryStage.setScene(show);
        //primaryStage.show();
        draw(root);
        drawAnimation(root);
    }

    private void draw(StackPane group) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        System.out.println("width: " + canvas.getWidth());
        System.out.println("height: " + canvas.getHeight());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.strokeRect(305,50,200,75);

        gc.setStroke(Color.BLUE);
        gc.strokeOval(305, 50, 200, 75);
        gc.setStroke(Color.ORANGE);
        gc.strokeOval(325, 200, 155, 75);
        gc.setStroke(Color.GREEN);
        gc.strokeOval(100, 200, 155, 75);
        gc.setStroke(Color.RED);
        gc.strokeOval(565, 200, 155, 75);

        Font font = new Font("Arial", 24);
        gc.setFont(font);
        gc.setFill(Color.RED);
        gc.setStroke(Color.PURPLE);
        gc.strokeText("Poke", 335, 90);
        gc.fillText("Match", 385, 90);
        gc.setFill(Color.ORANGE);
        gc.fillText("Medium", 360, 245);
        gc.setFill(Color.GREEN);
        gc.fillText("Easy", 145, 245);
        gc.setFill(Color.RED);
        gc.fillText("Hard", 615, 245);
        gc.setFill(Color.BEIGE);
    }

    private Timeline timeline = null;

    private int frameOffsetX = 0;
    private int frameOffsetY = 0;
    private final int frameWidth = 208;
    private final int frameHeight = 128;
    private final int totalWidth = 768;
    private final int totalHeight = 2536;
    private final int numFrames = 6;
    private int frameNum = 0;

    private void drawAnimation(StackPane group) {
        Image sprites = new Image("pikachu.png");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(120), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gc.setFill(Color.BLACK);
                gc.fillRect(685, 500, frameWidth, frameHeight);

                // draw the current frame
                gc.drawImage(sprites, frameOffsetX, frameOffsetY, frameWidth, frameHeight, 685, 500, frameWidth, frameHeight);

                // proceed to the next frame of the animation
                frameNum = (frameNum + 1) % numFrames;

                // increment x offset and y offset
                frameOffsetX += frameWidth;
                if (frameOffsetX >= totalWidth) {
                    frameOffsetX = 0;
                    frameOffsetY += frameHeight;
                    if (frameOffsetY >= totalHeight) {
                        frameOffsetY = 0;
                    }
                }
            }
        }));
        timeline.playFromStart();
    }

    private ImageView imageFile(String filename) {
        return new ImageView(new Image("file:"+filename));
    }

    public static void main(String[] args) {
        launch(args);
    }
}