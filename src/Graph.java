
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph extends JFrame {

    private static final long serialVersionUID = 1L;

    public Graph(String input) {
        super("Trend Occurences");

        int max_length = 0;
        for (String line : input.split("\\n")) {
            if (max_length < line.length()) {
                max_length = line.length();
            }
        }
        System.out.println("Max Length: " + max_length);

        Integer[] A = new Integer[max_length];
        Integer[] T = new Integer[max_length];
        Integer[] C = new Integer[max_length];
        Integer[] G = new Integer[max_length];

        for (int i = 0; i < max_length; i++) {
            A[i] = 0;
            C[i] = 0;
            T[i] = 0;
            G[i] = 0;

        }

        for (String line : input.split("\\n")) {
            line = line.toUpperCase();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'A') {
                    A[i]++;
                } else if (line.charAt(i) == 'C') {
                    C[i]++;
                } else if (line.charAt(i) == 'T') {
                    T[i]++;
                } else if (line.charAt(i) == 'G') {
                    G[i]++;
                }
            }
        }
        System.out.println("A Occurences:");
        for (int i = 0; i < A.length; i++) {
            System.out.print(A[i]);
        }
        System.out.println("");
        System.out.println("C Occurences:");
        for (int i = 0; i < C.length; i++) {
            System.out.print(C[i]);
        }
        System.out.println("");
        System.out.println("T Occurences:");
        for (int i = 0; i < T.length; i++) {
            System.out.print(T[i]);
        }
        System.out.println("");
        System.out.println("G Occurences:");
        for (int i = 0; i < G.length; i++) {
            System.out.print(G[i]);
        }

        final XYSeries series1 = new XYSeries("A");
        for (int i = 0; i < A.length; i++) {
            series1.add(i, A[i].intValue());
        }

        final XYSeries series2 = new XYSeries("C");
        for (int i = 0; i < C.length; i++) {
            series2.add(i, C[i].intValue());
        }

        final XYSeries series3 = new XYSeries("T");
        for (int i = 0; i < T.length; i++) {
            series3.add(i, T[i].intValue());
        }

        final XYSeries series4 = new XYSeries("G");
        for (int i = 0; i < G.length; i++) {
            series4.add(i, G[i].intValue());
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        JFreeChart xylineChart = ChartFactory.createXYLineChart("Trend Occurences", "Position", "Count", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 237));
        XYPlot plot = xylineChart.getXYPlot();

        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.black);
        plot.setDomainGridlinePaint(Color.BLACK);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setTickUnit(new NumberTickUnit(1.0));

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setTickUnit(new NumberTickUnit(1.0));

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesPaint(3, Color.BLUE);

        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesStroke(1, new BasicStroke(1.0f));
        renderer.setSeriesStroke(2, new BasicStroke(1.0f));
        renderer.setSeriesStroke(2, new BasicStroke(1.0f));

        plot.setRenderer(renderer);
        JPanel pane = new JPanel();
        pane.setPreferredSize(new java.awt.Dimension(850, 280));
        pane.add(chartPanel);
        JButton save = new JButton("Save");
        // add the listener to the jbutton to handle the "pressed" event
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // display/center the jdialog when the button is pressed
                System.out.println("MOUSE CLICKED");
                int width = 850;
                /* Width of the image */
                int height = 237;
                /* Height of the image */
                File XYChart = new File("XYLineChart.jpeg");
                try {
                    ChartUtilities.saveChartAsJPEG(XYChart, xylineChart, width, height);
                } catch (IOException ex) {
                    Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        JButton ret = new JButton("Return");
        ret.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            setVisible(false);
            }
        });
        
        pane.add(save);
        pane.add(ret);
//        
        setContentPane(pane);

    }

    Graph() {

    }

    public static void main(String[] args, String input) {
        Graph chart = new Graph(input);
        chart.pack();
        chart.setVisible(true);
    }
}
