package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
//	PLAYER
	public static final String PLAYER_IDLE = "/player/Idle.png";
	public static final String PLAYER_RUN = "/player/Run.png";
	public static final String PLAYER_JUMP = "/player/Jump.png";
	public static final String PLAYER_HURT = "/player/Hurt.png";
	public static final String PLAYER_ATTACK = "/player/Attack_3.png";
	public static final String PLAYER_DEAD = "/player/Dead.png";
	public static final String PLAYER_FALLING = "/player/Jump.png";

	public static final String LEVEL_ATLAS = "/outside_sprites.png";

//	MAP
	public static final String MAP01 = "/maps/map01.txt";

	public static BufferedImage getSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream(fileName);

		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static BufferedImage getSpriteAtlas(File file) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(file);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static int[][] getMapData(String fileName) {
		int[][] mapData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		InputStream is = LoadSave.class.getResourceAsStream(fileName);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
				String line = br.readLine().replace("   ", "  ");
				String[] number = line.split("  ");

				for (int j = 0; j < Game.TILES_IN_WIDTH; j++) {
					mapData[i][j] = Integer.parseInt(number[j]);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mapData;
	}
}
