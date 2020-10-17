import com.sun.xml.bind.v2.TODO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//wywołuje okno startowe i okno z wykresem warunkowo w trybie Modelity.APPLICATION_MODAL
//z linku https://o7planning.org/en/11533/opening-a-new-window-in-javafx
//1	Modelity.NONE
// When you open a new window with this modelity,
// the new window will be independent from the parent window.
// You can interact with the parent window, or close it without affecting the new window.
//2	Modelity.WINDOW_MODAL
// When you open a new window with this modelity,
// it will lock the parent window. You can not interact with the parent window
// until this window is closed.
//3	Modelity.APPLICATION_MODAL
// When you open a new window with this modelity,
// it will lock any other windows of the application.
// You can not interact with any other windows  until this window is closed.
public class StartWindow extends Application {
    public static String absolutePatch;
    private Desktop desktop = Desktop.getDesktop();
    public static boolean m11=false;
    public static boolean m22=false;
    public static boolean m33=false;

    @Override
    public void start(final Stage primaryStage) {
 //*************Deklaracja aktywne labele konfiguracja wstepma******************************
        // http://www.java2s.com/Code/Java/JavaFX/ChangeLabeltextinButtonclickevent.htm
        final Label label4 = new Label("");
        final Label label5 = new Label("");
//*************deklaracje przyciskow********************************************************
        Button button = new Button();
//----------------------------------------------------------------------------------------
        MenuItem m1 = new MenuItem("Linear Regression");
        MenuItem m2 = new MenuItem("menu item 2 for future");
        MenuItem m3 = new MenuItem("menu item 3 for future");
        MenuButton button1 = new MenuButton("Choise an algotithm and calcullate");
        button1.getItems().addAll(m1, m2, m3);
//----------------------------------------------------------------------------------------
        Button button2 = new Button("Choice a CSV File and open him");
//----------------------------------------------------------------------------------------
        Button button3 = new Button("Exit");
//****************************************************************************************
//************************Button1 ACTION**************************************************
        //open a choise window pobrano z https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
        button1.setDisable(true);
        button1.setMaxWidth(300);
        //wywolanie algorytmow z menu
        m1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                label5.setText("Linear Prediction");
                m11=true;
                try {
                    Weca01.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                button.setDisable(false);
            }
        });
//*************************Button2 ACTION************************************************
        button2.setMaxWidth(300);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            // uruchamia systemowe okno wyboru pliku
            public void handle(ActionEvent actionEvent) {
                button.setDisable(true);
                button1.setDisable(true);
                absolutePatch=null;
                label4.setText("");
                final FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
                fileChooser.getExtensionFilters().add(extFilter);
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    try {
                        openFile(file);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            private void configureFileChooser(
                    final FileChooser fileChooser) {
            }
            private void openFile(File file) throws IOException {
                absolutePatch = file.getAbsolutePath();
                m11=false;
                m22=false;
                m33=false;
                //zmiana textu na label4
                if(absolutePatch!=null) {
                    label4.setText(absolutePatch);
                    desktop.open(file);
                    button1.setDisable(false);
                }
            }
        });
// END  a choice window
//*********************Button ACTION wywolanie okna wykresów*********************************
        button.setMaxWidth(300);
        button.setText("Wallchart");
        //przycisk jest aktywny
        button.setDisable(true);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //StackPane secondaryLayout = new StackPane();
                //Scene secondScene = new Scene(secondaryLayout, 1000, 800);
                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("WALLCHART");
                Scene secondScene = null;
                if (m11 = true) {
                    double XL = Weca01.xmin;
                    double XH = Weca01.xmax;
                    double YL = Weca01.ymin;
                    double YH = Weca01.ymax;

                    final NumberAxis xAxis = new NumberAxis();
                    final NumberAxis yAxis = new NumberAxis();
                    xAxis.setLabel("[X] Variable");
                    //wywolanie funkcji wykresu liniowego
                    final LineChart<Number, Number> lineChart =
                            new LineChart<Number, Number>(xAxis, yAxis);
                    lineChart.setTitle("LINEAR REGRESSION");
                    //definicja serii
                    XYChart.Series series = new XYChart.Series();
                    series.setName("The series according to CSV file as min. and max value of [X] variable");
                    //ustanowienie serii - dwa punkty dla wykresu liniowego
                    series.getData().add(new XYChart.Data(XL, YL));
                    series.getData().add(new XYChart.Data(XH, YH));
                    secondScene = new Scene(lineChart, 1000, 800);
                    lineChart.getData().add(series);
                }
//**********************************************************************************************************X
//                                      PRZSTRZEN DLA NASTEPNYCH WYBORÓW ALGORYTMOW m22, m33 itd
//***********************************************************************************************************
                newWindow.setScene(secondScene);
                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);
                // Specifies the owner Window (parent) for new window
                newWindow.initOwner(primaryStage);
                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX() + 100);
                newWindow.setY(primaryStage.getY() + 100);
                newWindow.show();
            }
        });
//********************Button3 ACTION EXIT******************************************************
        button3.setMaxWidth(300);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close();
                Platform.exit();
            }
        });
//*****************************Konfiguracja okna  głównego******************************************
        //konfiguracja wg
        // https://www.geeksforgeeks.org/javafx-how-to-set-padding-between-nodes-of-a-gridpane/
        GridPane grid = new GridPane();
        grid.addColumn(3);

        //dodawanie wyposazenia okna
        //labele (szyldziki)
        Label label1 = new Label("Path :");
        Label label2 = new Label("Algorithm type :");

        //przyciski rozmieszczenie
        grid.add(button, 0, 3);
        grid.add(button2, 0, 1);
        grid.add(button1, 0, 2);
        grid.add(button3, 0, 5);
        // szyldziki rozmieszczenie
        grid.add(label1, 1, 1);
        grid.add(label2, 1, 2);
        //grid.add(label3, 1, 3);
        grid.add(label4, 2, 1);
        grid.add(label5, 2, 2);

        // spacing between grid nodes
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(grid, 650, 600);
        primaryStage.setTitle("Machine Learning examples - SDAcademy author D.Tomczak");
        primaryStage.setScene(scene);
        primaryStage.show();
}
    public static void main(String[] args) {
         Application.launch(args);
    }
}
