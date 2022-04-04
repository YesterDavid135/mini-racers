/*
 * Created by JFormDesigner on Wed Mar 30 09:23:54 CEST 2022
 */

package gui;

import backend.Car;
import backend.LogEntry;
import backend.RaceManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Vector;


public class DriverInfoView extends JFrame {
    private final RaceManager raceManager;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Message", "Lap"}, 0);
    private Car currentCar;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel driverName;
    private JLabel driverNumber;
    private JTable logTable;
    private JLabel positionLabel;
    private JLabel raceTimeLabel;
    private JLabel startLabel;
    private JLabel skilLabel;
    private JLabel staminaLabel;
    private JLabel crashLabel;
    private JLabel tyreType;
    private JProgressBar tyreBar;
    private JProgressBar fuelBar;
    /**
     * Constructor for DriverInfoView
     *
     * @param raceManager global raceManager
     * @param posX        X Location for the Window
     * @param posY        Y Location for the Window
     */
    public DriverInfoView(RaceManager raceManager, int posX, int posY) {
        this.raceManager = raceManager;
        initComponents();

        this.setLocation(posX, posY);
    }

    /**
     * Loads new Car data
     *
     * @param car Car to load
     */
    public void LoadCar(Car car) {
        this.currentCar = car;
        loadTelemetry();
        this.setVisible(true);
    }

    /**
     * Loads Telemetry Data and show on GUI
     */
    public void loadTelemetry() {
        if (currentCar == null) return;
        driverName.setText(currentCar.getDriver().getName());
        driverNumber.setText("#" + currentCar.getDriver().getNumber());
        positionLabel.setText("<html> Position <br>" + currentCar.getPosition());
        raceTimeLabel.setText("<html> Total Racetime <br>" + getFormattedTime(currentCar.getRacetimeTotal()));
        tyreType.setText("<html> Tyre Compound <br>" + currentCar.getTyres()[0].getTyreType().toString());
        tyreBar.setValue((int) (currentCar.getCombinedTyreCondition() * 100));
        fuelBar.setValue((int) Math.round(currentCar.getFuel()));
        startLabel.setText("<html> Startposition <br>" + currentCar.getStartPosition());
        skilLabel.setText("<html> Driver Skill <br>" + (int) (currentCar.getDriver().getSkill() * 100));
        staminaLabel.setText("<html> Driver Stamina <br>" + (int) (currentCar.getDriver().getStamina() * 100));
        crashLabel.setText("<html> Crash Probability <br>" + (int) currentCar.getCrashChance() + "%");


        model.setRowCount(0);
        for (LogEntry entry : currentCar.getLog()) {
            Vector<String> row = new Vector<>();
            row.addElement(entry.getMessage());
            row.addElement(entry.getLap() + "");
            model.addRow(row);
        }

    }

    /**
     * Formats Total Racetime to a Human Readable format
     *
     * @param laptime unformatted Time
     * @return formatted Time
     */
    public String getFormattedTime(double laptime) {
        //TODO: Replace "DecimalFormat" with method, which doesn't round the given value
        DecimalFormat df = new DecimalFormat("#.000");
        int timeMin = (int) laptime / 60;
        double timeSec = (laptime) - (60 * timeMin);
        if (timeSec < 1) {
            return timeMin + ":00" + df.format(timeSec);
        } else if (timeSec < 10) {
            return timeMin + ":0" + df.format(timeSec);
        } else {
            return timeMin + ":" + df.format(timeSec);
        }
    }

    /**
     * JFormDesigner <br>
     * Component initialization <br>
     * <strong>DO NOT MODIFY</strong>
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        var label1 = new JLabel();
        driverName = new JLabel();
        driverNumber = new JLabel();
        var scrollPane1 = new JScrollPane();
        logTable = new JTable();
        positionLabel = new JLabel();
        raceTimeLabel = new JLabel();
        startLabel = new JLabel();
        skilLabel = new JLabel();
        staminaLabel = new JLabel();
        crashLabel = new JLabel();
        tyreType = new JLabel();
        var label2 = new JLabel();
        tyreBar = new JProgressBar();
        fuelBar = new JProgressBar();

        //======== this ========
        setTitle("Driver Info");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                        "insets dialog,hidemode 3",
                        // columns
                        "[25%,grow,fill]" +
                                "[25%,grow,fill]" +
                                "[50%,fill]",
                        // rows
                        "[]" +
                                "[10%]" +
                                "[:10%:50]" +
                                "[10%]" +
                                "[10%]" +
                                "[:10%:10%]" +
                                "[0]" +
                                "[23]" +
                                "[20]" +
                                "[30]"));

                //---- label1 ----
                label1.setText("Driver History");
                contentPanel.add(label1, "cell 2 0,alignx center,growx 0");

                //---- driverName ----
                driverName.setText("Name");
                contentPanel.add(driverName, "cell 0 1,alignx center,growx 0");

                //---- driverNumber ----
                driverNumber.setText("Number");
                contentPanel.add(driverNumber, "cell 1 1,alignx center,growx 0");

                //======== scrollPane1 ========
                {

                    //---- logTable ----
                    logTable.setModel(model);
                    scrollPane1.setViewportView(logTable);
                }
                contentPanel.add(scrollPane1, "cell 2 1 1 5,alignx right,growx 0");

                //---- positionLabel ----
                positionLabel.setText("Position");
                contentPanel.add(positionLabel, "cell 0 2,align left top,grow 0 0");

                //---- raceTimeLabel ----
                raceTimeLabel.setText("TotalRaceTime");
                contentPanel.add(raceTimeLabel, "cell 1 2,align left top,grow 0 0");

                //---- startLabel ----
                startLabel.setText("startPosition");
                contentPanel.add(startLabel, "cell 0 3,alignx left,growx 0");

                //---- skilLabel ----
                skilLabel.setText("skill");
                contentPanel.add(skilLabel, "cell 1 3,alignx left,growx 0");

                //---- staminaLabel ----
                staminaLabel.setText("stamina");
                contentPanel.add(staminaLabel, "cell 0 4,alignx left,growx 0");

                //---- crashLabel ----
                crashLabel.setText("crashLabel");
                contentPanel.add(crashLabel, "cell 1 4,alignx left,growx 0");

                //---- tyreType ----
                tyreType.setText("TyreType");
                contentPanel.add(tyreType, "cell 0 7 2 1,alignx center,growx 0");

                //---- label2 ----
                label2.setText("Fuel:");
                contentPanel.add(label2, "cell 2 7,alignx center,growx 0");

                //---- tyreBar ----
                tyreBar.setValue(10);
                tyreBar.setForeground(new Color(0, 204, 51));
                tyreBar.setStringPainted(true);
                contentPanel.add(tyreBar, "cell 0 9 2 1");

                //---- fuelBar ----
                fuelBar.setMaximum(50);
                fuelBar.setValue(10);
                fuelBar.setForeground(new Color(0, 204, 51));
                fuelBar.setStringPainted(true);
                contentPanel.add(fuelBar, "cell 2 9");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(580, 430);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
