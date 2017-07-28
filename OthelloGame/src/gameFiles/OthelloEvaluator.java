package gameFiles;

public class OthelloEvaluator
{
	private static final int CORNER_SCORE = 4;
	private static final int SIDE_SCORE = 1;
	private static final int SIDE_ADJACENT_CORNER = 2;
	private static final int INNER_CORNER = 3;

	
	
	public int score(OthelloState state)
	{
		int board[][] = state.board;
		int totalScore = 0;

		totalScore += considerCorner(board, 0, 0);
		totalScore += considerCorner(board, 0, board.length - 1);
		totalScore += considerCorner(board, board.length - 1, 0);
		totalScore += considerCorner(board, board.length - 1, board.length - 1);

		totalScore += considerRow(board, 0);
		totalScore += considerRow(board, board.length - 1);
		totalScore += considerCol(board, 0);
		totalScore += considerCol(board, board.length- 1);

		totalScore += considerInnerCorner(board, 0, 0, 1, 1);
		totalScore += considerInnerCorner(board, 0, board.length - 1, 1, board.length - 2);
		totalScore += considerInnerCorner(board, board.length - 1, 0, board.length - 2, 1);
		totalScore += considerInnerCorner(board, board.length - 1, board.length - 1, board.length - 2, board.length - 2);

		return totalScore;
	}

	private int considerCorner(int board[][], int row, int col)
	{
		//add extra points for holding a corner, because these pieces are stable and can never be
		//captured
		if (board[row][col] == OthelloState.PLAYER1)
		{
			return CORNER_SCORE;
		}

		if (board[row][col] == OthelloState.PLAYER2)
		{
			return -CORNER_SCORE;
		}

		return 0;
	}

	private int considerRow(int board[][], int row)
	{
		int localScore = 0;
		int start = 1, end = board.length - 1;

		//if there is nothing in the corner yet, it is very bad to have a piece in one of the
		//adjacent spots. Therefore, subtract points if this is the case
		if (board[row][0] == OthelloState.EMPTY)
		{
			start++;
			if (board[row][1] == OthelloState.PLAYER1)
			{
				localScore += -SIDE_ADJACENT_CORNER;
			}
			else if (board[row][1] == OthelloState.PLAYER2)
			{
				localScore += SIDE_ADJACENT_CORNER;
			}
		}

		if (board[row][board.length - 1] == OthelloState.EMPTY)
		{
			end--;
			if (board[row][board.length - 2] == OthelloState.PLAYER1)
			{
				localScore += -SIDE_ADJACENT_CORNER;
			}
			else if (board[row][board.length - 2] == OthelloState.PLAYER2)
			{
				localScore += SIDE_ADJACENT_CORNER;
			}
		}

		//finally, after handling above cases, grant additional points for holding the sides,
		//because these positions are relatively stable
		for (int i = start; i < end; i++)
		{
			if (board[row][i] == OthelloState.PLAYER1)
			{
				localScore += SIDE_SCORE;
			}
			else if (board[row][i] == OthelloState.PLAYER2)
			{
				localScore += -SIDE_SCORE;
			}
		}

		return localScore;
	}

	private int considerCol(int board[][], int col)
	{
		int localScore = 0;
		int start = 1, end = board.length - 1;
		//if there is nothing in the corner yet, it is very bad to have a piece in one of the
		//adjacent spots. Therefore, subtract points if this is the case
		if (board[0][col] == OthelloState.EMPTY)
		{
			start++;
			if (board[1][col] == OthelloState.PLAYER1)
			{
				localScore += -SIDE_ADJACENT_CORNER;
			}
			else if (board[1][col] == OthelloState.PLAYER2)
			{
				localScore += SIDE_ADJACENT_CORNER;
			}
		}

		if (board[board.length - 1][col] == OthelloState.EMPTY)
		{
			end--;
			if (board[board.length - 2][col] == OthelloState.PLAYER1)
			{
				localScore += -SIDE_ADJACENT_CORNER;
			}
			else if (board[board.length - 2][col] == OthelloState.PLAYER2)
			{
				localScore += SIDE_ADJACENT_CORNER;
			}
		}

		//finally, after handling above cases, grant additional points for holding the sides,
		//because these positions are relatively stable
		for (int i = start; i < end; i++)
		{
			if (board[i][col] == OthelloState.PLAYER1)
			{
				localScore += SIDE_SCORE;
			}
			else if (board[i][col] == OthelloState.PLAYER2)
			{
				localScore += -SIDE_SCORE;
			}
		}

		return localScore;
	}

	private int considerInnerCorner(int board[][], int corner_row, int corner_col, int row, int col)
	{
		if (board[corner_row][corner_col] == OthelloState.EMPTY)
		{
			if (board[row][col] == OthelloState.PLAYER1)
			{
				return -INNER_CORNER;
			}
			
			if (board[row][col] == OthelloState.PLAYER2)
			{
				return INNER_CORNER;
			}
		}

		return 0;
	}
}
