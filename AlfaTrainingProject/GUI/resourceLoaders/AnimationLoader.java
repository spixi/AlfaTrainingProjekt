package resourceLoaders;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class AnimationLoader {

	private static final AnimationLoader animationLoaderSingleton = new AnimationLoader();

	private ArrayList<Image>[] animations;

	private AnimationLoader() {
		animations = new ArrayList[ImageName.values().length];

		// Animationen (Image-ArrayLists) ins Array laden. 

		
		Image frame;
		
		ArrayList<Image> attackDiceArrayList = new ArrayList<>();
		for (int i = 0; i < 220; i++) {
			// Pfad-String zusammenbasteln. image 0 -> 0000, 15 -> 0015 etc
			StringBuilder sb = new StringBuilder();
			sb.append("dice_w10/testsequence");
			int additionalZeros = 4 - ("" + i).length();

			for (int j = 0; j < additionalZeros; j++) {
				sb.append("0");
			}
			sb.append(i + ".png");

			frame = new ImageIcon(getClass().getClassLoader().getResource(sb.toString())).getImage();
			attackDiceArrayList.add(frame);
		}
		animations[AnimationName.ATTACKDICE.ordinal()]  = attackDiceArrayList;
				
		ArrayList<Image> hideDiceArrayList = new ArrayList<>();
		for (int i = 0; i < 120; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("dice_w6/testsequence");
            int additionalZeros = 4 - ("" + i).length();

            for (int j = 0; j < additionalZeros; j++) {
                sb.append("0");
            }
            sb.append(i + ".png");

            frame = new ImageIcon(getClass().getClassLoader().getResource(sb.toString())).getImage();
            hideDiceArrayList.add(frame);
        }
		animations[AnimationName.HIDEDICE.ordinal()]  = hideDiceArrayList;
		
		ArrayList<Image> deactivatedHideoutArrayList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			deactivatedHideoutArrayList
					.add(new ImageIcon(getClass().getClassLoader().getResource("Gameboard/" + i + "-Deactivated.png"))
							.getImage());
		}
		animations[AnimationName.DEACTIVATED_HIDEOUTS.ordinal()]  = deactivatedHideoutArrayList;
		
	}

	public static AnimationLoader getInstance() {
		return animationLoaderSingleton;
	}

	public ArrayList<Image> getAnimation(AnimationName name) {

		return animations[name.ordinal()];
	}
}