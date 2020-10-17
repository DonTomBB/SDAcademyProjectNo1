import javafx.application.Application;

//konfiguracja Maven z linku
// https://ducmanhphan.github.io/2019-10-17-Creating-JavaFX-project-with-Maven/

public class Main {

    public static void main(String[] args) {
//okno startowe w trybie Modelity.APPLICATION_MODAL
// uruchomienie JavaFX z nowej klasy z linku
// https://stackoverflow.com/questions/25873769/launch-javafx-application-from-another-class
        new Thread(() -> Application.launch(StartWindow.class))
        .start();
    }
}
