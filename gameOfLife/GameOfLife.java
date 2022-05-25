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

  JMenu figuren, obszilierend, segler;
  JMenuItem blinker, uhr, oktagon, gleiter, lwss, mwss;
  // Zeit welche gewartet wird zwischen simulations durchlauf
  int warteZeit = 50;

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
                  if (GameOfLifeGUI.ausgewaehlterModus == States.LAUFEN)
                    for (int i = 0; i < gameboard.length; i++) {
                      for (int j = 0; j < gameboard[i].length; j++) {
                        int lebendeNachbarn = 0;

                        System.out.print("\nzelle " + "(" + i + "," + j + ") : ");
                        for (Directions d : Directions.values()) {

                          // TODO - Ursprüngliches Datenarray darf erst ganz am Ende verändert
                          //  werden, jede Zelle muss noch auf das ursprüngliche Array zugreifen
                          //  können!

                          int ni = i + d.i;
                          int nj = j + d.j;

                          if (ni < 0) {
                            ni = gameboard.length - 1;
                          }
                          if (nj < 0) {
                            nj = gameboard[i].length - 1;
                          }
                          if (ni > (gameboard.length - 1)) {
                            ni = 0;
                          }
                          if (nj > (gameboard[i].length - 1)) {
                            nj = 0;
                          }

                          System.out.print("(" + d + " " + ni + "," + nj + ")");
                        }

                        if (lebendeNachbarn == 3 && !gameboard[i][j].isAlive) {
                          gameboard[i][j].isAlive = true;
                          gameboard[i][j].updateColor();
                        } else if (lebendeNachbarn < 2 && gameboard[i][j].isAlive) {
                          gameboard[i][j].isAlive = false;
                          gameboard[i][j].updateColor();
                        } else if ((lebendeNachbarn == 2 || lebendeNachbarn == 3)
                            && gameboard[i][j].isAlive) {
                          gameboard[i][j].isAlive = true;
                          gameboard[i][j].updateColor();
                        } else if (lebendeNachbarn > 3 && gameboard[i][j].isAlive) {
                          gameboard[i][j].isAlive = false;
                          gameboard[i][j].updateColor();
                        }
                      }
                    }
                  SwingUtilities.invokeLater(
                      () -> {
                        SwingUtilities.updateComponentTreeUI(GameOfLife.this.getContentPane());
                      });

                  try {
                    sleep(warteZeit);
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
