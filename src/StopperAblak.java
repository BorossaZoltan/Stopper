import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class StopperAblak extends JFrame {
    JPanel panel, panelReszido;
    JButton btnStartStop, btnResetReszido;
    JLabel lblSzamol;
    Timer myTimer;
    LocalTime startTime;
    Duration stopDuration;
    boolean startStop = false;


    public StopperAblak() {
        init();

    }

    private void init() {
        this.setTitle("Stopper");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = (JPanel) (this.getContentPane());
        this.setLayout(null);

        btnStartStop = new JButton("Start");
        btnStartStop.setBounds(20, 45, 120, 30);
        btnStartStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (startStop == true) {
                    btnStartStop.setText("Start");
                    btnResetReszido.setText("Reset");
                    myTimer.cancel();

                    startStop = false;
                } else {
                    startTime = LocalTime.now();
                    myTimer = new Timer();
                    TimerTask task = new TimerTask() {

                        @Override
                        public void run() {

                            btnStartStop.setText("Stop");
                            btnResetReszido.setText("Részidő");

                            String[] tomb = lblSzamol.getText().split("[:.]");
                            int ezred = Integer.parseInt(tomb[2]);
                            int masod = Integer.parseInt(tomb[1]);
                            int perc = Integer.parseInt(tomb[0]);

                            ezred++;
                            if (ezred == 1000) {
                                ezred = 0;
                                masod++;
                                if (masod == 60) {
                                    masod = 0;
                                    perc++;
                                }
                            }

                            lblSzamol.setText(String.format("%02d:%02d.%03d", perc, masod, ezred));
                            startStop = true;
                        }
                    };
                    myTimer.schedule(task, 0, 1);
                }
            }
        });

        btnResetReszido = new JButton("Reset");
        btnResetReszido.setBounds(20, 105, 120, 30);
        btnResetReszido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnResetReszido.getText().equals("Részidő")) {

                    Duration elapsed = Duration.between(startTime, LocalTime.now());
                    elapsed = elapsed.plus(stopDuration);
                    int minutes = elapsed.toMinutesPart();
                    int seconds = elapsed.toSecondsPart();
                    int millis = elapsed.toMillisPart();
                    //panelReszido.add(String.format("%02d:%02d.%03d",minutes,seconds,millis));
                    //panelReszido.add(lblSzamol).setFont(new Font("Serif",Font.PLAIN,15));

                }
                if (btnResetReszido.getText().equals("Reset")) {

                    lblSzamol.setText("00:00.000");
                    panelReszido.removeAll();
                    panelReszido.revalidate();
                    panelReszido.repaint();
                }
            }
        });

        lblSzamol = new JLabel("00:00.000");
        lblSzamol.setBounds(170, 75, 400, 80);
        lblSzamol.setFont(new Font("Serif", Font.BOLD, 60));

        panelReszido = new JPanel();
        panelReszido.setBounds(170, 150, 100, 100);
        panelReszido.setBackground(Color.yellow);
        panelReszido.setLayout(new BoxLayout(panelReszido, BoxLayout.Y_AXIS));


        panel.add(btnStartStop);
        panel.add(btnResetReszido);
        panel.add(lblSzamol);
        panel.add(panelReszido);


        this.setVisible(true);
    }
}
