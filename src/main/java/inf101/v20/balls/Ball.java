package inf101.v20.balls;

import java.util.Random;

import javafx.scene.paint.Paint;

/**
 * A class to represent bouncing balls
 * Balls have a size and a color as well as a motion
 * Balls move according to speed and acceleration in both x and y directions.
 * One can set a limit to the movement (like floor ceiling or walls) such that
 * balls will not go outside these limits.
 */
public class Ball{

	/** Color of the ball's surface */
	private Paint color;
	/** The ball's position and speed in x direction. */
	private Motion xMotion;
	/** The ball's position and speed in y direction. */
	private Motion yMotion;
	/** Number of steps taken */
	private int steps = 0;
	/** Describes how well the ball bounces, 
	 * needs to be larger than 0 and should be less than 1 
	 */
	private double bounceFactor = 0.99;
	
	private double radius;

	/**
	 * Create a new ball with position and velocity (0,0)
	 * 
	 * @param color
	 *            The color of the ball
	 * @param radius
	 *            The radius of the ball
	 */
	public Ball(Paint color, double radius) {
		if (radius < 0)
			throw new IllegalArgumentException("Radius should not be negative");
		this.radius = radius;
		this.color = color;
		
		this.xMotion = new Motion();
		this.yMotion = new Motion();
	}

	/**
	 * @return Current X position of the Ball
	 */
	public double getX() {
		return xMotion.getPosition();
	}

	/**
	 * @return Current Y position of the Ball
	 */
	public double getY() {
		return yMotion.getPosition();
	}
	
	/**
	 * @return The ball's radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @return The ball's width (normally 2x {@link #getRadius()})
	 */
	public double getWidth() {
		return 2 * getRadius();
	}

	/**
	 * @return The ball's height (normally 2x {@link #getRadius()})
	 */
	public double getHeight() {
		return 2 * getRadius();
	}

	/**
	 * @return Paint/color for the ball
	 */
	public Paint getColor() {
		return color;
	}

	/**
	 * Number of steps is used to determine the behavior of the ball
	 * 
	 * @return
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * Sets the bounce factor of this ball
	 * bounce factor describes how well a ball bounces
	 * @param bounceFactor
	 */
	public void setBounceFactor(double bounceFactor) {
		this.bounceFactor = bounceFactor;
	}
	
	/**
	 * Move ball to a new position.
	 * 
	 * After calling {@link #moveTo(double, double)}, {@link #getX()} will return
	 * {@code newX} and {@link #getY()} will return {@code newY}.
	 * 
	 * @param newX
	 *            New X position
	 * @param newY
	 *            New Y position
	 */
	public void moveTo(double newX, double newY) {
		xMotion.setPosition(newX);
		yMotion.setPosition(newY);
	}
	
	/**
	 * Returns the speed of the ball which is measured in pixels/move
	 * @return Current X movement
	 */
	public double getDeltaX() {
		return this.xMotion.getSpeed();
	}

	/**
	 * Returns the speed of the ball which is measured in pixels/move
	 * @return Current Y movement
	 */
	public double getDeltaY() {
		return this.yMotion.getSpeed();
	}
	
	/**
	 * Perform one time step.
	 * 
	 * For each time step, the ball's (xPos,yPos) position should change by
	 * (deltaX,deltaY).
	 */
	public void move() {
		xMotion.move();
		yMotion.move();
		steps++;
		//Hint: examine which methods there are in the class Motion
		//      maybe you don't have to do as much as you think.
	}


	
	/**
	 * This method makes one ball explode into 8 smaller balls with half the radius
	 * The new balls may have different speed and direction
	 * @return the new balls after the explosion
	 */
	public Ball[] explode() {
		Random rand = new Random();
		
		Ball[] ballChildren = new Ball[8];
		double newRadius = this.getRadius() / 2;
		
		for (int i = 0; i < 8; i++) {
			Ball ballChild = new Ball(this.color, newRadius);
			ballChild.moveTo(this.getX(), this.getY());
			
			double newSpeedX = rand.nextDouble() + this.getDeltaX();
			double newSpeedY = rand.nextDouble() + this.getDeltaY();
			double newAccelerationX = rand.nextDouble() + xMotion.getAcceleration();
			double newAccelerationY = rand.nextDouble() + yMotion.getAcceleration();

			
			ballChild.setAcceleration(newAccelerationX, newAccelerationY);
			ballChild.setSpeed(newSpeedX, newSpeedY);
			
			ballChildren[i] = ballChild;
		}
		
		return ballChildren;
	}

	/**
	 * Acceleration changes the speed of this ball every time move is called.
	 * This method sets the acceleration in both x and y direction to a given value.
	 * This acceleration is then added every time the move method is called
	 * 
	 * @param xAcceleration The extra speed along the x-axis
	 * @param yAcceleration The extra speed along the y-axis
	 */
	public void setAcceleration(double xAcceleration, double yAcceleration) {
		xMotion.setAcceleration(xAcceleration);
		yMotion.setAcceleration(yAcceleration);
	}

	/**
	 * This method changes the speed of the ball, this is a one time boost to the speed
	 * and will only change the speed, not the acceleration of the ball.
	 * 
	 * @param xAcceleration
	 * @param yAcceleration
	 */
	public void accelerate(double xAcceleration, double yAcceleration) {
		xMotion.accelerate(xAcceleration);
		yMotion.accelerate(yAcceleration);
	}

	/**
	 * Stops the motion of this ball
	 * Both speed and acceleration will be sat to 0
	 */
	public void halt() {
		xMotion.halt();
		yMotion.halt();
		
	}

	/**
	 * Sets the speed of the ball
	 * Note: in BallDemo positive ySpeed is down and negative ySpeed is up
	 * 
	 * @param xSpeed - speed in x direction
	 * @param ySpeed - speed in y direction
	 */
	public void setSpeed(double xSpeed, double ySpeed) {
		xMotion.setSpeed(xSpeed);
		yMotion.setSpeed(ySpeed);
	}

	/**
	 * Sets the lower limit for X values this Ball can have.
	 * If the limit is set the ball will bounce once reaching that limit
	 * @param limit
	 */
	public void setLowerLimitX(double limit) {
		xMotion.setLowerLimit(limit);
	}

	/**
	 * Sets the lower limit for Y values this Ball can have.
	 * If the limit is set the ball will bounce once reaching that limit
	 * @param limit
	 */
	public void setLowerLimitY(double limit) {
		yMotion.setLowerLimit(limit);
	}

	/**
	 * Sets the upper limit for X values this Ball can have.
	 * If the limit is set the ball will bounce once reaching that limit
	 * @param limit
	 */
	public void setUpperLimitX(double limit) {
		xMotion.setUpperLimit(limit);
	}

	/**
	 * Sets the upper limit for Y values this Ball can have.
	 * If the limit is set the ball will bounce once reaching that limit
	 * @param limit
	 */
	public void setUpperLimitY(double limit) {
		yMotion.setUpperLimit(limit);
	}
}
