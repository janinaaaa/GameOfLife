package gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

import static java.lang.Thread.sleep;

/**
 * @author Janina Dörflinger (211923)
 * @author Marvin Hill (211926)
 * @version 15.05.2022
 *     <p>GameOfLife repräsentiert ein Spielfeld
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

  JMenu figuren, obszilierend, segler, geschwindigkeit;
  JMenuItem blinker, uhr, oktagon, gleiter, lwss, mwss, langsam, mittel, schnell;
  // Zeit welche gewartet wird zwischen simulations durchlauf
  int warteZeit = 250;

  /**
   * Konstruktor von Game of Life
   *
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
            farbeTot = JColorChooser.showDialog(null, "Farbe für tote Zelle auswählen", farbeTot);
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
            farbeLebendig =
                JColorChooser.showDialog(
                    null, "Farbe für lebendige Zelle auswählen", farbeLebendig);
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

    // Fügt Figuren zum Menü hinzu
    figuren = new JMenu("Figuren");
    menuBar.add(figuren);
    obszilierend = new JMenu("Obszilierende Objekte");
    figuren.add(obszilierend);
    segler = new JMenu("Gleiter und Raumschiffe");
    figuren.add(segler);
    // Fügt obszilierende Objekte hinzu
    blinker = new JMenuItem("Blinker");
    blinker.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int[][] blinker =
                new int[][] {
                  {0, 1, 0},
                  {0, 1, 0},
                  {0, 1, 0}
                };
            zeichneFigur(blinker);
          }
        });
    obszilierend.add(blinker);
    uhr = new JMenuItem("Uhr");
    uhr.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int[][] uhr =
                new int[][] {
                  {0, 0, 1, 0, 0, 0},
                  {0, 0, 1, 0, 1, 0},
                  {0, 1, 0, 1, 0, 0},
                  {0, 0, 0, 1, 0, 0}
                };
            zeichneFigur(uhr);
          }
        });
    obszilierend.add(uhr);

    oktagon = new JMenuItem("Oktagon");
    oktagon.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int[][] oktagon =
                new int[][] {
                  {0, 0, 0, 0, 0, 0, 0},
                  {0, 0, 1, 0, 0, 1, 0},
                  {0, 1, 0, 1, 1, 0, 1},
                  {0, 0, 1, 0, 0, 1, 0},
                  {0, 0, 1, 0, 0, 1, 0},
                  {0, 1, 0, 1, 1, 0, 1},
                  {0, 0, 1, 0, 0, 1, 0}
                };
            zeichneFigur(oktagon);
          }
        });
    obszilierend.add(oktagon);

    // Fügt Segler und Raumschiffe hinzu
    gleiter = new JMenuItem("Gleiter");
    gleiter.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int[][] gleiter =
                new int[][] {
                  {0, 1, 0},
                  {0, 0, 1},
                  {1, 1, 1}
                };
            zeichneFigur(gleiter);
          }
        });
    segler.add(gleiter);

    lwss = new JMenuItem("Light-Weight Spaceship");
    lwss.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int[][] lwss =
                new int[][] {
                  {0, 1, 1, 1, 1},
                  {1, 0, 0, 0, 1},
                  {0, 0, 0, 0, 1},
                  {1, 0, 0, 1, 0}
                };
            zeichneFigur(lwss);
          }
        });
    segler.add(lwss);

    mwss = new JMenuItem("Middle-Weight Spaceship");
    mwss.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int[][] mwss =
                new int[][] {
                  {0, 1, 1, 1, 1, 1},
                  {1, 0, 0, 0, 0, 1},
                  {0, 0, 0, 0, 0, 1},
                  {1, 0, 0, 0, 1, 0},
                  {0, 0, 1, 0, 0, 0}
                };
            zeichneFigur(mwss);
          }
        });
    segler.add(mwss);

    geschwindigkeit = new JMenu("Geschwindigkeit");
    menuBar.add(geschwindigkeit);
    langsam = new JMenuItem("Langsam");
    mittel = new JMenuItem("Mittel");
    schnell = new JMenuItem("Schnell");
    geschwindigkeit.add(langsam);
    geschwindigkeit.add(mittel);
    geschwindigkeit.add(schnell);

    langsam.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        warteZeit = 500;
      }
    });

    mittel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        warteZeit = 250;
      }
    });

    schnell.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        warteZeit = 100;
      }
    });


    popupMenu = new JPopupMenu();
    popupMenu.add(farbeToteZelleÄndern);
    popupMenu.add(farbeLebendigeZelleÄndern);
    setComponentPopupMenu(popupMenu);

    setPreferredSize(new Dimension(250, 250));
    setVisible(true);
    setClosable(true);
    setResizable(true);
    setIconifiable(true);
    setMaximizable(true);
    setTitle("Game of Life" + ++fensterzahl);
    new Thread(
            new Runnable() {
              @Override
              public void run() {
                // TODO - Beim schließen des Fensters
                //  wird der Thread nicht beendet
                while (true) {
                  if (GameOfLifeGUI.ausgewaehlterModus == States.LAUFEN){
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

                          if (lebendeNachbarn == 3 && !gameboard[i][j].isAlive) gameboard[i][j].lebendigSetzen = true;
                          else if ((lebendeNachbarn == 2 || lebendeNachbarn == 3) && gameboard[i][j].isAlive)
                              gameboard[i][j].lebendigSetzen = true;
                          else gameboard[i][j].lebendigSetzen = false;

                      }
                    }
                      for (Zelle[] zellen : gameboard) {
                          for (Zelle zelle : zellen) {
                              zelle.isAlive = zelle.lebendigSetzen;
                              zelle.updateColor();
                          }
                      }

                  }



                  SwingUtilities.invokeLater(
                      () -> {
                        SwingUtilities.updateComponentTreeUI(GameOfLife.this.getContentPane());
                      });

                  try {
                    sleep(warteZeit); //TODO Wartezeit-Button
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
              }
            })
        .start();
  }

  /**
   * @param e ist das MouseEvent
   */
  public void showPopUpMenu(MouseEvent e) {
    popupMenu.show(e.getComponent(), e.getX(), e.getY());
  }

  public void clear() {
    for (int i = 0; i < gameboard.length; i++) {
      for (int j = 0; j < gameboard[i].length; j++) {
        gameboard[i][j].isAlive = false;
        gameboard[i][j].updateColor();
        SwingUtilities.updateComponentTreeUI(this);
      }
    }
  }

  public void zeichneFigur(int[][] figur) {
    clear();
    for (int i = 0; i < figur.length; i++) {
      for (int j = 0; j < figur[i].length; j++) {
        if (figur[i][j] == 1) {
          gameboard[i][j].isAlive = true;
          gameboard[i][j].updateColor();
          SwingUtilities.updateComponentTreeUI(this);
        } else {
          gameboard[i][j].isAlive = false;
          gameboard[i][j].updateColor();
          SwingUtilities.updateComponentTreeUI(this);
        }
      }
    }
  }
}
