package gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * @author Janina Dörflinger (211923)
 * @author Marvin Hill (211926)
 * @version 15.05.2022
 *
 * GameOfLife repräsentiert ein Spielfeld
 */

public class GameOfLife extends JInternalFrame {
  Zelle[][] gameboard;
  Color farbeTot = Color.lightGray;
  Color farbeLebendig = Color.green;
  JMenuItem farbeToteZelleÄndern;
  JMenuItem farbeLebendigeZelleÄndern;
  static int fensterzahl;
  JPopupMenu popupMenu;

  JMenuBar menuBar = new JMenuBar();

  JMenu figuren, obszilierend, segler;


  JMenuItem blinker, uhr, pulsator, oktagon, gleiter, lwss, mwss, hwss;

  /**
   * Konstruktor von Game of Life
   * @param size Die Größe des Spielfeldes von Game of Life
   * @param farbeL Farbe der ebendigen Zellen
   * @param farbeT Farbe der toten Zellen
   */
  GameOfLife(int size, Color farbeL, Color farbeT) {
    farbeLebendig = farbeL;
    farbeTot = farbeT;
    farbeToteZelleÄndern = new JMenuItem("Farbe: Tote Zellen");
    farbeLebendigeZelleÄndern = new JMenuItem("Farbe: Lebendige Zellen");
    farbeToteZelleÄndern.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            farbeTot = JColorChooser.showDialog(null,"Farbe für tote Zelle auswählen",farbeTot);
            System.out.println(farbeTot);
            for (int i = 0; i < gameboard.length; i++) {
              for (int j = 0; j < gameboard[i].length; j++) {
                gameboard[i][j].updateColor();
              }
            }
          }
        });


    farbeLebendigeZelleÄndern.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              farbeLebendig = JColorChooser.showDialog(null,"Farbe für lebendige Zelle auswählen",farbeLebendig);
            for (int i = 0; i < gameboard.length; i++) {
              for (int j = 0; j < gameboard[i].length; j++) {
                gameboard[i][j].updateColor();
              }
            }
          }
        });

    // Spielfeldgröße setzen
    gameboard = new Zelle[size][size];
    // Spielfeld mit toten Zellen füllen
    for (int i = 0; i < gameboard.length; i++) {
      for (int j = 0; j < gameboard[i].length; j++) {
        gameboard[i][j] = new Zelle(this);
      }
    }

    // Fügt die Zellen zu dem Layout hinzu
    setLayout(new GridLayout(size, size));
    for (int i = 0; i < gameboard.length; i++) {
      for (int j = 0; j < gameboard[i].length; j++) {
        getContentPane().add(gameboard[i][j]);
      }
    }

    this.setJMenuBar(menuBar);

    //Fügt Figuren zum Menü hinzu
    figuren = new JMenu("Figuren");
    menuBar.add(figuren);
    obszilierend = new JMenu("Obszilierende Objekte");
    figuren.add(obszilierend);
    segler = new JMenu("Gleiter und Raumschiffe");
    figuren.add(segler);
    //Fügt obszilierende Objekte hinzu
    blinker = new JMenuItem("Blinker");
    blinker.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < gameboard.length; i++) {
          for (int j = 0; j < gameboard[i].length; j++) {
            gameboard[i][j].isAlive = false;
          }
        }
        gameboard[1][0].isAlive = true;
        gameboard[1][0].updateColor();
        gameboard[1][1].isAlive = true;
        gameboard[1][1].updateColor();
        gameboard[1][2].isAlive = true;
        gameboard[1][2].updateColor();


      }
    });
    obszilierend.add(blinker);
    uhr = new JMenuItem("Uhr");
    obszilierend.add(uhr);
    pulsator = new JMenuItem("Pulsator");
    obszilierend.add(pulsator);
    oktagon = new JMenuItem("Oktagon");
    obszilierend.add(oktagon);
    //Fügt Segler und Raumschiffe hinzu
    gleiter = new JMenuItem("Gleiter");
    segler.add(gleiter);
    lwss = new JMenuItem("Light-Weight Spaceship");
    segler.add(lwss);
    mwss = new JMenuItem("Middle-Weight Spaceship");
    segler.add(mwss);
    hwss = new JMenuItem("Heavy-Weight Spaceship");
    segler.add(hwss);

    popupMenu = new JPopupMenu();
    popupMenu.add(farbeToteZelleÄndern);
    popupMenu.add(farbeLebendigeZelleÄndern);
    setComponentPopupMenu(popupMenu);

   new Thread(new Runnable() {
     @Override
     public void run() {
       while (true) {
         if (GameOfLifeGUI.ausgewaehlterModus.equals("laufen")) {

           for (int i = 0; i < gameboard.length; i++) {
             for (int j = 0; j < gameboard[i].length; j++) {
               int x = i;
               int y = j;
               int lebendeNachbarn = 0;

               //if(x-1<0) x=gameboard.length - 1;
               //if(y-1<0) y=gameboard[i].length - 1;

               //Links
               Zelle zelle = gameboard[x][y-1<0 ? gameboard[i].length -1 : y-1];
               //Rechts
               Zelle zelle2 = gameboard[x][(y + 1) % gameboard[i].length];
               //Oben
               Zelle zelle3 = gameboard[x-1<0 ? gameboard.length-1 : x-1][y];
               //Unten
               Zelle zelle4 = gameboard[(x + 1) % gameboard.length][y];
               //Rechtsoben
               Zelle zelle5 = gameboard[x-1<0 ? gameboard.length-1 : x-1][(y + 1) % gameboard[i].length];
               //Rechtsunten
               Zelle zelle6 = gameboard[(x + 1) % gameboard.length][(y + 1) % gameboard[i].length];
               //Linksoben
               Zelle zelle7 = gameboard[x-1<0 ? gameboard.length-1 : x-1][y-1<0 ? gameboard[i].length-1 : y-1];
               //Linksunten
               Zelle zelle8 = gameboard[(x + 1) % gameboard.length][y-1<0 ? gameboard[i].length-1 : y-1];

               if (zelle.isAlive) lebendeNachbarn += 1;
               if (zelle2.isAlive) lebendeNachbarn += 1;
               if (zelle3.isAlive) lebendeNachbarn += 1;
               if (zelle4.isAlive) lebendeNachbarn += 1;
               if (zelle5.isAlive) lebendeNachbarn += 1;
               if (zelle6.isAlive) lebendeNachbarn += 1;
               if (zelle7.isAlive) lebendeNachbarn += 1;
               if (zelle8.isAlive) lebendeNachbarn += 1;

               if (lebendeNachbarn == 3 && !gameboard[i][j].isAlive) gameboard[i][j].isAlive = true;
               else if ((lebendeNachbarn == 2 || lebendeNachbarn == 3) && gameboard[i][j].isAlive)
                 gameboard[i][j].isAlive = true;
               else gameboard[i][j].isAlive = false;

               SwingUtilities.invokeLater( () -> {GameOfLife.this.updateUI();});
             }
           }
         }
       }
     }
   }).start();

    setPreferredSize(new Dimension(250, 250));
    setVisible(true);
    setClosable(true);
    setResizable(true);
    setIconifiable(true);
    setMaximizable(true);
    setTitle("Game of Life" + ++fensterzahl);
  }

  /**
   *
   * @param e ist das MouseEvent
   */
  public void showPopUpMenu(MouseEvent e){
   popupMenu.show(e.getComponent(),e.getX(),e.getY());
  }
}
