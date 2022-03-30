/*
 * Created by JFormDesigner on Wed Mar 30 09:23:54 CEST 2022
 */

package gui;

import java.awt.*;
import javax.swing.*;

import backend.Car;
import backend.RaceManager;
import net.miginfocom.swing.*;

/**
 * @author unknown
 */
public class DriverInfoView extends JFrame {
    private final RaceManager raceManager;

    public DriverInfoView(RaceManager raceManager, int posX, int posY) {
        this.raceManager = raceManager;
        initComponents();

        this.setLocation(posX, posY);
    }

    public void loadTelemetry(Car car){
        driverName.setText(car.getDriver().getName());
        driverNumber.setText("#"+car.getDriver().getNumber());

        this.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        driverNumber = new JLabel();
        driverName = new JLabel();
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
                    "[fill]" +
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- driverNumber ----
                driverNumber.setText("text");
                contentPanel.add(driverNumber, "cell 2 0");

                //---- driverName ----
                driverName.setText("text");
                contentPanel.add(driverName, "cell 2 1");
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
                    null));

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
        pack();
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel driverNumber;
    private JLabel driverName;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
