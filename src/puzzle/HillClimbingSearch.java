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
public class HillClimbingSearch {
    
    public static int h;
    private static Board currentState;
    public static int cost = 0;
    
    public static void search(int[] state)
    {
        currentState = new Board(state);
        System.out.println("Start state : ");
        currentState.printState();      
        System.out.println("Heuristic value : " + currentState.hValue());
        System.out.println();
        while (!currentState.isGoal())
        {
            ArrayList<Board> tempSuccessors = currentState.generateSuccessors();
            ArrayList<Board> stateSuccessors = new ArrayList<Board>();

            for (int i = 0; i < tempSuccessors.size(); i++)
            {                
                if (tempSuccessors.get(i).hValue() > currentState.hValue())
                    stateSuccessors.add(tempSuccessors.get(i));
            }
            
            if (stateSuccessors.size() == 0)
                break;
            
            Board highestState = stateSuccessors.get(0);
            for (int i = 0; i < stateSuccessors.size(); i++)
            {               
                if (stateSuccessors.get(i).hValue() > highestState.hValue())
                    highestState = stateSuccessors.get(i);
            }
            //highestState.printState();
            //System.out.println("Heuristic value : " + highestState.hValue());           
            currentState = highestState;
            cost++;
        }
        
        if (currentState.isGoal())
        {
            System.out.println("Final State : ");
            currentState.printState();
            System.out.println("Heuristic value : " + currentState.hValue());
            System.out.println("The puzzle has been solved");
            System.out.println("Search cost : " + cost);
        }
        else
        {
            System.out.println("Unsolvable State : ");
            currentState.printState();
            System.out.println("Heuristic value : " + currentState.hValue());
            System.out.println("The puzzle cannot be solved");
            System.out.println("Search cost : " + cost);
        }
        
    }
    
}
