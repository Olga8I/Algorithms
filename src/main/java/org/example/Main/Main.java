package org.example.Main;
import org.example.Algorithms.Algorithms;

public class Main {
    public static void main(String[] args) {
        Integer[][] matrix = {
                {Integer.MAX_VALUE, 5, 8, 12, 3, 8},
                {5, Integer.MAX_VALUE, 5, 11, 7, 10},
                {8, 5, Integer.MAX_VALUE, Integer.MAX_VALUE, 5, 9},
                {12, 11, Integer.MAX_VALUE, Integer.MAX_VALUE, 5, 13},
                {3, 7, 5, 5, Integer.MAX_VALUE, 6},
                {8, 10, 2, 9, Integer.MAX_VALUE, Integer.MAX_VALUE}};


        matrix = Algorithms.floydAlgorithm(matrix);

        System.out.println("Little's algorithm");
        Algorithms.littleAlgorithm(matrix);
    }
}

