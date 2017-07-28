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
        /*   1125,
           1150,
           1175,
           1175,
           1337.6912668335158,
           1644.4309070120944,
           2514.9327271428137,
           2772.1944663430204,
           4044.2708969520404,
           7275.978141791256,
           10010.085409940186,
           10321.668107275927,
           10920.282820390874,
           12272.248821301802,
           13294.494911628402,
           13895.015903575078,
           21395.376132302106,
           27088.504258547495,
           32778.189599064644,
           33000,
           32984.435933806875,*/

        1150,
        1395.9807681485,
        2334.953145436,
        2948.5406790677,
        4414.0399281022,
        7408.9172849388,
        10185.7692935622,
        10732.6437190304,
        11786.6553344875,
        13959.3177679343,
        15929.3689008249,
        16150.2666311994,
        16869.8623475713,
        17570.843491199,
        18579.7846406429,
        18926.757865576,
        19221.7605943171,
        19695.1323736532,
        20090.1206592696,
        20445.7690619118,
        20773.4842822929,
        21393.3236458465,
        21880.6865312628,
        21978.159108346,
        23624.0578497125,
        25037.5079182688,
        28064.4205022161,
        29000,
        29000,
        29000,
        29107.1259946422,
        29337.4764456147,
        29567.8267684251,
        29678.7809360668,
        29918.9024595202,
        30283.515255262,
        30835.9186645689,
        31000,
        30925,
        30950,
        30975,
        31000,
        30991.2737820149,
    };

    private Long[] times =
                   {
                           /*1490889033000L,
                           1490889047000L,
                           1490889060000L,
                           1490889061000L,
                           1490889069003L,
                           1490889076007L,
                           1490889095388L,
                           1490889109195L,
                           1490889169024L,
                           1490889228854L,
                           1490889284081L,
                           1490889293285L,
                           1490889316297L,
                           1490889339308L,
                           1490889357717L,
                           1490889385331L,
                           1490889661467L,
                           1490889937602L,
                           1490890209136L,
                           1490890227545L,
                           1490890222943L,
                   */

                           1490916232999L,
                           1490916239039L,
                           1490916257189L,
                           1490916284783L,
                           1490916358368L,
                           1490916427354L,
                           1490916496339L,
                           1490916523934L,
                           1490916597518L,
                           1490916671103L,
                           1490916740089L,
                           1490916749287L,
                           1490916781480L,
                           1490916809075L,
                           1490916836669L,
                           1490916850466L,
                           1490916864263L,
                           1490916878061L,
                           1490916891858L,
                           1490916910254L,
                           1490916933249L,
                           1490916960843L,
                           1490916983839L,
                           1490916988438L,
                           1490917057424L,
                           1490917126409L,
                           1490917199994L,
                           1490917245985L,
                           1490917250584L,
                           1490917264381L,
                           1490917291975L,
                           1490917319569L,
                           1490917347164L,
                           1490917360961L,
                           1490917397753L,
                           1490917441560L,
                           1490917508001L,
                           1490917543994L,
                           1490917780455L,
                           1490917797563L,
                           1490917819431L,
                           1490917846641L,
                           1490917836901L,

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

