package InGameGUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import Heroes.Hero;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

import java.awt.Dimension;
import javax.swing.ImageIcon;

/**
 * Panel f�r die Darstellung eines Helden mit aktueller Anzahl an Lebens- und
 * Aktionspunkten sowie Verz�gerungsmarken. Zur verkleinerten Darstellung
 * anderer Spieler.
 *
 * @author Peter
 */
public class HeroPanelSmall extends JPanel {

    private Hero displayedHero;
    private Image hitPointImage;
    private Image hitPointUsedImage;
    private Image actionPointImage;
    private Image actionPointUsedImage;
    private Image delayTokenImage;

    private boolean grayedOut;

    private static final double POINTICON_TOPMARGIN_RELATIVE_Y = 0.05;
    private static final double POINTICON_SIDEMARGIN_RELATIVE_X = 0.05;
    private static final double POINTICON_SIZE_RELATIVE_X = 0.125;
    private static final double POINTICON_SIZE_RELATIVE_Y = 0.125;

    private static final double DELAYTOKEN_SIZE_RELATIVE_X = 0.1;
    private static final double DELAYTOKEN_SIZE_RELATIVE_Y = 0.18;
    private static final double DELAYTOKEN_BOTTOMMARGIN_RELATIVE_Y = 0.05;

    public HeroPanelSmall(Hero hero) {

        displayedHero = hero;
        grayedOut = false;

        hitPointImage = ImageLoader.getInstance().getImage(ImageName.HEART_ACTIVATED);
        hitPointUsedImage = ImageLoader.getInstance().getImage(ImageName.HEART_DEACTIVATED);
        actionPointImage = ImageLoader.getInstance().getImage(ImageName.ACTION_ACTIVATED);
        actionPointUsedImage = ImageLoader.getInstance().getImage(ImageName.ACTION_DEACTIVATED);
        delayTokenImage = ImageLoader.getInstance().getImage(ImageName.DELAY);

        setLayout(null);

    }

    /**
     * Zeichnet das Panel. Die Elemente werden dynamisch auf die aktuelle Gr��e
     * angepasst and angeordnet.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (displayedHero.isDead()) {
            grayedOut = true;
        }

        // Avatar als Hintergrund
        if (grayedOut) {
            g2d.drawImage(displayedHero.getAvatarDeactivated(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g2d.drawImage(displayedHero.getAvatar(), 0, 0, getWidth(), getHeight(), this);
        }

        //reale Abmessungen der Aktions- und Lebenspunkte basierend auf der aktuellen Panelgr��e
        int iconSize_X = (int) (POINTICON_SIZE_RELATIVE_X * getWidth());
        int iconSize_Y = (int) (POINTICON_SIZE_RELATIVE_Y * getHeight());
        int sideMargin = (int) (getWidth() * POINTICON_SIDEMARGIN_RELATIVE_X);
        int topMargin = (int) (getHeight() * POINTICON_TOPMARGIN_RELATIVE_Y);

        // Overlays f�r Actionpoints (links)
        drawActionPointIcons(g2d, iconSize_X, iconSize_Y, sideMargin, topMargin);

        // Overlays f�r Hitpoints (rechts)
        drawHitPointIcons(g2d, iconSize_X, iconSize_Y, sideMargin, topMargin);

        //reale Abmessungen der DelayTokens basierend auf der aktuellen Panelgr��e
        int delayTokenSize_X = (int) (DELAYTOKEN_SIZE_RELATIVE_X * getWidth());
        int delayTokenSize_Y = (int) (DELAYTOKEN_SIZE_RELATIVE_Y * getHeight());

        // Overlays f�r Delaytokens (unten)
        drawDelayTokenIcons(g2d, delayTokenSize_X, delayTokenSize_Y);
    }

    /**
     * Zeichnet die DelayTokens zentriert an den unteren Rand des Panels. Nimmt
     * reale Gr��en (in Pixel) f�r die Abmessungen eines einzelnen Tokens
     * entgegen.
     *
     * @param g2d
     * @param delayTokenSize_X
     * @param delayTokenSize_Y
     */
    private void drawDelayTokenIcons(Graphics2D g2d, int delayTokenSize_X, int delayTokenSize_Y) {
        int numDelayTokens = displayedHero.getDelayTokens();
        int totalSize_X = numDelayTokens * delayTokenSize_X;

        for (int i = 0; i < numDelayTokens; i++) {
            int position_x = ((getWidth() - totalSize_X) / 2) + (i * delayTokenSize_X);
            int position_y = getHeight() - delayTokenSize_Y - (int) (DELAYTOKEN_BOTTOMMARGIN_RELATIVE_Y * getHeight());

            g2d.drawImage(delayTokenImage,
                    position_x,
                    position_y,
                    delayTokenSize_X,
                    delayTokenSize_Y,
                    this);
        }

    }

    /**
     * Zeichnet die Icons der Action Points an den linken Rand des Panels,
     * angefangen in der oberen linken Ecke. Nimmt reale Gr��en (in Pixel) f�r
     * die Abmessungen eines einzelnen Icons sowie einen Seitenabstand entgegen.
     * entgegen.
     *
     * @param g2d
     * @param iconSize_X
     * @param iconSize_Y
     * @param sideMargin
     */
    private void drawActionPointIcons(Graphics2D g2d, int iconSize_X, int iconSize_Y, int sideMargin, int topMargin) {
        int maxPoints = displayedHero.getMaxActionPoints();
        int currentPoints = displayedHero.getCurrentActionPoints();

        for (int i = 0; i < maxPoints; i++) {
            if (currentPoints > 0) {
                g2d.drawImage(actionPointImage,
                        sideMargin, i * (iconSize_Y) + topMargin,
                        iconSize_X, iconSize_Y,
                        this);
                currentPoints--;
            } else {
                g2d.drawImage(actionPointUsedImage,
                        sideMargin, i * (iconSize_Y) + topMargin,
                        iconSize_X, iconSize_Y,
                        this);
            }

        }

    }

    /**
     * Zeichnet die Icons der Hit Points an den rechten Rand des Panels,
     * angefangen in der oberen rechten Ecke. Nimmt reale Gr��en (in Pixel) f�r
     * die Abmessungen eines einzelnen Icons sowie einen Seitenabstand entgegen.
     *
     * @param g2d
     * @param iconSize_X
     * @param iconSize_Y
     * @param sideMargin
     */
    private void drawHitPointIcons(Graphics2D g2d, int iconSize_X, int iconSize_Y, int sideMargin, int topMargin) {
        int maxPoints = displayedHero.getMaxHitPoints();
        int currentPoints = displayedHero.getCurrentHitPoints();

        for (int i = 0; i < maxPoints; i++) {
            if (currentPoints > 0) {
                g2d.drawImage(hitPointImage,
                        getWidth() - sideMargin - iconSize_X, i * (iconSize_Y) + topMargin,
                        iconSize_X, iconSize_Y,
                        this);
                currentPoints--;
            } else {
                g2d.drawImage(hitPointUsedImage,
                        getWidth() - sideMargin - iconSize_X, i * (iconSize_Y) + topMargin,
                        iconSize_X, iconSize_Y,
                        this);
            }
        }

    }

    public boolean isGrayedOut() {
        return grayedOut;
    }

    public void setGrayedOut(boolean grayedOut) {
        this.grayedOut = grayedOut;
        getParent().repaint();
    }

}
