package InGameGUI;

import Dice.AttackDice;
import Dice.HideDice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 */
public class HideDicePanel extends JPanel implements Runnable {

    // relative Position/Gr��e der Grafiken im Bezug auf die Panelgr��e
    private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_X = 0.0;
    private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_Y = 0.04;
    private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_X = 0.55;
    private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_Y = 0.85;

    private static final double SPOTLIGHTCONE_POSITION_RELATIVE_X = 0.22;
    private static final double SPOTLIGHTCONE_POSITION_RELATIVE_Y = 0.09;
    private static final double SPOTLIGHTCONE_SIZE_RELATIVE_X = 0.59;
    private static final double SPOTLIGHTCONE_SIZE_RELATIVE_Y = 0.74;

    private static final double DIE_POSITION_RELATIVE_X = -0.23;
    private static final double DIE_POSITION_RELATIVE_Y = -0.1;
    private static final double DIE_SIZE_RELATIVE_X = 1;
    private static final double DIE_SIZE_RELATIVE_Y = 1.2;

    private static final double CARD_POSITION_RELATIVE_X = 0.62;
    private static final double CARD_POSITION_RELATIVE_Y = 0.0;
    private static final double CARD_SIZE_RELATIVE_X = 0.39;
    private static final double CARD_SIZE_RELATIVE_Y = 1.0;

    private ArrayList<Image> animationImages;
    private Image cardImage;
    private Image spotlightConeImage;
    private Image spotlightCircleImage;

    private int currentAnimationFrame;
    private int targetAnimationFrame;

    public HideDicePanel() {

        // Animationsbilder laden
        Image frame;
        animationImages = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("dice_w6/testsequence");
            int additionalZeros = 4 - ("" + i).length();

            for (int j = 0; j < additionalZeros; j++) {
                sb.append("0");
            }
            sb.append(i + ".png");

            frame = new ImageIcon(getClass().getClassLoader().getResource(sb.toString())).getImage();
            animationImages.add(frame);
        }

        // Tower und Spotlight laden
        cardImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Hideout_Card.jpg")).getImage();
        spotlightConeImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/spot_right.png"))
                .getImage();
        spotlightCircleImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/spot_hideout.png"))
                .getImage();

//        setBackground(Color.BLACK);
		setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Spotlight Kreis ganz unten
        g2d.drawImage(spotlightCircleImage,
                (int) (getWidth() * SPOTLIGHTCIRCLE_POSITION_RELATIVE_X),
                (int) (getHeight() * SPOTLIGHTCIRCLE_POSITION_RELATIVE_Y),
                (int) (getWidth() * SPOTLIGHTCIRCLE_SIZE_RELATIVE_X),
                (int) (getHeight() * SPOTLIGHTCIRCLE_SIZE_RELATIVE_Y), this);

        // Die Karte daneben
        g2d.drawImage(cardImage,
                (int) (getWidth() * CARD_POSITION_RELATIVE_X),
                (int) (getHeight() * CARD_POSITION_RELATIVE_Y),
                (int) (getWidth() * CARD_SIZE_RELATIVE_X),
                (int) (getHeight() * CARD_SIZE_RELATIVE_Y), this);

        // den Cone darauf
        g2d.drawImage(spotlightConeImage,
                (int) (getWidth() * SPOTLIGHTCONE_POSITION_RELATIVE_X),
                (int) (getHeight() * SPOTLIGHTCONE_POSITION_RELATIVE_Y),
                (int) (getWidth() * SPOTLIGHTCONE_SIZE_RELATIVE_X),
                (int) (getHeight() * SPOTLIGHTCONE_SIZE_RELATIVE_Y),
                this);

        // und zuletzt den W�rfel
        g2d.drawImage(animationImages.get(currentAnimationFrame),
                (int) (getWidth() * DIE_POSITION_RELATIVE_X),
                (int) (getHeight() * DIE_POSITION_RELATIVE_Y),
                (int) (getWidth() * DIE_SIZE_RELATIVE_X),
                (int) (getHeight() * DIE_SIZE_RELATIVE_Y), this);
    }

    public void setRollResult(int result) {
        switch (result) {
            case HideDice.RESULT_NOTHING:
                if (currentAnimationFrame < 60 && currentAnimationFrame >= 1) {
                    targetAnimationFrame = 0;
                }
                if (currentAnimationFrame < 100 && currentAnimationFrame >= 60) {
                    targetAnimationFrame = 40;
                }
                if (currentAnimationFrame < 1 || currentAnimationFrame >= 100) {
                    targetAnimationFrame = 60;
                }
                break;
            case HideDice.RESULT_FAILURE:

                targetAnimationFrame = 20;

                break;
            case HideDice.RESULT_SUCCESS:
                if (currentAnimationFrame < 20 || currentAnimationFrame >= 81) {
                    targetAnimationFrame = 80;
                }
                if (currentAnimationFrame < 81 && currentAnimationFrame >= 20) {
                    targetAnimationFrame = 100;
                }
                break;

        }
    }

    @Override
    public void run() {
        while (true) {
            if (currentAnimationFrame != targetAnimationFrame) {
                currentAnimationFrame = (currentAnimationFrame + 1) % 120;
                repaint();
            }

            try {
                Thread.sleep(20);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}