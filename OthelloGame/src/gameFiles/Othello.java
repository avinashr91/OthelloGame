package gameFiles;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/*
 * Assumes the user to be Black and the agent to be White. So the user will start the first mover followed by the agent
 * Also assumes the user will always make valid moves
 */

public class Othello {

	 
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		OthelloState state = new OthelloState();
		Scanner s = new Scanner(System.in);
		int x,y; // user Input
		OthelloAgentAlphaBeta alphaBetaagent = new OthelloAgentAlphaBeta(true,2); // Depth of the search
		OthelloUserAgent userAgent = new OthelloUserAgent();
		do // Check if the game has not ended
		{
			// Display the current state in the console:
            
            state.display();
            System.out.println("\nCurrent state, " + OthelloState.PLAYER_NAMES[state.nextPlayerToMove] + " to move:");
         
            // Get the move from the player:
            if(state.nextPlayerToMove == 0) // Represents First player
            {
            	System.out.println("Input the coordinates to move to");
            	x = s.nextInt();
            	y = s.nextInt();
            	
            	OthelloCoordinates coords = userAgent.getMove(state, x, y);
            	// Make a check if the moves are valid. If invalid, prompt the user to input further values until a valid one is found. If no valid passes pass to the next player.
            	while(coords.x == -1)
            	{
            		
            		System.out.println("\nCurrent state, " + OthelloState.PLAYER_NAMES[state.nextPlayerToMove] + " to move:");
            		System.out.println("Invalid Coordinates Please Input valid coords to move to");
                    state.display();
                    
            		x = s.nextInt();
                	y = s.nextInt();
                	coords = userAgent.getMove(state, x, y);
            	}
            	
            	state = state.applyMoveCloning(coords);
            	
            }
            else // Calling the alpha beta agent
            {
            	OthelloCoordinates coords = alphaBetaagent.getMove(state);            
            	System.out.println(coords);
            	state = state.applyMoveCloning(coords); 
            }
		} while(!state.gameOver());
		System.out.println("Game Has Ended with score "+state.score());
		String result = state.score() > 0 ? "You Have won" : "Alpha Beta Agent has won";
		System.out.println(result);
		state.display();
		//ot.displayMatrix();
	}

}
