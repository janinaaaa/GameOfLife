package gameOfLife;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 * @author Janina Dörflinger (211923)
 * @author  Marvin Hill (211926)
 * @version 15.05.2022
 *
 * Zelle repräsentiert eine Zelle, die zu Game of Life gehört
 */

public class Zelle extends JPanel{
    GameOfLife game;
    boolean isAlive; //zeigt an, ob eine Zelle tot oder lebendig ist

    Zelle(GameOfLife game){
        setBorder(new LineBorder(Color.pink));
        this.game = game;
        isAlive = false;
        this.updateColor();

        /**
         * Wenn der Modus setzen ist, dann kann man mit der rechten Maustaste (Button 1)
         * die Zelle tot oder lebendig setzen. Dabei wird ihre Farbe geändert
         * Falls eine andere Taste geklickt wird, wird das PopUpMenü aufgerufen
         */
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(MouseEvent.BUTTON1 == e.getButton()){
                    if(GameOfLifeGUI.ausgewaehlterModus.equals("setzen"))
                        isAlive = !isAlive;
                    updateColor();
                    SwingUtilities.updateComponentTreeUI(game.getContentPane());

                }
                else {
                    game.showPopUpMenu(e);
                }
            }

            /**
             * Setzt die Zellen lebendig, über die mit der Maus gefahren wird
             * * @param e ist das Maus-Event
             */
            @Override
            public void mouseEntered(MouseEvent e) {
               if(GameOfLifeGUI.ausgewaehlterModus.equals("malen")){
                    isAlive = true;
                    updateColor();
                    SwingUtilities.updateComponentTreeUI(game.getContentPane());
               }
            }
        });
    }

    /**
     * setzt die neu ausgewählten Farben
     */
    public void updateColor(){
        if(isAlive){
            setBackground(game.farbeLebendig);
        }
        else {
            setBackground(game.farbeTot);
        }
    }




}
