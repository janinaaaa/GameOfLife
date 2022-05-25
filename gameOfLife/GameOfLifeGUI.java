package gameOfLife;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultDesktopManager;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author Janina Dörflinger (211923)
 * @author Marvin Hill (211926)
 * @version 15.05.2022
 *
 * Ist der Desktop Manager und beeinhaltet alle GameOfLife-Objekte
 */

public class GameOfLifeGUI extends JFrame{

    JMenuBar menuBar;

    JMenu modus;
    JMenu fenster;

    JMenuItem laufen;
    JMenuItem setzen;
    JMenuItem malen;
    JMenuItem neuesFenster;

    JDesktopPane pane;

    public static States ausgewaehlterModus = States.SETZEN; //Modus ist per Default immer setzen

    public GameOfLifeGUI(){

        pane = new JDesktopPane();
        pane.setDesktopManager(new DefaultDesktopManager());
        setContentPane(pane);



        menuBar = new JMenuBar(); //neue Menübar
        this.setJMenuBar(menuBar);

        //Fügt Modus hinzu
        modus = new JMenu("Modus");
        menuBar.add(modus);
        //Fügt die verschiedenen Modi zu Modus hinzu
        laufen = new JMenuItem("Laufen");
        modus.add(laufen);
        setzen = new JMenuItem("Setzen");
        modus.add(setzen);
        malen = new JMenuItem("Malen");
        modus.add(malen);

        //Fügt Fenster hinzu
        fenster = new JMenu("Fenster");
        menuBar.add(fenster);
        //Fügt neues Fenster hinzu
        neuesFenster = new JMenuItem("Neues Fenster");
        fenster.add(neuesFenster);


        //ActionListener, der den ausgewählten Modus ändert
        laufen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ausgewaehlterModus = States.LAUFEN;
            }
        });

        setzen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ausgewaehlterModus = States.SETZEN;
            }
        });

        malen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ausgewaehlterModus = States.MALEN;
            }
        });


        neuesFenster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NeuesSpielMenu(GameOfLifeGUI.this);
            }
        });


        //Erstellt und fügt ein neues Game of Life hinzu
        GameOfLife testGame = new GameOfLife(8, Color.PINK, Color.BLUE);
        testGame.setSize(new Dimension(250,250));
        add(testGame);



        setSize(500,500); // Größe des Fensters setzen
        setVisible(true); //setzt Sichtbarkeit
        setDefaultCloseOperation(3); //setzt Exit-On-Close
        setLocationRelativeTo(null); //platziert Frame in der Mitte des Bildschirms
    }

    public static void main(String[] args) {
        new GameOfLifeGUI();
    }

    }
