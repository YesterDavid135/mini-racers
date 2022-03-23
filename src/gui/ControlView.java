package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import backend.RaceManager;
import net.miginfocom.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ControlView  extends JPanel implements ActionListener {

    private final JFrame frame = new JFrame();

    public ControlView(RaceManager raceManager, int posX, int posY ) {
        initComponents();

        frame.setSize(400,250);
        frame.setTitle("Pitstop");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocation(posX, posY);

        frame.add(this);

        frame.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel = new JPanel();
        label5 = new JLabel();
        label1 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        slider1 = new JSlider();
        list1 = new JList();

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

        //---- label5 ----
        label5.setText("Current: Medium");
        label5.setHorizontalAlignment(SwingConstants.CENTER);
        add(label5, "cell 1 0");

        //---- label1 ----
        label1.setText("10L / 30L");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        add(label1, "cell 0 1");

        //---- label3 ----
        label3.setText("15%");
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        add(label3, "cell 1 1");

        //---- label4 ----
        label4.setText("69%");
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        add(label4, "cell 1 1");

        //---- label6 ----
        label6.setText("56%");
        label6.setHorizontalAlignment(SwingConstants.CENTER);
        add(label6, "cell 1 2");

        //---- label7 ----
        label7.setText("1%");
        label7.setHorizontalAlignment(SwingConstants.CENTER);
        add(label7, "cell 1 2");

        //---- button1 ----
        button1.setText("Refuel");
        add(button1, "cell 0 3");

        //---- button2 ----
        button2.setText("Change Tires");
        add(button2, "cell 1 3");
        add(slider1, "cell 0 4");
        add(list1, "cell 1 4");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel;
    private JLabel label5;
    private JLabel label1;
    private JLabel label3;
    private JLabel label4;
    private JLabel label6;
    private JLabel label7;
    private JButton button1;
    private JButton button2;
    private JSlider slider1;
    private JList list1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
