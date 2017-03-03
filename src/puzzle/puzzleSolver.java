package puzzle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author Thong
 */
public class puzzleSolver {
    
    private static int[] state = new int[8];
    private static Random random = new Random();
    private static int choice;
    private static long startTime, endTime, time = 0;

    public static void main(String[] args) {
        
        for (int i = 0; i < state.length; i++)
        {
            int randomNumber = 1 + random.nextInt(8);
            state[i] = randomNumber;
        }
        System.out.print("8-queen problem : ");
        for (int a : state)
            System.out.print(a);
        System.out.println();
        Scanner input = new Scanner(System.in);
        System.out.print("What algorithm you want to implement ?\n" + "1. Hill Climbing\n" + 
                "2. Genetic Algorithm\n");
        choice = input.nextInt();
        switch (choice) {
            case 1:
            {
                startTime = System.currentTimeMillis();
                HillClimbingSearch.search(state);
                endTime = System.currentTimeMillis();
                time = endTime - startTime;                
                System.out.println("Time : " + time);
                break;
            }
            case 2:
            {
                startTime = System.currentTimeMillis();
                GeneticAlgorithms.search(state);
                endTime = System.currentTimeMillis();
                time = endTime - startTime;                
                System.out.println("Time : " + time);
                break;
            }
            default:
                System.out.println("Exit");
                break;
        }
    }
    
}
