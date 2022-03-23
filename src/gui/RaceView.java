package gui;

import backend.RaceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

public class RaceView extends JFrame implements ActionListener {

    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();
    private final JTable raceTable = new JTable();
    private final JScrollPane scrollPane = new JScrollPane(raceTable);

    private final JLabel labelLap = new JLabel();
    private final JLabel labelTrack = new JLabel();
    private final JLabel labelFastestLap = new JLabel();
    private final JLabel labelWeather = new JLabel();
    private final JLabel labelSafetycarDeployed = new JLabel("SAFETYCAR DEPLOYED");
    private final JButton buttonNextLap = new JButton("NEXT LAP");

    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Postion", "Start Position", "Number", "Name", "Last Lap", "Delta"}, 0);
    private final RaceManager raceManager;

    private final CrashView crashView;
    private final ControlView controlView;

    public RaceView(RaceManager raceManager) {
        this.raceManager = raceManager;

        frame.setSize(500, 600);
        frame.setTitle("Mini Racers");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        labelLap.setText("LAPS LEFT: " + raceManager.getRace().getLapsLeft());
        labelLap.setBounds(10, 430, 200, 50);
        frame.add(labelLap);

        labelTrack.setText("Track: " + raceManager.getRace().getTrack().getName());
        labelTrack.setBounds(10, 445, 200, 50);
        frame.add(labelTrack);

        labelFastestLap.setText("Lap Record: " + getFormattedLaptime(raceManager.getRace().getTrack().getLaptimeRecord()));
        labelFastestLap.setBounds(10, 460, 200, 50);
        frame.add(labelFastestLap);

        labelWeather.setText("Weather: " + raceManager.getRace().getTrack().getWeather().getWeatherType());
        labelWeather.setBounds(10, 475, 200, 50);
        frame.add(labelWeather);

        labelSafetycarDeployed.setVisible(false);
        labelSafetycarDeployed.setBounds(10, 505, 200, 50);
        frame.add(labelSafetycarDeployed);

        buttonNextLap.setBounds(370, 500, 100, 50);
        buttonNextLap.addActionListener(this);
        frame.add(buttonNextLap);

        raceTable.setModel(model);
        raceTable.setRowHeight(30);
        panel.add(scrollPane);
        frame.add(panel);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        crashView = new CrashView(raceManager,
                frame.getX() + frame.getWidth() + 1,
                frame.getY());

        controlView = new ControlView(raceManager,
                frame.getX(),
                frame.getY() + frame.getHeight() + 1);
        reloadGui();
    }

    public void reloadGui() {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            model.removeRow(0);
        }
        boolean isFirstRow = true;
        for (int i = 0; i < raceManager.getRace().getCars().size(); i++) {
            Vector<String> row = new Vector<>();
            row.addElement("P" + raceManager.getRace().getCars().get(i).getPosition());
            row.addElement("P" + raceManager.getRace().getCars().get(i).getStartPosition());
            row.addElement("#" + raceManager.getRace().getCars().get(i).getDriver().getNumber());
            row.addElement(raceManager.getRace().getCars().get(i).getDriver().getName());
            row.addElement(getFormattedLaptime(raceManager.getRace().getCars().get(i).getLaptime()));
            if (!isFirstRow) {
                row.addElement("+" + getFormattedDelta(raceManager.getRace().getDeltaList().get(i - 1)));
            } else {
                row.addElement("Interval");
                isFirstRow = false;
            }
            model.addRow(row);
        }
        raceTable.setModel(model);
        labelLap.setText("Laps Left: " + raceManager.getRace().getLapsLeft());
        if (raceManager.getRace().isSafetycarDeployed()) {
            labelSafetycarDeployed.setVisible(true);
        }
    }

    public String getFormattedLaptime(double laptime) {
        //TODO: Replace "DecimalFormat" with method, which doesn't round the given value
        DecimalFormat df = new DecimalFormat("#.000");
        int laptimeMin = (int) laptime / 60;
        double laptimeSec = (laptime) - (60 * laptimeMin);
        if (laptimeSec < 1) {
            return laptimeMin + ":00" + df.format(laptimeSec);
        } else if (laptimeSec < 10) {
            return laptimeMin + ":0" + df.format(laptimeSec);
        } else {
            return laptimeMin + ":" + df.format(laptimeSec);
        }
    }

    public String getFormattedDelta(double delta) {
        //TODO: Replace "DecimalFormat" with method, which doesn't round the given value
        DecimalFormat df = new DecimalFormat("#.0");
        if (delta < 1) {
            return "0" + df.format(delta);
        } else {
            return df.format(delta);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.buttonNextLap) {
            if (!raceManager.getRace().isSafetycarDeployed()) {
                labelSafetycarDeployed.setVisible(false);
            }
            raceManager.getRace().nextLap();
            reloadGui();
            crashView.reloadGui();
        }
    }
}
