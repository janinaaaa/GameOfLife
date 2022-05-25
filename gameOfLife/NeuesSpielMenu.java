package gameOfLife;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Janina Dörflinger (211923)
 * @author Marvin Hill (211926)
 * @version 25.05.2022
 * Dieses Menü wird aufgerufen, wenn man ein neues GameOfLife erstellen will
 */

public class NeuesSpielMenu extends JFrame {
    JLabel groesseText, farbeTotText, farbeLebendigText;
    JSlider groesseWaehlen;
    JButton farbeTotWaehlen, farbeLebendigWaehlen, bestaetigen;
    int size = 8;
    GameOfLifeGUI gui;
    Color farbeLebendig, farbeTot;


    public NeuesSpielMenu(GameOfLifeGUI parentGUI){
        gui = parentGUI;
        setLayout(new FlowLayout());
        groesseText = new JLabel("Größe auswählen: ");
        add(groesseText);
        groesseWaehlen = new JSlider(SwingConstants.HORIZONTAL, 7, 15, 8);

        groesseWaehlen.setMinorTickSpacing(1);
        groesseWaehlen.setMajorTickSpacing(1);
        groesseWaehlen.setPaintTicks(true);
        groesseWaehlen.setPaintLabels(true);
        add(groesseWaehlen);
        groesseWaehlen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                size = groesseWaehlen.getValue();
            }
        });



        farbeTotText = new JLabel("Farbe für tote Zellen wählen");
        add(farbeTotText);
        farbeTotWaehlen = new JButton();
        farbeTotWaehlen.setPreferredSize(new Dimension(75,25));
        farbeTot = Color.lightGray;
        farbeTotWaehlen.setBackground(farbeTot);
        farbeTotWaehlen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farbeTot = JColorChooser.showDialog(null, "Farbe Tot Wählen", farbeTotWaehlen.getBackground());
                farbeTotWaehlen.setBackground(farbeTot);
            }
        });
        add(farbeTotWaehlen);

        farbeLebendigText = new JLabel("Farbe für lebendige Zellen wählen");
        add(farbeLebendigText);
        farbeLebendigWaehlen = new JButton();
        farbeLebendigWaehlen.setPreferredSize(new Dimension(75,25));
        farbeLebendig = Color.green;
        farbeLebendigWaehlen.setBackground(farbeLebendig);
        farbeLebendigWaehlen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farbeLebendig = JColorChooser.showDialog(null,"Farbe Lebendig Wählen", farbeLebendigWaehlen.getBackground());
                farbeLebendigWaehlen.setBackground(farbeLebendig);
            }
        });
        add(farbeLebendigWaehlen);

        bestaetigen = new JButton("OK");
        add(bestaetigen);
        bestaetigen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameOfLife game = new GameOfLife(size, farbeLebendig, farbeTot);
                game.setSize(new Dimension(250,250));
                game.setVisible(true);
                setLocationRelativeTo(null);
                gui.add(game);
                dispose();
            }
        });

        setVisible(true);
        setSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(2);

    }

}
