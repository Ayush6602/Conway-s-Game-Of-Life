import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

class GUI implements ActionListener {

    JFrame frame;
    JPanel panel;
    JButton startGameBtn;
    JLabel sizeLbl;
    JRadioButton size5Btn;
    JRadioButton size10Btn;
    JRadioButton size15Btn;
    ButtonGroup sizeBtnGroup;

    public GUI() {

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        sizeLbl = new JLabel(" Select the number of rows and columns for the grid ");
        panel.add(sizeLbl);

        size5Btn = new JRadioButton("5x5");
        panel.add(size5Btn);
        size10Btn = new JRadioButton("10x10");
        panel.add(size10Btn);
        size15Btn = new JRadioButton("15x15");
        panel.add(size15Btn);

        sizeBtnGroup = new ButtonGroup();
        sizeBtnGroup.add(size5Btn);
        sizeBtnGroup.add(size10Btn);
        sizeBtnGroup.add(size15Btn);

        startGameBtn = new JButton("Start Game");
        startGameBtn.addActionListener(this);
        panel.add(startGameBtn);

        frame = new JFrame("Game Of Life Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (size5Btn.isSelected()) {
            new Game(5, 5);
            frame.dispose();
        }
        else if (size10Btn.isSelected()) {
            new Game(10, 10);
            frame.dispose();
        }
        else if (size15Btn.isSelected()) {
            new Game(15, 15);
            frame.dispose();
        }
        else {
            sizeLbl.setText(" Select the number of rows and columns for the grid ");
        }
    }
}