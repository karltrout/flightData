package graph;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.stage.Stage;

/**
 * Created by karltrout on 7/16/17.
 *
 */
public class GraphAssent extends Application {

    private double[] altitudes = {
            8824.77784932565,
            9610.56423970016,
            10138.6775267419,
            10598.9249208349,
            11105.4296988152,
            12075.0226534055,
            13013.2209384585,
            14489.298061743,
            15801.8557206921,
            17275,
            17275,
            17298.9688393537,
            17414.6079951802,
            17450,
            /*14317.8091380953,
            14721.1052820293,
            15135.8285755904,
            15509.5807372558,
            5010.73933670646,
            7764.32408692981,
            12660.3909968645,
            15327.4669259026,
            9514.76170370031,
            10912.4324530216,
            13974.1686366136,
            14995.6198857114,
            15128.0385552202,
            15031.8214349404,
            9729.04899685987,
            10042.4884970206,
            10333.4424610394,
            10600.2824839316,*/
    };

    private Long[] times =
                   {
                           1498758928536L,
                           1498758933137L,
                           1498758937739L,
                           1498758942341L,
                           1498758946942L,
                           1498758956146L,
                           1498758969951L,
                           1498758983756L,
                           1498758997561L,
                           1498759034375L,
                           1498759061985L,
                           1498759075790L,
                           1498759089595L,
                           1498759112603L,
                         /*  1498759269061L,
                           1498759278264L,
                           1498759287468L,
                           1498759305874L,
                           1498759512951L,
                           1498759531357L,
                           1498759549764L,
                           1498759563569L,
                           1498759819575L,
                           1498759844034L,
                           1498759862441L,
                           1498759871644L,
                           1498759885449L,
                           1498759890051L,
                           1498760055712L,
                           1498760069517L,
                           1498760083322L,
                           1498760097127L,*/

                   };

    private int HEIGHT = 480;
    private int WIDTH = 600;

    private Number yRange = (altitudes[altitudes.length -1]+10000. - altitudes[0]) / (HEIGHT -100) ;
    private Number xRange = (times[times.length -1] - times[0]) / (WIDTH - 100);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Path path = new Path();

        Path lineToPath = getLines();
        Group root = new Group();

        root.getChildren().addAll(path, lineToPath);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        stage.setTitle("Ascent rate graph with cubic curve");
        stage.setScene(scene);
        stage.show();
    }

    private Path getLines() {

        Path path = new Path();
        System.out.println("X range: "+xRange+" Y range: "+yRange);
        for(int i=0; i < altitudes.length; i++ ){

            double x = (50d + ((times[i] - times[0] ) / xRange.doubleValue()));
            double y = ((HEIGHT-50d) - (altitudes[i] / yRange.doubleValue()));

            System.out.println("X: "+ x+" Y: "+y);

                if(i != 0 && (i+1) % 4 == 0 ) { // ascension points

                    double x0 = (50d + ((times[i - 3] - times[0]) / xRange.doubleValue()));
                    double y0 = ((HEIGHT - 50d) - (altitudes[i - 3] / yRange.doubleValue()));

                    double x1 = (50d + ((times[i - 2] - times[0]) / xRange.doubleValue()));
                    double y1 = ((HEIGHT - 50d) - (altitudes[i - 2] / yRange.doubleValue()));

                    double x2 = (50d + ((times[i - 1] - times[0]) / xRange.doubleValue()));
                    double y2 = ((HEIGHT - 50d) - (altitudes[i - 1] / yRange.doubleValue()));

                    double x3 = (50d + ((times[i] - times[0]) / xRange.doubleValue()));
                    double y3 = ((HEIGHT - 50d) - (altitudes[i] / yRange.doubleValue()));

                    CubicCurveTo cc = bezierCurve(x0, y0, x1, y1, x2, y2, x3, y3);
                    MoveTo moveTo = new MoveTo(x0, y0);
                    path.getElements().addAll(moveTo, cc);

            }
            else if (i != 0 && i % 4 == 0  ){

                    double x0 = (50d + ((times[i] - times[0]) / xRange.doubleValue()));
                    double y0 = ((HEIGHT - 50d) - (altitudes[i] / yRange.doubleValue()));

                    LineTo levelingLine = new LineTo(x0, y0);
                    path.getElements().add(levelingLine);

                }

        }

        return path;
    }

    private CubicCurveTo bezierCurve(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
        // measure chord lengths
        Point2D p0 = new Point2D(x0, y0);
        Point2D p1 = new Point2D(x1, y1);
        Point2D p2 = new Point2D(x2, y2);
        Point2D p3 = new Point2D(x3, y3);

        double c1 = p0.distance(p1);//dist(x0, y0, x1, y1);
        double c2 = p1.distance(p2);//dist(x1, y1, x2, y2);
        double c3 = p2.distance(p3); //dist(x3, y3, x2, y2);
        // make curve segment lengths proportional to chord lengths
        double t1=c1/(c1+c2+c3);
        double t2=(c1+c2)/(c1+c2+c3);
        double a=t1*(1-t1)*(1-t1)*3;
        double b=(1-t1)*t1*t1*3;
        double d=t2*(1-t2)*(1-t2)*3;
        double c=x1-(x0 * Math.pow(1-t1, 3.0))-(x3*Math.pow(t1, 3));
        double e=(1-t2)*t2*t2*3;
        double f=x2-(x0*Math.pow(1-t2, 3.0))-(x3*Math.pow(t2, 3));
        double g=y1-(y0*Math.pow(1-t1, 3.0))-(y3*Math.pow(t1, 3));
        double h=y2-(y0*Math.pow(1-t2, 3.0))-(y3*Math.pow(t2, 3));
        // find bezier control points
        double cpx2=(c-a/d*f)/(b-a*e/d);
        double cpx1=(c-(b*x2))/a;
        double cpy2=(g-a/d*h)/(b-a*e/d);
        double cpy1=(g-(b*y2))/a;
        // draw bezier curve using control points

        return new CubicCurveTo(cpx1, cpy1, cpx2, cpy2, x3, y3);

    }

}

