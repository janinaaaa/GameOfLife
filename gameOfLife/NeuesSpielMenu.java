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
    JLabel zeilenText, spaltenText, farbeTotText, farbeLebendigText;
    JSlider zeilenanzahl, spaltenanzahl;
    JButton farbeTotWaehlen, farbeLebendigWaehlen, bestaetigen;
    int zeilen = 8;
    int spalten = 8;
    GameOfLifeGUI gui;
    Color farbeLebendig, farbeTot;

    /**
     * Konstruktor von NeuesSpielMenü
     * @param parentGUI ist das dazugehörige GUI-Objekt
     * Wenn man ein neues Spiel erstellen will, kann man dort Größe und Farbe hierfür auswählen
     */
    public NeuesSpielMenu(GameOfLifeGUI parentGUI){

        setResizable(false);
        gui = parentGUI;
        setLayout(new FlowLayout());
        zeilenText = new JLabel("Anzahl Zeilen auswählen");
        add(zeilenText);

        zeilenanzahl = new JSlider(SwingConstants.HORIZONTAL, 8, 15, 8);
        zeilenanzahl.setMinorTickSpacing(1);
        zeilenanzahl.setMajorTickSpacing(1);
        zeilenanzahl.setPaintTicks(true);
        zeilenanzahl.setPaintLabels(true);
        add(zeilenanzahl);
        zeilenanzahl.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                zeilen = zeilenanzahl.getValue();
            }
        });

        spaltenText = new JLabel("Anzahl Spalten auswählen: ");
        add(spaltenText);
        spaltenanzahl = new JSlider(SwingConstants.HORIZONTAL, 8, 15, 8);

        //Slider, um die Größe zu wählen
        spaltenanzahl.setMinorTickSpacing(1);
        spaltenanzahl.setMajorTickSpacing(1);
        spaltenanzahl.setPaintTicks(true);
        spaltenanzahl.setPaintLabels(true);
        add(spaltenanzahl);
        spaltenanzahl.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                spalten = spaltenanzahl.getValue();
            }
        });


        //Um die Farbe zu wählen
        farbeTotText = new JLabel("Farbe für tote Zellen wählen");
        add(farbeTotText);
        farbeTotWaehlen = new JButton();
        farbeTotWaehlen.setPreferredSize(new Dimension(75,25));
        farbeTot = Color.lightGray;
        farbeTotWaehlen.setBackground(farbeTot);
    farbeTotWaehlen.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              Color gewählteFarbe = JColorChooser.showDialog(null, "Farbe Tot Wählen", farbeTotWaehlen.getBackground());

              // Wenn das Fenster geschlossen ist, soll die alte Farbe behalten werden
              if(gewählteFarbe != null) {
                  farbeTot = gewählteFarbe;
                  farbeTotWaehlen.setBackground(farbeTot);
              }
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
                Color gewählteFarbe = JColorChooser.showDialog(null, "Farbe Lebendig Wählen", farbeLebendigWaehlen.getBackground());

                // Wenn das Fenster geschlossen ist soll die alte Farbe behalten werden
                if(gewählteFarbe != null) {
                    farbeLebendig = gewählteFarbe;
                    farbeLebendigWaehlen.setBackground(farbeLebendig);
                }
            }
        });
        add(farbeLebendigWaehlen);

        //Am Schluss wird bestätigt, es wird ein neues Spiel erstellt und das Fenster verschwindet
        bestaetigen = new JButton("OK");
        add(bestaetigen);
        bestaetigen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameOfLife game = new GameOfLife(zeilen, spalten, farbeLebendig, farbeTot);
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
