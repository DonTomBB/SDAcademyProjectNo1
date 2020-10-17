
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Weca01{

    public static  String Path = StartWindow.absolutePatch;
    private static Object String;

    public static double xmin;
    public static double xmax;
    public static double ymin;
    public  static double ymax;
    //private static Component frame;

    public static void run() throws Exception {
//***********************ładowanie pliku CSV ***********************************************
        //Laduje plik CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(Path));
        //*pobrano z linku
        // http://www.java2s.com/example/java/machine-learning-ai/use-weka-linearregression-regression.html
        // https://www.programcreek.com/java-api-examples/?class=weka.classifiers.functions.LinearRegression&method=coefficients
        // * linear regression model *
        Instances dataset = loader.getDataSet();
        dataset.setClassIndex(dataset.numAttributes() - 1);
//***********************Wywolanie funkcji WECA predykcja liniwea**************************
        LinearRegression lr = new LinearRegression();
        lr.buildClassifier(dataset);
        //Do kontroli posrednich testow
//        System.out.println(lr);
//        System.out.println("====================================================");

        Evaluation lreval = new Evaluation(dataset);
        lreval.evaluateModel(lr, dataset);
        double coef[] = lr.coefficients();
        int size = dataset.size()-1;
//*****************wydzielenie i oblicznie parametrów do wykresu
        String  MIN[] = dataset.instance(0).toString().split(",");
        String  MAX[] = dataset.instance(size).toString().split(",");
//          parametry do wykresu
        xmin = Double.parseDouble(MIN[0].trim());
        xmax = Double.parseDouble(MAX[0].trim());
        ymin = coef[0]*xmin+coef[2];
        ymax = coef[0]*xmax+coef[2];
//***********************Uruchomienie pola informacyjngo z obliczeniami*******************************
        // wzór pobrany ze strony
        //https://www.javatpoint.com/java-jtextarea
        // https://perso.ensta-paris.fr/~diam/java/online/notes-java/GUI/components/40textarea/20textarea.html
        final JFrame frame= new JFrame("CALCULLATE OF LINEAR REGRESSION");
        JTextArea area=new JTextArea();
        JScrollPane scroller = new JScrollPane(area);
        area.setText("Result of calcullation for y=f(x) function:\n" +
                "Y = ["+coef[0]+"] * X  +  ["+coef[2]+"] "+
                lreval.toSummaryString());
        area.setBounds(10,30, 500,300);
        frame.add(area);
        frame.setSize(530,400);
        frame.setLayout(null);
        frame.setVisible(true);


        //custom title, custom icon
//        JOptionPane.showMessageDialog(frame, "Result of calcullation for y=f(x) function:\n" +
//                "Y = ["+coef[0]+"] * X  +  ["+coef[2]+"] "+
//                lreval.toSummaryString());

        }
    }


