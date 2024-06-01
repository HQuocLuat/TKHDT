package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import main.Game;
import utilz.LoadSave;

public class TileManager {
	private Game game;
	private Tile[] tiles;
	private int[][] mapTileID;

	public TileManager(Game game) {
		this.game = game;
		tiles = new Tile[61];
		mapTileID = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];

		getTileImage();
		loadMap();
	}

	public void getTileImage() {
		File folder = new File("./res/tiles");
		
		File[] files = folder.listFiles();
		
		for (int i = 0; i < tiles.length - 1; i++) {
			tiles[i] = new Tile();
			tiles[i].setImg(LoadSave.getSpriteAtlas(files[i]));
		}
		
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
		tiles[60] = new Tile();
		tiles[60].setImg(img.getSubimage(11 * 32, 0, 32, 32));
	}
	
	public void loadMap() {
		mapTileID = LoadSave.getMapData(LoadSave.MAP01);
	}

	public void draw(Graphics g) {
		for (int i = 0; i < Game.TILES_IN_HEIGHT; i++)
			for (int j = 0; j < Game.TILES_IN_WIDTH; j++) {
				int tileID = mapTileID[i][j];
				g.drawImage(tiles[tileID].getImg(), j * Game.TILES_SIZE, i * Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}

	public void update() {
		
	}
	
	public int[][] getMapTileID() {
		return mapTileID;
	}
}
