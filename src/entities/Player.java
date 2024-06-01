package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.LoadSave.*;
import static utilz.HelpMethods.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 1.5f;
	private int[][] mapData;
	private float xDrawOffset = 46 * Game.SCALE_PLAYER;
	private float yDrawOffset = 58 * Game.SCALE_PLAYER;

	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 30 * Game.SCALE_PLAYER, 69 * Game.SCALE_PLAYER);
	}

	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();

		// Kiểm tra xem nhân vật có đang đứng trên mặt đất không
//        System.out.println(isEntityOnFloor(hitbox, mapData));
	}

	public void render(Graphics g) {
		if (playerAction >= 0 && playerAction < animations.length) {
			int animationLength = animations[playerAction].length;
			if (animationLength > 0) {
				aniIndex %= animationLength; // Dam bao rang aniIndex khong vuot qua kich thuoc cua mang con
				g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset),
						(int) (hitbox.y - yDrawOffset), width, height, null);
				drawHitbox(g);
			} else {
				System.out.println("Mang con RUN rong");
			}
		} else {
			System.out.println("playerAction vuot qua kich thuoc mang");
		}
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick > aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
	}

	private void setAnimation() {
		int startAni = playerAction;

//		if (moving) {
//			playerAction = RUN;
//		}else
//			playerAction = IDLE;
//
//		if (inAir) {
//			if (airSpeed < 0)
//				playerAction = JUMP;
//			else
//				playerAction = FALLING;
//		}
//
//		if (attacking)
//			playerAction = ATTACK;
//
//		if (startAni != playerAction)
//			resetAniTick();

		// fixing
		if (!inAir) {
			if (moving) {
				playerAction = RUN;
			} else {
				playerAction = IDLE;
			}
		}

		if (inAir && !attacking) {
			if (airSpeed < 0)
				playerAction = JUMP;
			else
				playerAction = FALLING;
		}

		if (attacking && !inAir)
			playerAction = ATTACK;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();

		if (!left && !right && !inAir)
			return;

		float xSpeed = 0;

		if (left)
			xSpeed -= playerSpeed;
		if (right && !left)
			xSpeed += playerSpeed;

		if (!inAir)
			if (!isEntityOnFloor(hitbox, mapData))
				inAir = true;

		if (inAir) {
			if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, mapData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;

				// Nhảy lên vẫn cho phép di chuyển sang ngang
				updateXPos(xSpeed);
			} else {
				hitbox.y = getEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed, mapData);
				if (airSpeed > 0)
					// Rơi xuống và chạm đất rồi
					resetInAir();
				else
					// Bay lên nhưng chạm ngay vào roof nên airSpeed sẽ được gán bằng ngay vị trí
					// chạm đó và bắt đầu rơi xuống
					airSpeed = fallSpeedAfterCollision;

				// Nhảy lên vẫn cho phép di chuyển sang ngang
				updateXPos(xSpeed);
			}
		} else {
			updateXPos(xSpeed);
		}
		moving = true;
	}

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, mapData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = getEntityXPosNextToWall(hitbox, xSpeed, mapData);
		}
	}

	private void loadAnimations() {
		BufferedImage img;
		animations = new BufferedImage[7][];
		for (int i = 0; i < 7; i++) {
			switch (i) {
			case IDLE:
				img = getSpriteAtlas(PLAYER_IDLE);
				animations[IDLE] = new BufferedImage[getSpriteAmount(IDLE)];
				for (int j = 0; j < animations[IDLE].length; j++) {
					animations[IDLE][j] = img.getSubimage(j * 128, 0, 128, 128);
				}
				break;
			case RUN:
				img = getSpriteAtlas(PLAYER_RUN);
				animations[RUN] = new BufferedImage[getSpriteAmount(RUN)];
				for (int j = 0; j < animations[RUN].length; j++) {
					animations[RUN][j] = img.getSubimage(j * 128, 0, 128, 128);
				}
				break;
			case JUMP:
				img = getSpriteAtlas(PLAYER_JUMP);
				animations[JUMP] = new BufferedImage[getSpriteAmount(JUMP)];
				for (int j = 0; j < animations[JUMP].length; j++) {
					animations[JUMP][j] = img.getSubimage((j + 2) * 128, 0, 128, 128);

				}
				break;
			case HURT:
				img = getSpriteAtlas(PLAYER_HURT);
				animations[HURT] = new BufferedImage[getSpriteAmount(HURT)];
				for (int j = 0; j < animations[HURT].length; j++) {
					animations[HURT][j] = img.getSubimage(j * 128, 0, 128, 128);
				}
				break;
			case ATTACK:
				img = getSpriteAtlas(PLAYER_ATTACK);
				animations[ATTACK] = new BufferedImage[getSpriteAmount(ATTACK)];
				for (int j = 0; j < animations[ATTACK].length; j++) {
					animations[ATTACK][j] = img.getSubimage(j * 128, 0, 128, 128);
				}
				break;
			case DEAD:
				img = getSpriteAtlas(PLAYER_DEAD);
				animations[DEAD] = new BufferedImage[getSpriteAmount(DEAD)];
				for (int j = 0; j < animations[DEAD].length; j++) {
					animations[DEAD][j] = img.getSubimage(j * 128, 0, 128, 128);
				}
				break;
			case FALLING:
				img = getSpriteAtlas(PLAYER_FALLING);
				animations[FALLING] = new BufferedImage[getSpriteAmount(FALLING)];
				for (int j = 0; j < animations[FALLING].length; j++) {
					animations[FALLING][j] = img.getSubimage((j + 4) * 128, 0, 128, 128);
				}
				break;
			}
		}
	}

	public void loadMapData(int[][] mapData) {
		this.mapData = mapData;
		if (!isEntityOnFloor(hitbox, mapData))
			inAir = true;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
}
