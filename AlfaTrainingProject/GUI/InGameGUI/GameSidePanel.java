package InGameGUI;

import Heroes.Hero;
import static InGameGUI.MapPanel.MAPSTATE_AIMING;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Das Panel, auf dem die Sidebar mit Helden, Aktionen usw angezeigt wird.
 */
class GameSidePanel extends JPanel {

    private final static int PANELSIZE_X = 840;
    private final static int PANELSIZE_Y = 1080;

    private Image backgroundImage;

    //Array der Gegnerhelden, angezeigt im OtherHeroesPanel oben
    private ArrayList<Hero> otherHeroes;

    //der Held des Hauptspielers, angezeigt im zentralen HeroPanelLarge
    private Hero playerHero;

    //Die Hero-Panels
    private OtherHeroesPanel panelOtherHeroes;
    private HeroPanelLarge panelPlayerHero;

    //Die Dice-Panels
    private AttackDicePanel panelAttackDice;
    private HideDicePanel panelHideDice;

    //Die Threads für die Würfelanimationen, werden im Konstruktor gestartet
    private Thread threadAttackDicePanel;
    private Thread threadHideDicePanel;

    public GameSidePanel(ArrayList<Hero> otherHeroes, Hero playerHero) {
        super();
        this.otherHeroes = otherHeroes;
        this.playerHero = playerHero;

        setPreferredSize(new Dimension(PANELSIZE_X, PANELSIZE_Y));
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Gameboard_Right.png"))
                .getImage();

        //TODO: Layout (evtl. weitere Unterteilungen), Elemente hinzufügen
        setLayout(null);

        panelOtherHeroes = new OtherHeroesPanel(otherHeroes);
        panelOtherHeroes.setBounds(14, 85, 780, 200);

        panelPlayerHero = new HeroPanelLarge(playerHero);
        panelPlayerHero.setBounds(30, 340, 558, 393);

        panelAttackDice = new AttackDicePanel();
        panelAttackDice.setBounds(20, 750, 350, 250);
        threadAttackDicePanel = new Thread(panelAttackDice);
        threadAttackDicePanel.start();

        panelHideDice = new HideDicePanel();
        panelHideDice.setBounds(370, 742, 437, 295);
        threadHideDicePanel = new Thread(panelHideDice);
        threadHideDicePanel.start();

        add(panelOtherHeroes);
        add(panelPlayerHero);
        add(panelAttackDice);
        add(panelHideDice);

    }

    /**
     * Zeichnet das Panel. Die meisten Elemente werden als eigene Components
     * hinzugefügt und zeichnen sich daher selbst, nur das Hintergrundbild wird
     * hier extra gezeichnet.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // Hintergrund ganz unten
        g2d.drawImage(backgroundImage, 0, 0, PANELSIZE_X, PANELSIZE_Y, this);

    }

    public void setOtherHeroes(ArrayList<Hero> otherHeroes) {
        this.otherHeroes = otherHeroes;
    }

    public OtherHeroesPanel getPanelOtherHeroes() {
        return panelOtherHeroes;
    }

    public HeroPanelLarge getPanelPlayerHero() {
        return panelPlayerHero;
    }

    public AttackDicePanel getPanelAttackDice() {
        return panelAttackDice;
    }

    public HideDicePanel getPanelHideDice() {
        return panelHideDice;
    }

    public Thread getThreadAttackDicePanel() {
        return threadAttackDicePanel;
    }

    public Thread getThreadHideDicePanel() {
        return threadHideDicePanel;
    }
}
