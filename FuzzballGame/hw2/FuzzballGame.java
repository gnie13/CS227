
package hw2;

/**
 * Models a simplified baseball-like game called Fuzzball.
 * 
 * @author Gavin Nienke
 */
public class FuzzballGame {
  /**
   * Number of strikes causing a player to be out.
   */
  public static final int MAX_STRIKES = 2;

  /**
   * Number of balls causing a player to walk.
   */
  public static final int MAX_BALLS = 5;

  /**
   * Number of outs before the teams switch.
   */
  public static final int MAX_OUTS = 3;
  /**
  * variable that keeps track of the ball count of the player at bat. 
  */
  private int ballCount;
  /**
   * variable that hold the number of strikes that the batter has. 
   */
  private int strikeCount = 0;
  /**
   * variable that holds the current number of outs for the current batting team. 
   */
  private int outCount = 0;
  /**
   * variable that indicates the current inning of the game. 
   */
  private int currentInning = 1;
  /**
   * set equal to the given number of innings in the constructor to keep track of total innings. 
   */
  private int numInnings;
  /**
   * score of first team, initialized to 0.
   */
  private int team0Score = 0;
  /**
   * score of second team, also initialized to 0. 
   */
  private int team1Score = 0;
  /**
   * used in the isTopOfInning method to determine which team is at bat. 
   */
  private int topOfInning = 0;
  /**
   * used to indicate whether the game has ended, initialized to false. 
   */
  private boolean endOfGame = false;
  /**
   * first base, initialized to false to indicate it is empty.
   */
  private boolean firstBase = false;
  /**
   * second base, initialized to false to indicate it is empty.
   */
  private boolean secondBase = false;
  /**
   * third base, initialized to false to indicate it is empty.
   */
  private boolean thirdBase = false;
  
  /**
   * Constructs a game that has the given number of innings and starts at the top of inning 1.
   * @param givenNumInnings max innings set by the user. 
   */
  public FuzzballGame(int givenNumInnings) {

	this.numInnings = givenNumInnings;

}
  /**
   * Checks if the game has ended. 
   * @return true if the game is over, false otherwise
   */
  public boolean gameEnded() {
	  if(currentInning > numInnings) {
		  endOfGame = true;
		  }
		  return endOfGame;
	 }
  
/**
 * indicates a strike for the player at bat. If swung parameter is true, the batter is out. Else, the player is given one called strike. 
 * @param swung is true if the batter swings, false if it is called a strike
 */
  public void strike(boolean swung) {
		if(!endOfGame) {
			if(swung) {
				outCount++;
				switchBatters();
			}else {
				strikeCount++;
				if(strikeCount >= MAX_STRIKES) {
					outCount++;
					switchBatters();
				}
			}
	}if(outCount >= MAX_OUTS) {
		switchTeams();
	}
}
  /**
   * this method switches the team at bat, and increases the innings. 
   */
  private void switchTeams() {
	  if(!endOfGame) {
		  topOfInning = (topOfInning + 1) % 2;
		  if(topOfInning == 0) {
			  currentInning++;
		  }
		  firstBase = false;
		  secondBase = false;
		  thirdBase = false;
		  outCount = 0;
		  switchBatters();
	  }
  }
  /**
   * if 1st team is at bat, then returns true, and false if 2nd team is at bat. 
   * @return true if 1st half of inning, false if not. 
   */
  public boolean isTopOfInning() {
		return topOfInning == 0;
	}
  /**
   * will return true if a runner is on a base when base = 1, 2, or 3. If base doesn't equal these, then returns false. 
   * @param base - checks the base number
   * @return true if runner is on the indicated base. 
   */
  public boolean runnerOnBase(int which) {
	  if(which == 1) {
		  return firstBase;
	  }else if(which == 2){
		  return secondBase;
	  }else if(which == 3) {
		  return thirdBase;
	  }else {
		  return false;
	  }
	}
  /**
   * Method called to indicate a bad pitch at which the batter did not swing. This method adds 1 the the batter's count of balls, possibly resulting in a walk.
   * This method does nothing if the game has ended.
   */
  public void ball() {
		if(!endOfGame) {
			ballCount++;
			//ballCount = (ballCount + 1) % MAX_BALLS;
			if(ballCount == MAX_BALLS) {
				walk();
				switchBatters();
			}
		}
	}
  /**
   * returns outs for the team at bat. 
   * @return number of outs
   */
  public int getCurrentOuts() {
	  return outCount;
	}
  /**
   * returns the strikes called on the player at bat
   * @return current called strikes
   */
  public int getCalledStrikes() {
	  return strikeCount;
	}
  /**
   * returns the count of balls called on the player at bat
   * @return current number of balls
   */
  public int getBallCount() {
	  return ballCount;
	}
  /**
   * returns 1st team score
   * @return score for team0
   */
  public int getTeam0Score() {
	  return team0Score;
    }
  /**
   * returns 2nd team score
   * @return score for team1
   */
  public int getTeam1Score() {
	  return team1Score;
    }
  /**
   * indicates that the batter is struck out because the ball is ca
   */
  public void caughtFly() {
	  strike(true);
  }
  /**
   * Returns the current inning beginning at 1. Returns the total number of innings +1 once the game ends.  
   * @return current inning or the number of innings +1 when the game ends. 
   */
  public int whichInning() {
  	if(currentInning > numInnings) {
  		return numInnings + 1;
  	}else {
  		return currentInning;
  	}
  }
 /**
  * used to fill bases. Once bases are loaded, the walk method will score a point for that team using Single(). Switches batters when done. 
  */
  private void walk() {
	  if(topOfInning == 0) {
		  if (firstBase && secondBase && thirdBase) {
	            Single();
		  } 
		  if(secondBase && firstBase) {
			  firstBase = true;
			  secondBase = true;
			  thirdBase = true;  
		  }
		  if(firstBase) {
			  secondBase = true;
			  firstBase = true;
		  }
		  else {
			  firstBase = true;
		  }
	  }
	  switchBatters();
  }
	  
