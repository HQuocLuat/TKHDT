package utilz;

public class Constants {

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUN = 1;
		public static final int JUMP = 2;
		public static final int HURT = 3;
		public static final int ATTACK = 4;
		public static final int DEAD = 5;
		public static final int FALLING = 6;
		
		public static int getSpriteAmount(int player_action) {
			switch(player_action) {
			case IDLE:
				return 5;
			case RUN:
				return 8;
			case JUMP:
				return 1;
			case HURT:
				return 2;
			case ATTACK:
				return 4;
			case DEAD:
				return 5;
			case FALLING:
				return 3;
			default:
				return 1;
			}
		}
	}
}
