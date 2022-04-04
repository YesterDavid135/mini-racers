/*
 * Created by JFormDesigner on Wed Mar 30 09:23:54 CEST 2022
 */

package gui;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import backend.Car;
import backend.LogEntry;
import backend.RaceManager;
import net.miginfocom.swing.*;

/**
 * @author unknown
 */
public class DriverInfoView extends JFrame {
    private final RaceManager raceManager;
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Message", "Lap"}, 0);
    private Car currentCar;

    public DriverInfoView(RaceManager raceManager, int posX, int posY) {
        this.raceManager = raceManager;
        initComponents();



        this.setLocation(posX, posY);
    }
    public void LoadCar(Car car){
        this.currentCar = car;
        loadTelemetry();
        this.setVisible(true);
    }

    public void loadTelemetry(){
        if (currentCar == null) return;
        driverName.setText(currentCar.getDriver().getName());
        driverNumber.setText("#"+currentCar.getDriver().getNumber());
        positionLabel.setText("P " + currentCar.getPosition());
        raceTimeLabel.setText(getFormattedTime(currentCar.getRacetimeTotal()));
        tyreType.setText("Tyre Compound: " +currentCar.getTyres()[0].getTyreType().toString());
        tyreBar.setValue((int)(currentCar.getCombinedTyreCondition()*100));
        fuelBar.setValue((int) Math.round(currentCar.getFuel()));

        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(0);
        }
        for (LogEntry entry : currentCar.getLog()){
            Vector<String> row = new Vector<>();
            row.addElement(entry.getMessage());
            row.addElement(entry.getLap() + "");
            model.addRow(row);
        }
    }

    /**
     * Formats Total Racetime to a Human Readable format
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
        tyreType = new JLabel();
        var label2 = new JLabel();
        tyreBar = new JProgressBar();
        fuelBar = new JProgressBar();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

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
                    "[118]" +
                    "[]" +
                    "[20]" +
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
                contentPanel.add(scrollPane1, "cell 2 1 1 2");

                //---- positionLabel ----
                positionLabel.setText("Position");
                contentPanel.add(positionLabel, "cell 0 2,align center top,grow 0 0");

                //---- raceTimeLabel ----
                raceTimeLabel.setText("TotalRaceTime");
                contentPanel.add(raceTimeLabel, "cell 1 2,align center top,grow 0 0");

                //---- tyreType ----
                tyreType.setText("TyreType");
                contentPanel.add(tyreType, "cell 0 3 2 1,alignx center,growx 0");

                //---- label2 ----
                label2.setText("Fuel:");
                contentPanel.add(label2, "cell 2 3,alignx center,growx 0");

                //---- tyreBar ----
                tyreBar.setValue(10);
                tyreBar.setForeground(new Color(0, 204, 51));
                tyreBar.setStringPainted(true);
                contentPanel.add(tyreBar, "cell 0 5 2 1");

                //---- fuelBar ----
                fuelBar.setMaximum(50);
                fuelBar.setValue(10);
                fuelBar.setForeground(new Color(0, 204, 51));
                fuelBar.setStringPainted(true);
                contentPanel.add(fuelBar, "cell 2 5");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]" +
                    "[button,fill]",
                    // rows
                    "[]"));

                //---- okButton ----
                okButton.setText("OK");
                buttonBar.add(okButton, "cell 0 0");

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                buttonBar.add(cancelButton, "cell 1 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(580, 430);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel driverName;
    private JLabel driverNumber;
    private JTable logTable;
    private JLabel positionLabel;
    private JLabel raceTimeLabel;
    private JLabel tyreType;
    private JProgressBar tyreBar;
    private JProgressBar fuelBar;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
