package InGameGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Das Gesamtpanel, dass w�hrend des Spiels gezeigt wird. Beinhaltet das
 * eigentliche Spielfeld sowie die Sidebar mit Helden, Aktionen usw.
 *
 */
public class GamePanel extends JPanel {

    private MapPanel mapPanel;
    private GameSidePanel gameSidePanel;

    private static final int MAPPANEL_STANDARD_SIZE = 1080;

    public GamePanel(MapPanel mp, GameSidePanel gsp) {
        super();
        mapPanel = mp;
        mapPanel.setPreferredSize(new Dimension(MAPPANEL_STANDARD_SIZE, MAPPANEL_STANDARD_SIZE));

        gameSidePanel = gsp;

        setLayout(new BorderLayout());

        add(mapPanel, BorderLayout.LINE_START);
        add(gameSidePanel, BorderLayout.LINE_END);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        //MapPanel quadratisch links hinein
//        mapPanel.setPreferredSize(new Dimension(getHeight(), getHeight()));
        
        //GameSidePanel f�llt den Rest
//        gameSidePanel.setPreferredSize(new Dimension(getWidth() - getHeight(), getHeight()));
        
        super.paintComponent(grphcs);
    }
    
    

}
