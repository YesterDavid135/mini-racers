package gui;

import backend.RaceManager;
import backend.tyre.Tyre;
import backend.tyre.TyreType;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ControlView extends JPanel implements ActionListener {

    private final JLabel[] tyreLabels = new JLabel[4];
    private final RaceManager raceManager;

    /**
     * ActionListener entry point
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == refuelButton) {
            raceManager.refuelCar(fuelSlider.getValue());
        } else if (e.getSource() == reTyreButton) {
            raceManager.changeTyre((TyreType) tyreList.getSelectedItem());
        }

    }

    /**
     * Constructor for ControlView
     *
     * @param raceManager global raceManager
     * @param posX        X Location for the Window
     * @param posY        Y Location for the Window
     */
    public ControlView(RaceManager raceManager, int posX, int posY) {
        this.raceManager = raceManager;
        initComponents();

        JFrame frame = new JFrame();
        frame.setSize(400, 250);
        frame.setTitle("Pitstop");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocation(posX, posY);

        frame.add(this);

        frame.setVisible(true);

        tyreList.add(new PopupMenu("Soft"));
        tyreList.add(new PopupMenu("Hard"));
        tyreList.add(new PopupMenu("Wet"));

        updateData();
    }

    /**
     * Fires the Update-Methods
     */
    public void updateData() {
        updateTyre();
        updateFuel();
    }

    /**
     * Updates the Fuellabel and Fuelbar
     */
    private void updateFuel() {
        double fuel = raceManager.getFuelLeft();

        fuelLabel.setText(getFormattedDouble(fuel) + "L / 50L");
        fuelBar.setValue((int) fuel);
    }

    /**
     * Updates the Tyre Condition and Tyretype
     */
    private void updateTyre() {
        Tyre[] tyres = raceManager.getTyres();

        tyreType.setText("Current Compound: " + tyres[0].getTyreType().toString());

        for (int i = 0; i < 4; i++) {
            setTyreLabel(tyreLabels[i], tyres[i].getTyreCondition() * 100);
        }
    }

    /**
     * Set the Tyrecondition and color per Tyre
     *
     * @param tyre      Label to change
     * @param condition Condition the tyre
     */
    private void setTyreLabel(JLabel tyre, double condition) {
        tyre.setText(getFormattedDouble(condition) + "%");
        if (condition >= 50)
            tyre.setForeground(Color.GREEN);
        else if (condition >= 15)
            tyre.setForeground(Color.orange);
        else
            tyre.setForeground(Color.red);
    }

    /**
     * Rounds and format a double
     *
     * @param input double to format
     * @return formatted double
     */
    public String getFormattedDouble(double input) {
        //TODO: Replace "DecimalFormat" with method, which doesn't round the given value
        DecimalFormat df = new DecimalFormat("#.00");
        if (input < 1) {
            return "0" + df.format(input);
        } else {
            return df.format(input);
        }
    }

    /**
     * JFormDesigner <br>
     * Component initialization <br>
     * <strong>DO NOT MODIFY</strong>
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel = new JPanel();
        tyreType = new JLabel();
        fuelLabel = new JLabel();
        var tyreFL = new JLabel();
        this.tyreLabels[0] = tyreFL;
        var tyreFR = new JLabel();
        this.tyreLabels[1] = tyreFR;
        fuelBar = new JProgressBar();
        var tyreRL = new JLabel();
        this.tyreLabels[2] = tyreRL;
        var tyreRR = new JLabel();
        this.tyreLabels[3] = tyreRR;
        refuelButton = new JButton();
        refuelButton.addActionListener(this);
        reTyreButton = new JButton();
        reTyreButton.addActionListener(this);
        fuelSlider = new JSlider();
        tyreList = new JComboBox<>();

        //======== this ========
        setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[180,fill]" +
                        "[151,fill]" +
                        "[fill]",
                // rows
                "[]" +
                        "[26]" +
                        "[]" +
                        "[104]" +
                        "[]"));

        //---- tyreType ----
        tyreType.setText("Current: Medium");
        tyreType.setHorizontalAlignment(SwingConstants.CENTER);
        add(tyreType, "cell 1 0");

        //---- fuelLabel ----
        fuelLabel.setText("10L / 50L");
        fuelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(fuelLabel, "cell 0 1");

        //---- tyreFL ----
        tyreFL.setText("15%");
        tyreFL.setHorizontalAlignment(SwingConstants.CENTER);
        add(tyreFL, "tag ok,cell 1 1");

        //---- tyreFR ----
        tyreFR.setText("69%");
        tyreFR.setHorizontalAlignment(SwingConstants.CENTER);
        add(tyreFR, "tag ok,cell 1 1");

        //---- fuelBar ----
        fuelBar.setMaximum(50);
        fuelBar.setValue(10);
        fuelBar.setForeground(new Color(0, 204, 51));
        fuelBar.setStringPainted(true);
        add(fuelBar, "cell 0 2");

        //---- tyreRL ----
        tyreRL.setText("56%");
        tyreRL.setHorizontalAlignment(SwingConstants.CENTER);
        add(tyreRL, "tag ok,cell 1 2");

        //---- tyreRR ----
        tyreRR.setText("1%");
        tyreRR.setHorizontalAlignment(SwingConstants.CENTER);
        add(tyreRR, "tag ok,cell 1 2");

        //---- refuelButton ----
        refuelButton.setText("Refuel");
        add(refuelButton, "cell 0 3");

        //---- reTyreButton ----
        reTyreButton.setText("Change Tires");
        add(reTyreButton, "cell 1 3");

        //---- fuelSlider ----
        fuelSlider.setMajorTickSpacing(10);
        fuelSlider.setMaximum(50);
        fuelSlider.setMinorTickSpacing(5);
        fuelSlider.setPaintLabels(true);
        fuelSlider.setPaintTicks(true);
        fuelSlider.setBorder(null);
        fuelSlider.setToolTipText("Select how much liters you want to refuel");
        add(fuelSlider, "cell 0 4");

        //---- tyreList ----
        tyreList.setMaximumRowCount(3);
        tyreList.setModel(new DefaultComboBoxModel<>(TyreType.values()));
        add(tyreList, "cell 1 4");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel;
    private JLabel tyreType;
    private JLabel fuelLabel;
    private JProgressBar fuelBar;
    private JButton refuelButton;
    private JButton reTyreButton;
    private JSlider fuelSlider;
    private JComboBox<TyreType> tyreList;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
