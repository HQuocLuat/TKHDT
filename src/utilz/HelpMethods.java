package utilz;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {
	public static boolean canMoveHere(float x, float y, float width, float height, int[][] mapData) {
		if (!isSolid(x, y, mapData))
			if (!isSolid(x + width, y + height, mapData))
				if (!isSolid(x + width, y, mapData))
					if (!isSolid(x, y + height, mapData))
						return true;
		return false;
	}

	private static boolean isSolid(float x, float y, int[][] mapData) {
		if (x < 0 || x >= Game.GAME_WIDTH)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		int value = mapData[(int) yIndex][(int) xIndex];

		if (value >= 61 || value < 0 || value != 60)
			return true;
		return false;
	}

	public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed, int[][] mapData) {
		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			// xOffset là khoảng cách từ x của tile đến x của hitbox
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		} else {
			// Left
			return currentTile * Game.TILES_SIZE;
		}
		
//		//fixing
//		int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
//
//        if (xSpeed > 0) {
//            // Di chuyển sang phải
//            int nextTileIndex = (int) ((hitbox.x + hitbox.width + xSpeed) / Game.TILES_SIZE);
//
//            // Kiểm tra nếu nhân vật sắp di chuyển ra khỏi ô gạch hiện tại
//            if (nextTileIndex != currentTile) {
//                // Kiểm tra xem ô gạch tiếp theo có là ô gạch trống không
//                if (isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y, mapData)) {
//                    // Trả về vị trí của góc trái của ô gạch kề cạnh
//                    return nextTileIndex * Game.TILES_SIZE - hitbox.width - 1;
//                }
//            }
//        } else if (xSpeed < 0) {
//            // Di chuyển sang trái
//            int nextTileIndex = (int) ((hitbox.x + xSpeed) / Game.TILES_SIZE);
//
//            // Kiểm tra nếu nhân vật sắp di chuyển ra khỏi ô gạch hiện tại
//            if (nextTileIndex != currentTile) {
//                // Kiểm tra xem ô gạch tiếp theo có là ô gạch trống không
//                if (isSolid(hitbox.x + xSpeed, hitbox.y, mapData)) {
//                    // Trả về vị trí của góc phải của ô gạch kề cạnh
//                    return (nextTileIndex + 1) * Game.TILES_SIZE;
//                }
//            }
//        }
//
//        // Nếu không có xung đột, trả về vị trí hiện tại
//        return hitbox.x;
	}

	public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed, int[][] mapData) {
//		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
//		if (airSpeed > 0) {
//			// Falling
//			int tileYPos = currentTile * Game.TILES_SIZE;
//			int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
//			return tileYPos + yOffset - 1;
//		} else {
//			// Jumping
//			return currentTile * Game.TILES_SIZE;
//		}
		
		//fixing
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);

        if (airSpeed > 0) {
            // Đang rơi xuống
            int nextTileIndex = (int) ((hitbox.y + hitbox.height + airSpeed) / Game.TILES_SIZE);

            // Kiểm tra nếu nhân vật sắp rơi ra khỏi ô gạch hiện tại
            if (nextTileIndex != currentTile) {
                // Kiểm tra xem ô gạch tiếp theo có là ô gạch trống không
                if (isSolid(hitbox.x, hitbox.y + hitbox.height + airSpeed, mapData)) {
                    // Trả về vị trí của góc trên của ô gạch kề cạnh
                    return nextTileIndex * Game.TILES_SIZE - hitbox.height - 1;
                }
            }
        } else if (airSpeed < 0) {
            // Đang nhảy lên
            int nextTileIndex = (int) ((hitbox.y + airSpeed) / Game.TILES_SIZE);

            // Kiểm tra nếu nhân vật sắp nhảy ra khỏi ô gạch hiện tại
            if (nextTileIndex != currentTile) {
                // Kiểm tra xem ô gạch tiếp theo có là ô gạch trống không
                if (isSolid(hitbox.x, hitbox.y + airSpeed, mapData)) {
                    // Trả về vị trí của góc dưới của ô gạch kề cạnh
                    return currentTile * Game.TILES_SIZE;
                }
            }
        }

        // Nếu không có xung đột, trả về vị trí hiện tại
        return hitbox.y;
	}

	public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] mapData) {
		// Check the pixel below bottomleft and bottomright
		if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, mapData))
			if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, mapData))
				return false;
		return true;
	}
}