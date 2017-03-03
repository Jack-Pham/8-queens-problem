/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author Thong
 */
public class GeneticAlgorithms {
    
    private static Board currentState;
    private static int cost = 0;
    private static Random random = new Random();
    private static int p = 10;
    private static int r = 4;
    
    public static void search(int[] state)
    {
        currentState = new Board(state);
        System.out.println("Start state : ");
        currentState.printState();       
        System.out.println("Heuristic value : " + currentState.hValue());
        System.out.println();
        while (!currentState.isGoal())
        {
            ArrayList<Board> population = currentState.generateSuccessors();
            ArrayList<Board> stateSuccessors = new ArrayList<Board>();
            ArrayList<Board> childSuccessors = new ArrayList<Board>();
            ArrayList<Board> mutatedChildren = new ArrayList<Board>();
            
            for (int i = 0; i < population.size(); i++)
            {                
                if (population.get(i).hValue() > averageFitness(population))
                    stateSuccessors.add(population.get(i));
            }
            
            try
            {
                sort(stateSuccessors);
            }
            catch(IllegalArgumentException e)
            {
                //System.out.printf( "Exception: %s\n\n", e.getMessage() );
            }
            for (int i = 0; i < p; i++)
            {
                int number = 0;
                do
                {
                    number = random.nextInt(stateSuccessors.size() - 1);
                } while(number == i);
                ArrayList<Board> tempSuccessors = crossover(stateSuccessors.get(i),stateSuccessors.get(number));
                childSuccessors.add(tempSuccessors.get(0));
                childSuccessors.add(tempSuccessors.get(1));
            }
            
            for (int i = 0; i < childSuccessors.size(); i++)
            {
                mutatedChildren.add(mutation(childSuccessors.get(i)));
            }
            Board highestState = mutatedChildren.get(0);
            for (int i = 0; i < mutatedChildren.size(); i++)
            {               
                if (mutatedChildren.get(i).hValue() > highestState.hValue())
                    highestState = mutatedChildren.get(i);
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
            System.out.println(currentState.hValue());
            System.out.println("The puzzle cannot be solved");
            System.out.println("Search cost : " + cost);
        }
        
    }
    
    public static int averageFitness(ArrayList<Board> a)
    {
        int average = 0;
        int sum = 0;
        for (int i = 0; i < a.size(); i++)
        {
            sum += a.get(i).hValue();
        }
        average = sum/(a.size());
        return average;
    }
    
    public static void sort(ArrayList<Board> a)
    {
        Collections.sort(a, new SortArray());
    }
    
static class SortArray implements Comparator<Board>{
 
    @Override
    public int compare(Board b1, Board b2) {
        if(b1.hValue() < b2.hValue()){
            return 1;
        } else {
            return -1;
        }
    }
}

    public static ArrayList<Board> crossover(Board b1, Board b2)
    {
        ArrayList<Board> children = new ArrayList<Board>();
        int[] parent1 = b1.getCurrentState();
        int[] parent2 = b2.getCurrentState();
        int[] temp1 = new int[parent1.length-r];
        int[] temp2 = new int[parent2.length-r];
        
        for (int i = r; i < parent1.length; i++)
        {
            temp1[i-r] = parent1[i];
        }
        
        for (int i = r; i < parent2.length; i++)
        {
            temp2[i-r] = parent2[i];
        }
        
        for (int i = r; i < parent1.length; i++)
        {
            parent1[i] = temp2[i-r];
        }
        
        for (int i = r; i < parent2.length; i++)
        {
            parent2[i] = temp1[i-r];
        }
        
        children.add(new Board(parent1));
        children.add(new Board(parent2));
        
        return children;
    }
    
    public static Board mutation(Board b)
    {
        Board mutatedState;
        int[] temp = b.getCurrentState();
        int number1 = random.nextInt(temp.length - 1);
        int number2 = 1 + random.nextInt(temp.length);
        temp[number1] = number2;
        
        return mutatedState = new Board(temp);
    }
}
