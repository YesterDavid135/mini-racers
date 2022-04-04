package gui;

import backend.Car;
import backend.RaceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.Vector;

public class CrashView extends JFrame {

    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();

    private final JTable crashTable = new JTable();
    private final JScrollPane scrollPane = new JScrollPane(crashTable);

    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Driver", "Laps left", "Crash Probability", "Fuel"}, 0);

    private final RaceManager raceManager;

    /**
     * Constructor for CrashView
     *
     * @param raceManager global raceManager
     * @param posX        X Location for the Window
     * @param posY        Y Location for the Window
     */
    public CrashView(RaceManager raceManager, int posX, int posY) {
        this.raceManager = raceManager;

        frame.setSize(500, 475);
        frame.setTitle("Crashed Cars");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocation(posX, posY);

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

    /**
     * Reloads the Crashtable
     */
    public void reloadGui() {
        int lapsleft = raceManager.getLapsLeft();

        for (Car car : raceManager.getCrashedCarsThisRound()) {
            Vector<String> row = new Vector<>();
            row.addElement(car.getDriver().getName());
            row.addElement(lapsleft + " Laps");
            row.addElement(getFormattedCrashChance(car.getCrashChance()));
            row.addElement(getFormattedFuel(car.getFuel()));
            model.addRow(row);
        }
    }

    /**
     * Rounds and Formats the CrashChance
     *
     * @param crashChance unformatted CrashChance
     * @return formatted CrashChance
     */
    public String getFormattedCrashChance(double crashChance) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (crashChance < 1) {
            return "0" + df.format(crashChance) + "%";
        } else {
            return df.format(crashChance) + "%";
        }
    }

    /**
     * Rounds and Formats the Fuel
     *
     * @param fuel unformatted Fuel
     * @return formatted Fuel
     */
    public String getFormattedFuel(double fuel) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (fuel < 1) {
            return "0" + df.format(fuel) + "L";
        } else {
            return df.format(fuel) + "L";
        }
    }

}
