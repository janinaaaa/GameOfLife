package gameOfLife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ContainerAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;


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
  // Fenster geschlossen muss den Thread stoppen
  boolean closed;

  JMenuBar menuBar = new JMenuBar();

  JMenu figuren, geschwindigkeit;
  JMenuItem figurenWaehlen, langsam, mittel, schnell;
  // Zeit welche gewartet wird zwischen simulations durchlauf
  int warteZeit = 250;

  /**
   * Konstruktor von Game of Life
   *
   * @param sizeZeilen,
   * @param sizeSpalten Die Größe des Spielfeldes von Game of Life
   * @param farbeL Farbe der lebendigen Zellen
   * @param farbeT Farbe der toten Zellen
   */
  GameOfLife(int sizeZeilen, int sizeSpalten, Color farbeL, Color farbeT) {
    closed = false;
    farbeLebendig = farbeL;
    farbeTot = farbeT;
    farbeToteZelleÄndern = new JMenuItem("Farbe: Tote Zellen");
    farbeLebendigeZelleÄndern = new JMenuItem("Farbe: Lebendige Zellen");

    // Spielfeldgröße setzen
    gameboard = new Zelle[sizeZeilen][sizeSpalten];
    // Spielfeld mit toten Zellen füllen
    for (int i = 0; i < gameboard.length; i++) {
      for (int j = 0; j < gameboard[i].length; j++) {
        gameboard[i][j] = new Zelle(this);
      }
    }

    //Man kann die Farben der toten Zellen über einen JColorChooser ändern
    farbeToteZelleÄndern.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            farbeTot = JColorChooser.showDialog(null, "Farbe für tote Zelle auswählen", farbeTot);
            for (int i = 0; i < gameboard.length; i++) {
              for (int j = 0; j < gameboard[i].length; j++) {
                gameboard[i][j].updateColor();
              }
            }
          }
        });

    //Man kann die Farben der lebendigen Zellen über einen JColorChooser ändern
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


    // Fügt die Zellen zu dem Layout hinzu
    setLayout(new GridLayout(sizeZeilen, sizeSpalten));
    for (int i = 0; i < gameboard.length; i++) {
      for (int j = 0; j < gameboard[i].length; j++) {
        getContentPane().add(gameboard[i][j]);
      }
    }

    this.setJMenuBar(menuBar);

    // Fügt Figuren zum Menü hinzu
    figuren = new JMenu("Figuren");
    menuBar.add(figuren);
    figurenWaehlen = new JMenuItem("Figuren wählen");
    figuren.add(figurenWaehlen);

    figurenWaehlen.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            new FigurWaehlenFenster(GameOfLife.this);
          }
        });

    //Fügt Geschwindigkeit hinzu
    geschwindigkeit = new JMenu("Geschwindigkeit");
    menuBar.add(geschwindigkeit);
    langsam = new JMenuItem("Langsam");
    mittel = new JMenuItem("Mittel");
    schnell = new JMenuItem("Schnell");

    //Die Geschwindigkeit kann mit diesen MenuItems geändert werden
    geschwindigkeit.add(langsam);
    geschwindigkeit.add(mittel);
    geschwindigkeit.add(schnell);

    //Wartezeit wird jeweils verändert
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

    addInternalFrameListener(new InternalFrameAdapter() {
      @Override
      public void internalFrameClosing(InternalFrameEvent e) {
        super.internalFrameClosing(e);
        closed = true;
      }
    });

    // Neuer Thread für jedes Fenster
    new Thread(
            new Runnable() {
              /**
               * Die Run-Methode des Threads beeinhaltet die Logik des Spiels und ändert darauf
               * basierend den Zustand (tot, lebendig) der Zelle, allerdings nur, wenn der
               * ausgewählte Modus laufen ist. Dabei werden bei Zellen im Eck oder an Kanten, die
               * auf der anderen Seite berücksichtigt
               */
              @Override
              public void run() {
                while (!closed) {
                  if (GameOfLifeGUI.ausgewaehlterModus == States.LAUFEN) {
                    for (int i = 0; i < gameboard.length; i++) {
                      for (int j = 0; j < gameboard[i].length; j++) {
                        int x = i;
                        int y = j;
                        int lebendeNachbarn = 0;

                        // Links
                        Zelle zelle = gameboard[x][y - 1 < 0 ? gameboard[i].length - 1 : y - 1];
                        // Rechts
                        Zelle zelle2 = gameboard[x][(y + 1) % gameboard[i].length];
                        // Oben
                        Zelle zelle3 = gameboard[x - 1 < 0 ? gameboard.length - 1 : x - 1][y];
                        // Unten
                        Zelle zelle4 = gameboard[(x + 1) % gameboard.length][y];
                        // Rechtsoben
                        Zelle zelle5 =
                            gameboard[x - 1 < 0 ? gameboard.length - 1 : x - 1][
                                (y + 1) % gameboard[i].length];
                        // Rechtsunten
                        Zelle zelle6 =
                            gameboard[(x + 1) % gameboard.length][(y + 1) % gameboard[i].length];
                        // Linksoben
                        Zelle zelle7 =
                            gameboard[x - 1 < 0 ? gameboard.length - 1 : x - 1][
                                y - 1 < 0 ? gameboard[i].length - 1 : y - 1];
                        // Linksunten
                        Zelle zelle8 =
                            gameboard[(x + 1) % gameboard.length][
                                y - 1 < 0 ? gameboard[i].length - 1 : y - 1];

                        if (zelle.isAlive) lebendeNachbarn += 1;
                        if (zelle2.isAlive) lebendeNachbarn += 1;
                        if (zelle3.isAlive) lebendeNachbarn += 1;
                        if (zelle4.isAlive) lebendeNachbarn += 1;
                        if (zelle5.isAlive) lebendeNachbarn += 1;
                        if (zelle6.isAlive) lebendeNachbarn += 1;
                        if (zelle7.isAlive) lebendeNachbarn += 1;
                        if (zelle8.isAlive) lebendeNachbarn += 1;

                        if (lebendeNachbarn == 3 && !gameboard[i][j].isAlive)
                          gameboard[i][j].lebendigSetzen = true;
                        else if ((lebendeNachbarn == 2 || lebendeNachbarn == 3)
                            && gameboard[i][j].isAlive) gameboard[i][j].lebendigSetzen = true;
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

    /**
     * Setzt alle Zellen auf tot.
     */
  public void clear() {
    for (int i = 0; i < gameboard.length; i++) {
      for (int j = 0; j < gameboard[i].length; j++) {
        gameboard[i][j].isAlive = false;
        gameboard[i][j].updateColor();
        SwingUtilities.updateComponentTreeUI(this);
      }
    }
  }

    /**
     * Zeichnet die Figuren aufs Brett
     * @param figur entspricht der Figur, dabei steht eine 1 im Array für eine lebende Zelle
     *              und eine 0 für eine tote
     *  Davor werden alle Zellen auf tot gesetzt
     */
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

  /**
   * Ein Fenster welches angezeigt wird wenn man eine Figur laden möchte.
   */
  class FigurWaehlenFenster extends JFrame{
    //Layout Komponenten
    JPanel figurenContainer;
    JLabel ueberschrift;
    //Parent GameObjekt
    GameOfLife game;

    //Alle Figuren als int Schablone,
    // 1 = am Leben,
    // 0 = tot

    static int[][] blinker =
        new int[][] {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
        };
    static int[][] uhr =
        new int[][] {
            {0, 0, 1, 0, 0, 0},
            {0, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0}
        };
    static int[][] oktagon =
        new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 1, 0, 0},
            {0, 1, 0, 1, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 1, 0, 0},
            {0, 1, 0, 1, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
    static int[][] gleiter =
        new int[][] {
            {0, 1, 0},
            {0, 0, 1},
            {1, 1, 1}
        };
    static int[][] lwss =
        new int[][] {
            {0, 1, 1, 1, 1},
            {1, 0, 0, 0, 1},
            {0, 0, 0, 0, 1},
            {1, 0, 0, 1, 0}
        };
    static int[][] mwss =
        new int[][] {
            {0, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 0}
        };


    /**
     * Konstruktor für Figuren wählen Fenster, es wird das Fenster erstellt mit allen Figuren.
     * @param game Das parent game in welchem das Fenster geöffnet wurde.
     */
      FigurWaehlenFenster(GameOfLife game){
          // Zuweisung Parent game, Setup
          this.game = game;

          // Close operator wird gesetzt
          setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

          // Fenster wird mittig ausgerichtet auf dem Bildschirm
          setLocationRelativeTo(null);

          //Layout wird als BorderLayout festgelegt
          setLayout(new BorderLayout());

          // Überschrift erstellt
          ueberschrift = new JLabel("Spielfigur wählen");
          ueberschrift.setHorizontalAlignment(SwingConstants.CENTER);
          add(ueberschrift,BorderLayout.PAGE_START);

          // Container für Figuren erstellt
          figurenContainer = new JPanel(new FlowLayout());

          //Figuren
          figurenContainer.add(new FigurFenster(blinker,"Blinker",FigurWaehlenFenster.this),BorderLayout.CENTER);
          figurenContainer.add(new JPanel());
          figurenContainer.add(new FigurFenster(uhr,"Uhr",FigurWaehlenFenster.this),BorderLayout.CENTER);
          figurenContainer.add(new JPanel());
          figurenContainer.add(new FigurFenster(oktagon,"Oktagon",FigurWaehlenFenster.this),BorderLayout.CENTER);
          figurenContainer.add(new JPanel());
          figurenContainer.add(new FigurFenster(gleiter,"Gleiter",FigurWaehlenFenster.this),BorderLayout.CENTER);
          figurenContainer.add(new JPanel());
          figurenContainer.add(new FigurFenster(lwss,"Light-Weight Spaceship",FigurWaehlenFenster.this),BorderLayout.CENTER);
          figurenContainer.add(new JPanel());
          figurenContainer.add(new FigurFenster(mwss, "Middle-Weight Spaceship",FigurWaehlenFenster.this),BorderLayout.CENTER);


          // Figuren Container hinzufügen zu Fenster
          add(figurenContainer);

          // Fenster einpassen automatisch
          pack();

          // Setup
          setResizable(false);
          setVisible(true);
          setAlwaysOnTop(true);
      }

  }

  /**
   * Repraesentiert einzelne Figur im Figuren waehlen Fenster mit Beschriftung
   */
  public class FigurFenster extends JPanel {
     // Schablone 1 = am leben, 0 = tot
    int[][] data;
    // Layout Komponenten
    JPanel contentPanel;
    JLabel beschriftung;
    // Parent Objekt
    FigurWaehlenFenster parent;

    /**
     * Konstruktor es wird ein neue Figur erstellt aus einer int Schablone.
     * @param data Integer Schablone aus welcher die Zellen generiert werden.
     * @param name  Name und Beschriftung der Figur
     * @param parent  Parent Objekt der Figur
     */
    FigurFenster(int[][] data, String name, FigurWaehlenFenster parent) {
      this.parent = parent;
      this.data = data;
      //Modus wird auf Setzen gesetzt damit das Spiel beim aufbauen der Figur nicht weiterläuft
      GameOfLifeGUI.ausgewaehlterModus = States.SETZEN;

      // Listener wodurch bei klick auf Grafik die Figur aufgebaut wird
      // und das Fenster geschlossen
      addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          super.mouseClicked(e);
          FigurFenster.this.parent.game.zeichneFigur(FigurFenster.this.data);
          parent.dispose();
        }

        // Hover Effekt für die Figuren, indem die Border verändert wird
        @Override
        public void mouseEntered(MouseEvent e) {
          super.mouseEntered(e);
          setBorder(new BevelBorder(BevelBorder.LOWERED));
        }

        @Override
        public void mouseExited(MouseEvent e) {
          super.mouseExited(e);
          setBorder(new BevelBorder(BevelBorder.RAISED) );
        }
      });

      // Alle Borders werden standardmäßig auf raised gesetzt
      setBorder(new BevelBorder(BevelBorder.RAISED) );

      // Layout Einstellungen für das Fenster in welchem die Figur dargestellt wird.
      Dimension dimension = new Dimension(100, 100);
      setLayout(new BorderLayout());
      contentPanel = new JPanel(new GridLayout(data.length, data[0].length));
      contentPanel.setPreferredSize(dimension);
      add(contentPanel, BorderLayout.CENTER);

      // Beschriftung der Figur
      beschriftung = new JLabel(name);
      beschriftung.setHorizontalAlignment(SwingConstants.CENTER);
      add(beschriftung,BorderLayout.PAGE_END);

      // Zellen werden nach dem data Schema generiert und dem Figur-Fenster hinzugefügt
      for (int[] i : this.data) {
        for (int j : i) {
          JPanel zelle = new JPanel();
          zelle.setBorder(new LineBorder(Color.black));
          if (j == 1) {
            zelle.setBackground(this.parent.game.farbeLebendig);
          }
          if (j == 0) {
            zelle.setBackground(this.parent.game.farbeTot);
          }
          contentPanel.add(zelle);
        }
      }
    }
  }
}
