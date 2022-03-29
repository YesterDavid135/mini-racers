/*
 * Created by JFormDesigner on Tue Mar 29 08:24:18 CEST 2022
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import backend.RaceManager;
import data.Difficulty;
import net.miginfocom.swing.*;

/**
 * @author unknown
 */
public class StartupView extends JFrame {

    public StartupView() {
        initComponents();
        this.setVisible(true);
    }

    private void ok(ActionEvent e) {
        if (!labelPlayerName.getText().isEmpty() && (Integer) spinnerPlayerNumber.getValue() < 100 && (Integer) spinnerPlayerNumber.getValue() > 0) {
            RaceManager raceManager = new RaceManager();
            new RaceView(raceManager);
            this.setVisible(false);
        }
    }

    private void cancel(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        var dialogPane = new JPanel();
        var contentPanel = new JPanel();
        var labelTitle = new JLabel();
        spinnerPlayerNumber = new JSpinner();
        labelPlayerName = new JTextField();
        comboBoxDifficulty = new JComboBox();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Start up");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "insets dialog,hidemode 3,alignx center,gapx 10",
                    // columns
                    "[51,fill]" +
                    "[106,fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- labelTitle ----
                labelTitle.setText("Mini Racers");
                contentPanel.add(labelTitle, "cell 1 0,alignx center,growx 0");
                contentPanel.add(spinnerPlayerNumber, "cell 0 3");
                contentPanel.add(labelPlayerName, "cell 1 3");

                //---- comboBoxDifficulty ----
                comboBoxDifficulty.setModel(new DefaultComboBoxModel<>(Difficulty.values()));
                contentPanel.add(comboBoxDifficulty, "cell 2 3");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]" +
                    "[button,fill]" +
                    "[button,fill]",
                    // rows
                    null));

                //---- okButton ----
                okButton.setText("Run");
                okButton.addActionListener(e -> ok(e));
                buttonBar.add(okButton, "cell 0 0");

                //---- cancelButton ----
                cancelButton.setText("Stop");
                cancelButton.addActionListener(e -> cancel(e));
                buttonBar.add(cancelButton, "cell 1 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JSpinner spinnerPlayerNumber;
    private JTextField labelPlayerName;
    private JComboBox comboBoxDifficulty;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
