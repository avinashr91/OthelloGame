package gameFiles;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OthelloState {
    
	int boardSize = 8;
    int board[][] = new int[8][8];
    public static final int EMPTY = -1;
    public static final int PLAYER1 = 0; // Represents Black Coin
    public static final int PLAYER2 = 1; // Represents White Coin
    public static final String PLAYER_NAMES[] = {"B","W"};
    int nextPlayerToMove = PLAYER1;
    // 8 Possible directions for a piece
    public static final int allDirection_x[] = { 0, 1, 1, 1, 0,-1,-1,-1};
    public static final int allDirection_y[] = {-1,-1, 0, 1, 1, 1, 0,-1};

    
    /*
     * Constructor Othello State - Sets the board basics for Othelllo  
    */
    public OthelloState() {
    	
    	for(int i =0; i < 8; i++)
		{
			for(int j =0; j < 8; j++)
			{
				board[i][j]= EMPTY; // represents empty cell
			}
		}
    	board[3][4] = PLAYER2; // represents black coin
    	board[3][3] = PLAYER1; // represents white coin
    	board[4][3] = PLAYER2; // represents black coin
    	board[4][4] = PLAYER1; // represents white coin
    }
    
    
    /*
     * Converts a game board to a string, for displaying it via the console    
     */
    public void display() 
    {
    	for(int i=0; i<8; i++)
    	{
    		for(int j=0;j<8;j++)
    		{
    			if(board[i][j] == -1)
    			{
    				System.out.print("X");
    			}
    			else
    			{
    				System.out.print(PLAYER_NAMES[board[i][j]]);
    			}
    			System.out.print("\t");
    		}
    		System.out.println("");
    	}
    }
    
    
    /*
     * Makes a copy of a game state
     */
    public OthelloState clone() 
    {
        OthelloState cloneState = new OthelloState();
        for(int i=0; i<8; i++)
        {
        	for(int j =0; j<8; j++)
        	{
        		cloneState.board[i][j] = board[i][j];
        	}
        }
        cloneState.nextPlayerToMove = nextPlayerToMove;
        return cloneState;
    }
    
    
    /*
     * Determines whether the game is over or not
    */
    
    public boolean gameOver() 
    {
        if (generateMoves(PLAYER1).isEmpty() && generateMoves(PLAYER2).isEmpty()) return true;
        return false;
    }
    
    
    /*
     * Returns the final score, once a game is over - Can implement a better evaluation function considering the edges and corners.
     */
    
    public int score() 
    {
        int score = 0;
        for(int i = 0;i<8;i++)
        {
            for(int j = 0;j<8;j++) 
            {
                if (board[i][j]==PLAYER1) score++;
                if (board[i][j]==PLAYER2) score--;
            }
        }
        return score;
    }
    
    
    /*
     * Returns the list of possible moves for the next player to move
     */
    public List<OthelloCoordinates> generateMoves() {
        return generateMoves(nextPlayerToMove);
    }
    
    
    /*
     * Returns the list of possible moves for player 
     */
    public List<OthelloCoordinates> generateMoves(int player) {
        List<OthelloCoordinates> coords = new LinkedList<OthelloCoordinates>();

      
        for(int i = 0;i<8;i++) 
        {
            for(int j = 0;j<8;j++) 
            {
                if (board[i][j]==EMPTY) 
                {
                    boolean moveFound = false;
                    for(int k = 0;k<allDirection_x.length && !moveFound;k++) 
                    {
                        int current_x = i + allDirection_x[k];
                        int current_y = j + allDirection_y[k];
                        while(current_x+allDirection_x[k]>=0 && current_x+allDirection_x[k]<8 &&
                              current_y+allDirection_y[k]>=0 && current_y+allDirection_y[k]<8 &&
                              board[current_x][current_y] == otherPlayer(player)) 
                        {
                            current_x += allDirection_x[k];
                            current_y += allDirection_y[k];
                            if (board[current_x][current_y] == player) 
                            {
                                // Legal move:
                                moveFound = true;
                                coords.add(new OthelloCoordinates(player, i, j));
                                break;
                            }
                        }
                    }
                }
            }
        }
                
        return coords;
    }
    
    
    /*
     * Modifies the game state as for applying the given 'move'
     * Notice that move can be "null", which means that the player passes.
     * "passing" is only allowed if a player has no other moves available.
     */
    public void applyMove(OthelloCoordinates coords) 
    {
        nextPlayerToMove = otherPlayer(nextPlayerToMove);
        
        if (coords==null) 
        {
        	System.out.println("Passing to other player ");
        	return; // player passes
        }
        // set the piece:
        board[coords.x][coords.y] = coords.player;
        
        // these two arrays encode the 8 possible directions in which a player can capture pieces:
        int allDirection_x[] = { 0, 1, 1, 1, 0,-1,-1,-1};
        int allDirection_y[] = {-1,-1, 0, 1, 1, 1, 0,-1};
        
        // see if any pieces are captured:
        for(int i = 0;i<allDirection_x.length;i++) {
            int current_x = coords.x + allDirection_x[i];
            int current_y = coords.y + allDirection_y[i];
            while(current_x+allDirection_x[i]>=0 && current_x+allDirection_x[i]<boardSize &&
                  current_y+allDirection_y[i]>=0 && current_y+allDirection_y[i]<boardSize &&
                  board[current_x][current_y] == otherPlayer(coords.player)) 
            {
                current_x += allDirection_x[i];
                current_y += allDirection_y[i];
                if (board[current_x][current_y] == coords.player) {
                    // pieces captured!:
                    int reversed_x = coords.x + allDirection_x[i];
                    int reversed_y = coords.y + allDirection_y[i];
                    while(reversed_x!=current_x || reversed_y!=current_y) 
                    {
                        board[reversed_x][reversed_y] = coords.player;
                        reversed_x += allDirection_x[i];
                        reversed_y += allDirection_y[i];
                    }
                    break;
                }
            }
        }
    } 
    
    
    /*
     * Creates a new game state that has the result of applying move 'move'
     */
    public OthelloState applyMoveCloning(OthelloCoordinates coord) {
        OthelloState newState = clone();
        newState.applyMove(coord);
        return newState;
    }
    
    public int otherPlayer(int player) 
    {
        if (player==PLAYER1) return PLAYER2;
        return PLAYER1;
    }

	public int nextPlayer() 
	{
		return nextPlayerToMove;
	}
}
