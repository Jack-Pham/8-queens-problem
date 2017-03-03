/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;

/**
 *
 * @author Thong
 */
public class Board {
    
    private final int BSIZE = 8;
    private char[][] board = new char[BSIZE][BSIZE];
    private int[] currentState;
    private int hValue;
    
    public Board(int state[])
    {
        currentState = state;
        createBoard();
        nonAttacking();
    }
    
    public void createBoard()
    {
        for (int i = 0; i < BSIZE; i++)
        {
            board[BSIZE - currentState[i]][i] = 'Q';
        }
    }
    
    public void printState()
    {
        for (int i = 0; i < BSIZE; i++)
        {
            for (int j = 0; j < BSIZE; j++)
            {
                if (board[i][j] == 'Q')
                    System.out.printf(" %s |",board[i][j]);
                else
                    System.out.printf(" %s |",' ');
            }
            System.out.println();
            System.out.println("--------------------------------------");
        }
    }
    
    public int checkAttack(int row, int column)
    {
        int count = 0;
        for (int i = column + 1; i < BSIZE; i++)
        {
            if (board[row][i] == 'Q')
                count++;
        }
        int j = 1;
        int k = 1;
        for (int i = column + 1; i < BSIZE; i++)
        {
            if (((row - j >= 0) && (board[row - j][i] == 'Q')) || ((row + k < BSIZE) && (board[row + k][i] == 'Q')))
                count++;
            j++;
            k++;
        }
        
        return count;
    }
    
    public int calculateAttack()
    {
        int totalAttack = 0;
        for (int i = 0; i < BSIZE; i++)
        {
            totalAttack += checkAttack(BSIZE - currentState[i],i);
        }
        return totalAttack;
    }
    
    public ArrayList<Board> generateSuccessors()
    {
        ArrayList<Board> successors = new ArrayList<Board>();
        
        for (int i = 0; i < 8; i ++)
        {
            int[] copy = new int[BSIZE];
            int memory = currentState[i];
            for (int j = 1; j <= 8; j++)
            {
                if (j != currentState[i])
                    copy[j-1] = j;
            }
            
            for (int k = 0; k < copy.length; k++)
            {
                if (copy[k] != 0)
                {
                    currentState[i] = copy[k];
                    int[] tempState = copyState(currentState);
                    successors.add(new Board(tempState));                             
                }
            }                     
            currentState[i] = memory;
        }        
        return successors;
    }
    
    private int[] copyState(int[] state)
	{
		int[] board = new int[BSIZE];
		for (int i = 0; i < BSIZE; i++)
		{
			board[i] = state[i];
		}
		return board;
	}
    
    public void nonAttacking()
    {
        hValue = 28 - calculateAttack();
    }
    
    public int hValue()
    {
        return hValue;
    }
    
    public boolean isGoal()
    {
        return hValue() == 28;
    }
    
    public int[] getCurrentState()
    {
        return currentState;
    }
}
