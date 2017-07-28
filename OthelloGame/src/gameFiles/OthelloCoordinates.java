package gameFiles;

public class OthelloCoordinates {
    
    
    int player;
    int x,y;
    
    
    public OthelloCoordinates(int a_player, int a, int b) {
        player = a_player;
        x = a;
        y = b;
    }
    
    
    public String toString() {
        return "Player " + OthelloState.PLAYER_NAMES[player] + " to " + x + ", " + y;
    }
    
    
}