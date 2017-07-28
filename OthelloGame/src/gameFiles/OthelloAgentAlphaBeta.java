package gameFiles;


import java.util.List;

public class OthelloAgentAlphaBeta
{
	private boolean isMax;
	private int depth;
	private OthelloEvaluator evalMetric = new OthelloEvaluator();

	public OthelloAgentAlphaBeta(boolean isMax, int depth)
	{
		this.isMax = isMax;
		this.depth = depth;
	}

	

	public OthelloCoordinates getMove(OthelloState state)
	{
		List<OthelloCoordinates> coords = state.generateMoves();
		if (coords.isEmpty())
		{
			return null;
		}

		//treat the call to getMove as the "first call" to alphabeta, though here, find the bestMove
		//instead of just the best score
		int depth_curr = depth - 1;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
	
		//assume the first move is the best one first
		OthelloCoordinates bestMove = coords.get(0);
		int bestScore = alphabeta(state.applyMoveCloning(bestMove), !isMax, depth_curr, alpha, beta);
		if (isMax)
		{
			//update alpha based on the bestScore for the first move
			alpha = max(alpha, bestScore);
			//note that loop is exited if beta ever becomes <= alpha
			for (int i = 1; i < coords.size() && beta > alpha; i++)
			{
				OthelloCoordinates currMove = coords.get(i);
				int nextScore = alphabeta(state.applyMoveCloning(currMove), false, depth_curr, alpha, beta);
				if (nextScore > bestScore)
				{
					bestScore = nextScore;
					bestMove = currMove;
				}
				alpha = max(alpha, bestScore);
			}
		}
		else
		{
			//update best based on the bestScore for the first move
			beta = min(beta, bestScore);
			//note that loop exits if beta ever becomes <= than alpha
			for (int i = 1; i < coords.size() && beta > alpha; i++)
			{
				OthelloCoordinates currMove = coords.get(i);
				int nextScore = alphabeta(state.applyMoveCloning(currMove), true, depth_curr, alpha, beta);
				if (nextScore < bestScore)
				{
					bestScore = nextScore;
					bestMove = currMove;
				}
				beta = min(beta, bestScore);
			}
		}

		return bestMove;
	}

	private int alphabeta(OthelloState state, boolean maxPlayer, int depth, int alpha, int beta)
	{
		//Leaf node - return alpha beta value
		if (depth <= 0 || state.gameOver())
		{
			return evalMetric.score(state);
		}

		depth--;
		List<OthelloCoordinates> coords = state.generateMoves();

		//if no moves available, then only move is to pass, so return that as the alphabeta value
		if (coords.isEmpty())
		{
			return alphabeta(state.applyMoveCloning(null), !maxPlayer, depth, alpha, beta);
		}

		//otherwise, check each possible move and determine the highest/lowest possible value
		int bestScore = maxPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		//note that loop exits if beta ever becomes <= alpha
		for (int i = 0; i < coords.size() && beta > alpha; i++)
		{
			int nextScore = alphabeta(state.applyMoveCloning(coords.get(i)), !maxPlayer, depth, alpha, beta);
			if (maxPlayer)
			{
				bestScore = max(bestScore, nextScore);
				alpha = max(alpha, bestScore);
			}
			else
			{
				bestScore = min(bestScore, nextScore);
				beta = min(beta, bestScore);
			}
		}
		
		return bestScore;
	}

	private int min(int a, int b)
	{
		return a < b ? a : b;
	}

	private int max(int a, int b)
	{
		return a > b ? a : b;
	}
}
