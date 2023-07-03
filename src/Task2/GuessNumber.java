/**********************************************
 Workshop 6
 Course: JAC444 - Semester 4
 Last Name: Tse
 First Name: Chungon
 ID: 154928188
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 CHUNGON
 Date: 17 Mar 2023
 **********************************************/
package Task2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Task 2   Guess number
 */
public class GuessNumber extends Application implements EventHandler<ActionEvent> {
    /**
     * GuessNumber
     */
    public GuessNumber() {}
    static int RandomNum = (int)(Math.random() * 1000 + 1);
    private TextField NumText;
    private Button PlayAgain;
    private Label ChangeText;
    private GridPane gp;

    public void start(Stage ps) {
        gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(20);
        gp.setVgap(20);
        gp.setStyle("-fx-font-weight: 600; -fx-font-size: 15px;");
        gp.setBackground(new Background(new BackgroundFill(Color.MEDIUMAQUAMARINE, null, null)));

        Label label1 = new Label("I have a number between 1 and 1000. Can you guess my number?");
        label1.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        gp.add(label1, 0, 0);

        ChangeText = new Label("Please enter your first guess.");
        ChangeText.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        gp.add(ChangeText, 0, 1);

        NumText = new TextField();
        NumText.setMaxWidth(80);
        PlayAgain = new Button("Guess");

        HBox inputBox = new HBox();
        inputBox.setSpacing(10);
        inputBox.getChildren().addAll(NumText, PlayAgain);
        gp.add(inputBox, 0, 2);

        NumText.setOnAction(this);
        PlayAgain.setOnAction(this);
        System.out.println(RandomNum);

        Scene sc = new Scene(gp, 550, 300);
        sc.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });
        ps.setTitle("Task 2: Guess Number - Design myself");
        ps.setScene(sc);
        ps.show();
    }

    public void handle(ActionEvent event) {
        Button QuitButton = new Button("Quit");
        if (event.getSource() == NumText) {
            String input = NumText.getText().trim();
            if (input.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid number.");
                alert.showAndWait();
            }
            else {
                try {
                    int guess = ((int) Math.round(Double.parseDouble(input)));
                    if (guess > RandomNum) {
                        ChangeText.setText("Too High");
                        gp.setBackground(new Background(new BackgroundFill(Color.PINK, null, null)));
                        NumText.setText("");
                    }
                    else if (guess < RandomNum) {
                        ChangeText.setText("Too Low");
                        gp.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
                        NumText.setText("");
                    }
                    else {
                        ChangeText.setText("Correct!");
                        PlayAgain.setText("Try again");
                        gp.add(QuitButton, 0, 4);
                        QuitButton.setOnAction(e -> System.exit(0));
                        NumText.setDisable(true);
                    }
                }
                catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid number.");
                    alert.showAndWait();
                    NumText.setText("");
                }
            }

        }

        if (event.getSource() == PlayAgain) {
            gp.getChildren().remove(3);
            gp.setBackground(new Background(new BackgroundFill(Color.MEDIUMAQUAMARINE, null, null)));
            NumText.setDisable(false);
            RandomNum = (int)(Math.random() * 1000 + 1);
            System.out.println(RandomNum);
            NumText.setText("");
            NumText.requestFocus();
        }
    }

    /**
     * main
     * @param args  launch
     */
    public static void main(String[] args) {
        launch(args);
    }
}
