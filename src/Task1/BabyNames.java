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
package Task1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Task 1   Baby name ranking
 */
public class BabyNames extends Application
{
    /**
     * BN
     */
    public BabyNames(){}
    private final TextField EnterYear = new TextField();
    private final TextField EnterGender = new TextField();
    private final TextField EnterName = new TextField();
    private final Text ResultText = new Text();

    @Override
    public void start(Stage stage1) {
        //set up stage
        GridPane gp1 = new GridPane();
        stage1.setTitle("Search Name Ranking Application");
        gp1.setBackground(new Background(new BackgroundFill(Color.rgb(176, 224, 230), null, null)));

        Scene scene1 = new Scene(gp1, 440, 400);
        stage1.setScene(scene1);
        stage1.show();
        scene1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });

        //labels
        Label LabelYear = new Label("Enter the Year:");
        Font font1 = LabelYear.getFont();
        Font boldFont1 = Font.font(font1.getFamily(), FontWeight.BOLD, font1.getSize());
        LabelYear.setFont(boldFont1);
        LabelYear.setOnMouseEntered(e1 -> {
            Tooltip tooltip = new Tooltip("Enter 2009 to 2018 inclusive only!");

            Point2D mouseLocation = LabelYear.localToScreen(e1.getX(), e1.getY());
            tooltip.show(LabelYear, mouseLocation.getX() + 10, mouseLocation.getY() + 10);

            LabelYear.setOnMouseExited(e2 -> tooltip.hide());
        });
        LabelYear.setPrefWidth(100);

        Label LabelGender = new Label("Enter the Gender:");
        Font font2 = LabelGender.getFont();
        Font boldFont2 = Font.font(font2.getFamily(), FontWeight.BOLD, font2.getSize());
        LabelGender.setFont(boldFont2);
        LabelGender.setOnMouseEntered(e1 -> {
            Tooltip tooltip = new Tooltip("Enter M or F");

            Point2D mouseLocation = LabelGender.localToScreen(e1.getX(), e1.getY());
            tooltip.show(LabelGender, mouseLocation.getX() + 10, mouseLocation.getY() + 10);

            LabelGender.setOnMouseExited(e2 -> tooltip.hide());
        });
        LabelGender.setPrefWidth(100);

        Label LabelName = new Label("Enter the Name:");
        Font font3 = LabelName.getFont();
        Font boldFont3 = Font.font(font3.getFamily(), FontWeight.BOLD, font3.getSize());
        LabelName.setFont(boldFont3);
        LabelName.setOnMouseEntered(e1 -> {
            Tooltip tooltip = new Tooltip
                    ("Exact match only");

            Point2D mouseLocation = LabelName.localToScreen(e1.getX(), e1.getY());
            tooltip.show(LabelName, mouseLocation.getX() + 10, mouseLocation.getY() + 10);

            LabelName.setOnMouseExited(e2 -> tooltip.hide());
        });
        LabelName.setPrefWidth(100);

        //exit
        Button exit = new Button("Exit");
        exit.setPrefWidth(100);
        gp1.add(exit, 1, 3);
        exit.setOnAction(e -> System.exit(0));

        gp1.setPadding(new Insets(25, 50, 50, 50));
        gp1.setHgap(25);
        gp1.setVgap(25);
        //populate
        gp1.add(LabelYear, 0, 0);
        gp1.add(EnterYear, 1, 0);
        gp1.add(LabelGender, 0, 1);
        EnterGender.setMaxWidth(35);
        gp1.add(EnterGender, 1, 1);
        gp1.add(LabelName, 0, 2);
        gp1.add(EnterName, 1, 2);
        //gp2
        GridPane gp2 = new GridPane();
        gp2.setBackground(new Background
                (new BackgroundFill(Color.rgb(135, 206, 235), null, null)));
        gp2.setPadding(new Insets(35, 25, 5, 55));
        gp2.setHgap(10);
        gp2.setVgap(10);
        //submit button
        Button submit = new Button("Submit Query");
        gp1.add(submit, 0, 3);
        EnterYear.requestFocus();
        scene1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submit.fire();
            }
        });
        //main logic
        submit.setOnAction(arg0 -> {
            try {
                if (EnterName.getText().isEmpty() || EnterYear.getText().isEmpty() || EnterGender.getText().isEmpty())
                    throw new NullPointerException();
                final ObservableList<String> ValidYears = FXCollections.observableArrayList("2009",
                        "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017",
                        "2018");
                String InputYear = EnterYear.getText().trim();
                if (!ValidYears.contains(InputYear)){
                    throw new IllegalArgumentException();
                }
                String FileStr = "babynamesranking" + EnterYear.getText() + ".txt";
                File FileEntries = new File(FileStr);
                int NumRecords;
                try {
                    NumRecords = ReadRecords(FileEntries);
                    //each text file string contains both boy and girl names, so that's twice the records
                    RankNum[] num = new RankNum[NumRecords * 2];
                    GetRecord(FileEntries, num);
                    //the name lists are all sentence-case names. Even if you input "billy", it reads as "Billy"
                    //must be exact match. "Alex/alex/ALEX/aLEX" returns "Alex". "Alexander" returns "Alexander"
                    int match = FindMatches(num, EnterName.getText().substring(0, 1).toUpperCase() +
                                    EnterName.getText().substring(1).toLowerCase(),
                            EnterGender.getText().toUpperCase());
                    //this testing line shows the "ranking" of a name in the entire name list index 0-1999
                    System.out.println(match);
                    if (match < 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Name not found");
                        alert.setContentText("Please try another name.");
                        alert.showAndWait();
                    }
                    else {
                        SearchResult(num[match], Integer.parseInt(EnterYear.getText()));
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An essential field is empty.");
                alert.setContentText("Check the year, gender, or name.");
                alert.showAndWait();
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid year. Must be 2009-2018 inclusive");
                alert.showAndWait();
            }
        });
    }

    /**
     * Find matches
     * @param num       2000
     * @param name      name
     * @param gender    M or F
     * @return          Name index 0-1999
     */
    public int FindMatches(RankNum[] num, String name, String gender) {
        int pos = -1;
        //must match gender too, but lower case m and f are ok
        for(int n = 0; n < num.length; n++) {
            if(num[n].getName().matches(name) && num[n].getGender() == gender.charAt(0)) {
                pos = n;
            }
        }
        return pos;
    }
    /**
     * Read records
     * @param src   Text file
     * @return      Lines of strings
     */
    public static int ReadRecords(File src) {
        int NumRecords = 0;
        try{
            BufferedReader BRead = new BufferedReader(new FileReader(src));
            while (BRead.readLine() != null) {
                NumRecords++;
            }
            BRead.close();

        }
        //play safe
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File not found");
            alert.setContentText("The specified file was not found.");
            alert.showAndWait();
        }
        return NumRecords;
    }
    /**
     * Get record
     * @param src   Text file
     * @param num   array
     * @throws IOException  Exception
     */
    public static void GetRecord(File src, RankNum[] num) throws IOException {
        String LocalStr;
        BufferedReader br = new BufferedReader(new FileReader(src));
        //read the files' exact format using BR
        //split each text file string line into 5
        //get rid of 3rd and 5th columns of text files (how many babies are named these names)
        //resultant string: boy's name, ranking, add gender M
        //resultant string: girl's name, ranking, add gender F
        for(int i = 0; i < num.length/2; i++) {
            LocalStr = br.readLine();
            if(LocalStr != null) {
                String[] split = LocalStr.split("\\s+");
                if(split.length == 5) {
                    num[i*2] = new RankNum(split[1], Integer.parseInt(split[0].replaceAll(",", "")),
                            Integer.parseInt(split[2].replaceAll(",", "")), 'M');
                    num[i*2+1] = new RankNum(split[3], Integer.parseInt(split[0].replaceAll(",", "")),
                            Integer.parseInt(split[4].replaceAll(",", "")), 'F');
                }
            }
        }
        br.close();
    }
    /**
     * Search result
     * @param src       the queried string
     * @param year      text file's year
     */
    public void SearchResult(RankNum src, int year) {
        //stage
        Stage SearchStage = new Stage();
        GridPane gp2 = new GridPane();
        gp2.setBackground(new Background
                (new BackgroundFill(Color.rgb(135, 206, 235), null, null)));
        gp2.setAlignment(Pos.CENTER);
        gp2.setHgap(6);
        gp2.setVgap(10);
        //result text
        if (src.getGender() == 'F') {
            ResultText.setText("Girl");
        }
        else if(src.getGender() == 'M'){
            ResultText.setText("Boy");
        }
        String ResultString = ResultText.getText() + " name " + src.getName() + " is ranked #" + src.getRank()
                + " in " + year + " year\n";
        Text ResultText2 = new Text(ResultString);
        ResultText2.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gp2.add(ResultText2, 0, 0);
        //new prompt
        Text Prompt = new Text("Do you want to Search for another name:\n");
        Prompt.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        gp2.add(Prompt, 0, 1);
        Button Yes = new Button("Yes");
        Yes.setOnAction(e -> {
            SearchStage.close();
            EnterYear.setText("");
            EnterGender.setText("");
            EnterName.setText("");
            EnterYear.requestFocus();
        });

        Button No = new Button("No");
        No.setOnAction(e -> Platform.exit());

        HBox ButtonBox = new HBox (Yes, No);
        ButtonBox.setSpacing(20);
        ButtonBox.setAlignment(Pos.CENTER);
        gp2.add(ButtonBox, 0, 2);
        //just more customizing below
        gp2.setPadding(new Insets(20, 0, 20, 0));
        gp2.setPrefSize(400, 250);
        gp2.setStyle("-fx-background-color: rgb(135, 206, 235);");

        Scene sc = new Scene(gp2);
        SearchStage.setTitle("Search Name Ranking Application");
        SearchStage.setScene(sc);
        SearchStage.show();

        Yes.setPrefWidth(90);
        No.setPrefWidth(90);
    }
    /**
     * Launch
     * @param args  Scene
     */
    public static void main(String[] args) {
        launch(args);
    }

}