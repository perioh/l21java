import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class MyCalculationApp extends JFrame {

    private JLabel resultLabel;
     private  JMenuItem toggleCalculationMenuItem = new JMenuItem("Почати\\зупинити обрахунок");
     private boolean calculationRunning;

    public MyCalculationApp() {
        setTitle("Обрахунок");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resultLabel = new JLabel("Результат буде тут");


        resultLabel.setHorizontalAlignment(JLabel.CENTER);


        add(resultLabel);

        JMenu menu = new JMenu("Опції");

        menu.add(toggleCalculationMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

        toggleCalculationMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (calculationRunning) {
                    toggleCalculationMenuItem.setText("Почати обрахунок");
                    stopCalculation();

                } else {
                    toggleCalculationMenuItem.setText("Зупинити обрахунок");
                    startCalculationThread();
                }
            }
        });
    }

    private void startCalculationThread() {
        calculationRunning = true;
        Thread calculationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int n = 0;
                double result = 0;
                while (calculationRunning) {
                    var factorial = factorial(n);
                    double term = Math.pow(-1, n)  / factorial;
                    System.out.println(result);
                    result += term;
                    n++;
                    final double finalResult = result;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            resultLabel.setText("Результат: " + String.format("%.5f", finalResult));
                        }
                    });
                }
            }
        });
        calculationThread.start();
    }

    private void stopCalculation() {
        calculationRunning = false;
    }

    private double factorial(int n) {
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyCalculationApp app = new MyCalculationApp();
                app.setVisible(true);
            }
        });
    }
}