  /**
   * Method called to indicate that the batter hit the ball. The interpretation of the distance parameter is as follows:
less than 15: the hit is a foul and the batter is immediately out.
at least 15, but less than 150: the hit is a single. An imaginary runner advances to first base, and all other runners on base advance by 1. If there was a runner on third base, the score increases by 1.
at least 150, but less than 200: the hit is a double. An imaginary runner advances to second base, and all other runners on base advance by 2. If there were runners on second or third, the score increases by 1 or 2.
at least 200, but less than 250: the hit is a triple. An imaginary runner advances to third base, and all other runners on base advance by 3. If there were runners on first, second, or third, the score is increased by 1, 2, or 3.
at least 250: the hit is a home run. All imaginary runners currently on base advance to home. The score is increased by 1 plus the number of runners on base.
This method does nothing if the game has ended.
   * @param distance is the distance the ball travels. Can be negative. 
   */
  public void hit(int distance) {
	  if(!endOfGame) {
	  if(distance < 15) {
		  caughtFly();
	  } else if(distance >= 15 && distance < 150) {
		  Single();
	  } else if(distance >= 150 && distance < 200) {
		  Double();
	  } else if(distance >= 200 && distance < 250) {
		  Triple();
	  }else {
		  homeRun();
	  }
	  switchBatters();
  }
 }
/**
 * This method moves the players one base after the single criteria is met. This is also used in Double, Triple, and homeRun, to limit redundancy. 
 */
  private void Single() {
	    if (topOfInning == 0) {
	        if (thirdBase) {
	            team0Score++;
	            thirdBase = false;
	        }
	    } else {
	        if (thirdBase) {
	            team1Score++;
	            thirdBase = false;
	        }
	    }

	    if (secondBase) {
	        thirdBase = true;
	        secondBase = false;
	    }

	    if (firstBase) {
	        secondBase = true;
	    }

	    firstBase = true;
	}
 /**
  * Moves the batter to second base, and moves any players currently on a base. 
  */
	private void Double() {
	  Single();
	  Single();
	  firstBase = false;
	  secondBase = true;
  }
  /**
   * Moves the batter to third, and moves any other player currently on a base. 
   */
  private void Triple() {
	  Double();
	  Single();
	  firstBase = false;
	  secondBase = false;
	  thirdBase = true;
  }
  /**
   * method that empties out all bases since distance in the hit method met homeRun criteria
   */
  private void homeRun() {
	  Double();
	  Double();
	  firstBase = false;
	  secondBase = false;
	  thirdBase = false;
  }
  /**
   * method used to reset the batter's ball and strike values, indicating a new batter is playing. 
   */
  private void switchBatters() {
	  strikeCount = 0;
	  ballCount = 0;
  }
// The methods below are provided for you and you should not modify them.
  // The compile errors will go away after you have written stubs for the
  // rest of the API methods.
  /**
   * Returns a three-character string representing the players on base, in the
   * order first, second, and third, where 'X' indicates a player is present and
   * 'o' indicates no player. For example, the string "oXX" means that there are
   * players on second and third but not on first.
   * 
   * @return three-character string showing players on base
   */
  public String getBases()
  {
    return (runnerOnBase(1) ? "X" : "o") + (runnerOnBase(2) ? "X" : "o")
        + (runnerOnBase(3) ? "X" : "o");
  }

  /**
   * Returns a one-line string representation of the current game state. The
   * format is:
   * <pre>
   *      ooo Inning:1 [T] Score:0-0 Balls:0 Strikes:0 Outs:0
   * </pre>
   * The first three characters represent the players on base as returned by the
   * <code>getBases()</code> method. The 'T' after the inning number indicates
   * it's the top of the inning, and a 'B' would indicate the bottom. The score always
   * shows team 0 first.
   * 
   * @return a Single line string representation of the state of the game
   */
  public String toString()
  {
    String bases = getBases();
    String topOrBottom = (isTopOfInning() ? "T" : "B");
    String fmt = "%s Inning:%d [%s] Score:%d-%d Balls:%d Strikes:%d Outs:%d";
    return String.format(fmt, bases, whichInning(), topOrBottom, getTeam0Score(),
        getTeam1Score(), getBallCount(), getCalledStrikes(), getCurrentOuts());
  }
}
