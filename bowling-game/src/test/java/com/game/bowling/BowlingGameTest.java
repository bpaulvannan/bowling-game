package com.game.bowling;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BowlingGameTest {
	private static final int[] GAME_OVER = new int[21];
	private static final int[] GAME_ALL_ZERO = new int[20];
	private static final int[] GAME_ALL_ONE = {
		1,1, //Frame 01
		1,1, //Frame 02
		1,1, //Frame 03
		1,1, //Frame 04
		1,1, //Frame 05
		1,1, //Frame 06
		1,1, //Frame 07
		1,1, //Frame 08
		1,1, //Frame 09
		1,1  //Frame 10
	};
	private static final int[] GAME_ALL_STRIKES = {
		10, //Frame 01
		10, //Frame 02
		10, //Frame 03
		10, //Frame 04
		10, //Frame 05
		10, //Frame 06
		10, //Frame 07
		10, //Frame 08
		10, //Frame 09
		10,10,10  //Frame 10
	};
	private static final int[] GAME_ALL_SPARES = {
		5,5, //Frame 01
		5,5, //Frame 02
		5,5, //Frame 03
		5,5, //Frame 04
		5,5, //Frame 05
		5,5, //Frame 06
		5,5, //Frame 07	
		5,5, //Frame 08
		5,5, //Frame 09
		5,5,5  //Frame 10
	};
	
	private static final int[] GAME_MIXED = {
		5,5, //Frame 01
		10,  //Frame 02
		1,1, //Frame 03
		2,2, //Frame 04
		0,0, //Frame 05
		5,5, //Frame 06
		8,2, //Frame 07	
		0,10, //Frame 08
		5,0, //Frame 09
		5,0  //Frame 10
	};
	
	private static final int[] GAME_TYPICAL = {
		1,4, //Frame 01
		4,5,  //Frame 02
		6,4, //Frame 03
		5,5, //Frame 04
		10, //Frame 05
		0,1, //Frame 06
		7,3, //Frame 07	
		6,4, //Frame 08
		10, //Frame 09
		2,8,6  //Frame 10
	};
	
	private void rollPins(BowlingGame game, int... pinsToKnock){
		for(int pins : pinsToKnock){
			game.roll(pins);
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidFirstRoll(){
		BowlingGame game = new BowlingGame();
		rollPins(game, new int[]{20});
	}
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidSecondRoll(){
		BowlingGame game = new BowlingGame();
		rollPins(game, new int[]{5,6});
	}
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidSecondRollInLastFrame(){
		BowlingGame game = new BowlingGame();
		int[] rolls = new int[21];
		rolls[19] = 10;
		rolls[20] = 12;
		rollPins(game, rolls);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidThirdRollInLastFrame(){
		BowlingGame game = new BowlingGame();
		int[] rolls = new int[21];
		rolls[19] = 10;
		rolls[20] = 10;
		rolls[20] = 12;
		rollPins(game, rolls);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testGameOver(){
		BowlingGame game = new BowlingGame();
		rollPins(game, GAME_OVER);
	}
	@Test(expected=IllegalStateException.class)
	public void testGameOverAllStrikes(){
		BowlingGame game = new BowlingGame();
		rollPins(game, GAME_ALL_STRIKES);
		rollPins(game, new int[]{5});
	}
	
	@Test
	public void testAllZero(){
		BowlingGame game = new BowlingGame();
		rollPins(game, GAME_ALL_ZERO);
	}
	

	@Test
	public void testAllOne(){
		BowlingGame game = new BowlingGame();
		rollPins(game, GAME_ALL_ONE);
		assertEquals(20, game.calculateScore());
	}
	
	@Test
	public void testAllStrikes(){
		BowlingGame game = new BowlingGame();
		rollPins(game, GAME_ALL_STRIKES);
		assertEquals(300, game.calculateScore());
	}
	
	@Test
	public void testAllSpare(){
		BowlingGame game = new BowlingGame();
		rollPins(game, GAME_ALL_SPARES);
		assertEquals(150, game.calculateScore());
	}
	
	@Test
	public void testMixedGame(){
		BowlingGame game = new BowlingGame();
		rollPins(game, GAME_MIXED);
		assertEquals(91, game.calculateScore());
	}

	@Test
	public void testTypicalGame(){
		BowlingGame game = new BowlingGame();
		rollPins(game, GAME_TYPICAL);
		assertEquals(133, game.calculateScore());
	}
}
