package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;

public class Play {
    private Canvas canvas;
    private BorderPane layout;
    private int[][] grid;//stores random numbers
    private int guess1, guess2;
    private int[] pressedButtons=new int[2];

    private boolean[][] pressed;//if the cards are already flipped over
    private boolean end=false;//if all cards are flipped over

    private int difficulty;
    public Play(int difficulty){
        this.difficulty=difficulty;
    }

    public void play(int difficulty, Stage primaryStage){
        Group root = new Group();
        layout=new BorderPane();
        Scene scene;
        primaryStage.setTitle("Pokemon matching game");

        Scanner scan=new Scanner(System.in);

        int[] nums;
        Button[][] buttons;
        GridPane area=new GridPane();
        boolean repeat;
        int x,y, xLength, yLength;
        if (difficulty==1){
            xLength=5;
            yLength=2;
            grid=new int[xLength][yLength];//5x2 board
            nums=new int[5];
            pressed=new boolean[xLength][yLength];
            buttons=new Button[xLength][yLength];
        } else if(difficulty==2){
            xLength=5;
            yLength=4;
            grid=new int[xLength][yLength];//5x4 board
            nums=new int[10];
            pressed=new boolean[xLength][yLength];
            buttons=new Button[xLength][yLength];
        } else {
            xLength=8;
            yLength=5;
            grid=new int[xLength][yLength];//8x5 board
            nums=new int[20];
            pressed=new boolean[xLength][yLength];
            buttons=new Button[xLength][yLength];
        }

        Random r=new Random();
        for (int i=0;i<nums.length;i++){
            do{
                repeat=false;
                nums[i]=r.nextInt(493)+1;//generates a random array of unique pokemon
                for(int j=0;j<i;j++){
                    if(nums[i]==nums[j]){
                        repeat=true;
                    }
                }
            }while(repeat);
            Image image=null;

            image=new Image("/csci2020project/Pokeball.PNG");

            for (int j=0;j<2;j++){
                do{
                    if(difficulty==1){
                        x=r.nextInt(5);
                        y=r.nextInt(2);
                    } else if (difficulty==2){
                        x=r.nextInt(5);
                        y=r.nextInt(4);
                    } else{
                        x=r.nextInt(8);
                        y=r.nextInt(5);
                    }
                }while(grid[x][y]!=0);
                grid[x][y]=nums[i];
                buttons[x][y]=new Button();
                buttons[x][y].setGraphic(new ImageView(image));
                final int x1=x;
                final int y1=y;
                buttons[x][y].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (!pressed[x1][y1]) {
                            //Group group=(Group) buttons[x1][y1].getParent();

                            if (grid[x1][y1] < 100) {
                                if (grid[x1][y1] < 10) {
                                    buttons[x1][y1].setGraphic(new ImageView(new Image("/csci2020project/00" + grid[x1][y1] + ".png")));
                                } else {
                                    buttons[x1][y1].setGraphic(new ImageView(new Image("/csci2020project/0" + grid[x1][y1] + ".png")));
                                }
                            } else {
                                buttons[x1][y1].setGraphic(new ImageView(new Image("/csci2020project/" + grid[x1][y1] + ".png")));
                            }
                            if (guess1 == 0) {
                                guess1 = grid[x1][y1];

                                pressedButtons[0] = x1;
                                pressedButtons[1] = y1;
                                System.out.println(pressedButtons[0] + " " + pressedButtons[1]);
                            } else {
                                guess2 = grid[x1][y1];

                                if (x1 == pressedButtons[0] && y1 == pressedButtons[1]) {
                                    System.out.println("Already pressed");
                                } else {
                                    //System.out.println(grid[x1][y1]);

                                    if (grid[x1][y1] < 100) {
                                        if (grid[x1][y1] < 10) {
                                            buttons[x1][y1].setGraphic(new ImageView(new Image("/csci2020project/00" + grid[x1][y1] + ".png")));
                                        } else {
                                            buttons[x1][y1].setGraphic(new ImageView(new Image("/csci2020project/0" + grid[x1][y1] + ".png")));
                                        }
                                    } else {
                                        buttons[x1][y1].setGraphic(new ImageView(new Image("/csci2020project/" + grid[x1][y1] + ".png")));
                                        //System.out.println(grid[x1][y1]);
                                    }
                                    area.getChildren().remove(buttons[x1][y1]);
                                    area.getChildren().add(buttons[x1][y1]);

                                    System.out.println(guess1 + " " + guess2);
                                    if (guess1 == guess2) {
                                        System.out.println("Match!");
                                        pressed[pressedButtons[0]][pressedButtons[1]]=true;
                                        pressed[x1][y1]=true;
                                        for(int i=0;i<pressed[x1].length;i++){
                                            for(int j=0;j<pressed[y1].length;j++){
                                                if(pressed[i][j]){
                                                    end=true;
                                                } else{
                                                    end=false;
                                                    break;
                                                }
                                            }
                                            if(!end){
                                                break;
                                            }
                                        }
                                        if(end){
                                            area.getChildren().remove(buttons[x1][y1]);

                                            area.getChildren().add(buttons[x1][y1]);

                                            Group root=new Group();
                                            layout=new BorderPane();
                                            ImageView image=new ImageView();
                                            image.setImage(new Image("/csci2020project/haunter.gif"));
                                            image.setFitHeight(500);
                                            image.setFitWidth(500);
                                            layout.setBottom(image);

                                            Canvas canvas=new Canvas();
                                            canvas.setHeight(100);
                                            canvas.setWidth(500);
                                            GraphicsContext gc=canvas.getGraphicsContext2D();
                                            gc.setFill(Color.BLUE);
                                            Font font=new Font("Arial",50);
                                            gc.setFont(font);
                                            gc.fillText("Congratulations!",65,75);
                                            layout.setTop(canvas);

                                            Scene win=new Scene(layout,500,600);
                                            primaryStage.setScene(win);
                                            primaryStage.show();
                                            /*
                                            /////////////////////////////////
                                            /////////////////////////////////
                                            Cody, add your code here
                                            /////////////////////////////////
                                            /////////////////////////////////
                                            */

                                        }


                                    } else {//no match

                                        try {
                                            Thread.sleep(500);                 //1000 milliseconds is one second.
                                        } catch(InterruptedException ex) {
                                            Thread.currentThread().interrupt();
                                        }

                                        buttons[x1][y1].setGraphic(new ImageView(new Image("/csci2020project/Pokeball.PNG")));
                                        buttons[pressedButtons[0]][pressedButtons[1]].setGraphic(new ImageView(new Image("/csci2020project/Pokeball.PNG")));
                                        area.getChildren().remove(buttons[x1][y1]);
                                        area.getChildren().add(buttons[x1][y1]);

                                        pressed[x1][y1]=false;
                                        pressed[pressedButtons[0]][pressedButtons[1]]=false;
                                    }
                                    guess1 = 0;
                                    guess2 = 0;
                                    pressedButtons[0] = 0;
                                    pressedButtons[1] = 0;
                                }
                            }
                        }
                    }
                });
                area.add(buttons[x][y],x,y);
            }
        }

        if(difficulty==1){
            scene= new Scene(layout,900, 350);
        } else if(difficulty==2){
            scene = new Scene(layout,900, 700);
        } else {
            scene = new Scene(layout,1425, 850);
        }
        layout.setCenter(area);

        //scene=new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
