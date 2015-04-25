package dfc9ed.cs2110.virginia.edu.ghosthunter2;

import android.graphics.Rect;
import android.util.Log;

public class Player extends Entity {
	private static final String TAG = Player.class.getSimpleName();

	private int money;

	public Player(int x, int y, int health, GameView game) {
		super(x, y, game);
		this.setSprite(R.drawable.no_armor_no_weapon);
		this.money = 0;
		this.speed = 16;
		this.maxHealth = GameView.INITIAL_HEALTH;
		this.health = health;
		this.attack = 5;

		this.attackDist = 0;
		this.attackCooldown = 0;
		game.activity.displayHealth(health);
		game.activity.displayMoney(money);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public boolean attack() {
		if (!super.attack()) return false;
		return true;
	}

	@Override
	public boolean canAttack() {
		return super.canAttack()
	}

	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		game.activity.displayHealth(health);
	}

	public void action() {
		Log.d(TAG, "Action");
		Rect rect = getActionRect();
		for (NPC npc : game.getNPCs()) {
			if (npc.getBoundingRect().intersect(rect)) {
				npc.interact();
				return;
			}
		}
	}

	@Override
	public void despawn() {
		super.despawn();
		//game.activity.gameOver();
	}

	public void moveUp() {
		ySpeed = -speed;
		setDirection(UP);
		setAnim(WALKING);
	}
	
	public void moveDown() {
		ySpeed = speed;
		setDirection(DOWN);
		setAnim(WALKING);
	}
	
	public void moveLeft() {
		xSpeed = -speed;
		setDirection(LEFT);
		setAnim(WALKING);
	}
	
	public void moveRight() {
		xSpeed = speed;
		setDirection(RIGHT);
		setAnim(WALKING);
	}
	
	public void stopUp() {
		ySpeed = 0;
		setAnim(STANDING);
	}
	
	public void stopDown() {
		ySpeed = 0;
		setAnim(STANDING);
	}
	
	public void stopLeft() {
		xSpeed = 0;
		setAnim(STANDING);
	}
	
	public void stopRight() {
		xSpeed = 0;
		setAnim(STANDING);
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int amount) {
		money += amount;
		game.activity.displayMoney(money);
	}

	public void subtractMoney(int amount) {
		money -= amount;
		game.activity.displayMoney(money);
	}

}
