package gui;

import backend.Car;
import backend.RaceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Log extends JFrame implements ActionListener {

    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();

    private final JTable crashTable = new JTable();
    private final JScrollPane scrollPane = new JScrollPane(crashTable);

    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Driver", "Laps left", "Crash Probability"}, 0);

    private final RaceManager raceManager;

    public Log(RaceManager raceManager){
        this.raceManager = raceManager;

        frame.setSize(500,600);
        frame.setTitle("Mini Racers");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        crashTable.setModel(model);
        crashTable.setRowHeight(30);
        panel.add(scrollPane);
        frame.add(panel);

        panel.add(scrollPane);
        frame.add(panel);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        reloadGui();
    }

    public void reloadGui() {
        int lapsleft = raceManager.getLapsLeft();

        for (Car car : raceManager.getCrashedCarsThisRound()){
            Vector<String> row = new Vector<>();
            row.addElement(car.getDriver().getName());
            row.addElement(lapsleft + " Laps");
            row.addElement(car.getCrashChance() + "%");

            model.addRow(row);
        }


    }

        @Override
    public void actionPerformed(ActionEvent e) {

    }
}
