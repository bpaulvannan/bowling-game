package com.game.bowling;

public class BowlingGame {
	public static final int MAX_PINS = 10;
	public static final int MAX_FRAMES = 10;
	public static final int MAX_ROLLS = 21;
	
	private final int[] rolls = new int[21];
	private int curRollNo = 0;
	private int curFrameNo = 0;
	private int frameRollCount = 0;
	private int curFrameFirstRollNo = 0;

	/**
	 * Roll the ball and knock pins
	 * @param knockedPins
	 */
	public void roll(int knockedPins){
		validateFrame(knockedPins);		

		rolls[curRollNo] = knockedPins;
		curRollNo = curRollNo + 1;
		
		processFrame();
	}
	
	/**
	 * Validate frame count and knocked pins in frame
	 * @param knockedPins
	 */
	private void validateFrame(int knockedPins){
		if(curFrameNo >= MAX_FRAMES){
			throw new IllegalStateException("Invalid Roll. All frames are bowled.");
		}
		if(knockedPins < 0 || knockedPins > MAX_PINS){
			throw new IllegalArgumentException("Invalid number of knocked pins " + knockedPins);
		}
		if(curRollNo > curFrameFirstRollNo){
			validateKnockedPins(curFrameFirstRollNo, knockedPins);
			if(isLastFrame(curFrameNo) && !isSpare(curFrameFirstRollNo)){
				validateKnockedPins(curFrameFirstRollNo+1, knockedPins);
			}
		}
	}
	
	/**
	 * Validate knocked pins in current roll
	 * @param rollNo
	 * @param knockedPins
	 */
	private void validateKnockedPins(int rollNo, int knockedPins){
		if(!isStrike(rollNo) && rolls[rollNo] + knockedPins > MAX_PINS){
			throw new IllegalArgumentException("Invalid number of knocked pins " + knockedPins);
		}
	}
	
	/**
	 * Track frame roll count and frame no
	 */
	private void processFrame(){
		frameRollCount = frameRollCount + 1;
		boolean addFrame = false;
		if(isLastFrame(curFrameNo)){
			//IF last frame has strike or spare, check for 3 rolls
			if((isStrike(curFrameFirstRollNo) || isSpare(curFrameFirstRollNo))){
				addFrame = frameRollCount == 3;
			}else{
				addFrame = frameRollCount == 2;
			}
		}else{
			//Not Last Frame -> if strike or roll count = 2, addFrame
			addFrame = isStrike(curRollNo-1) || frameRollCount == 2;
		}
		if(addFrame){
			curFrameFirstRollNo = curRollNo;
			curFrameNo = curFrameNo + 1;
			frameRollCount = 0;
		}
	}
	
	/**
	 * Calculate score
	 * @return
	 */
	public int calculateScore(){
		int score = 0;
		int rollNo = 0;
		for(int i=0; i<MAX_FRAMES;i++){
			if(isStrike(rollNo)){
				score = score + MAX_PINS + getStrikeBonus(rollNo);
				rollNo = rollNo + 1;
			}else if(isSpare(rollNo)){
				score = score + MAX_PINS + getSpareBonus(rollNo);
				rollNo = rollNo + 2;
			}else{
				score = score + getFrameScore(rollNo);
				rollNo = rollNo + 2;
			}
			if(rollNo >= curRollNo){}
		}
		return score;
	}
	
	private boolean isLastFrame(int frameNo){
		return frameNo == MAX_FRAMES - 1;
	}
	
	private boolean isStrike(int rollNo){
		return rolls[rollNo] == MAX_PINS;
	}
	
	private boolean isSpare(int rollNo){
		return rolls[rollNo] + rolls[rollNo+1] == MAX_PINS;
	}
	
	private int getStrikeBonus(int rollNo){
		return rolls[rollNo+1] + rolls[rollNo+2];
	}
	
	private int getSpareBonus(int rollNo){
		return rolls[rollNo+2];
	}
	
	private int getFrameScore(int rollNo){
		return rolls[rollNo] + rolls[rollNo+1]; 
	}
}
