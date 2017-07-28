package gameFiles;
import java.util.List;



public class OthelloUserAgent 
{
	public OthelloCoordinates getMove(OthelloState state,int x, int y) 
	{
        // generate the list of moves:
        List<OthelloCoordinates> coords = state.generateMoves();           
        
        
        int i = 0;
        boolean foundValid = false;
        if (!coords.isEmpty()) 
        {
        	OthelloCoordinates coord = new OthelloCoordinates(OthelloState.PLAYER1,-1,-1);
        	OthelloCoordinates validcoord = null;
        	while(i < coords.size())
        	{
        		validcoord = coords.get(i);
        		//System.out.println(validcoord.x + " " + validcoord.y);
        		if(validcoord.x == x && validcoord.y == y)
        		{
        			foundValid = true;
        			break;
        		}
        		i++;
        	}
        	return  foundValid == true ? validcoord : coord;
        }
        
        // If there are no possible moves, just return "pass":
        return null;
    }
}
