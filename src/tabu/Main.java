package tabu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String file = "./instances/dane1.txt";
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int numOfCities = 0;
        if (scanner != null) {
            numOfCities = scanner.nextInt();
        }

        int[][] cords = new int[numOfCities][2];
        for (int i=0;i<numOfCities;i++){
            scanner.nextInt();
            cords[i][0]=scanner.nextInt();
            cords[i][1]=scanner.nextInt();
        }
//        for (int i=0;i<numOfCities;i++){
//            System.out.println(cords[i][0]+" "+cords[i][1]);
//        }

        int [][] distances = new int[numOfCities][numOfCities];
        for (int i = 0; i < numOfCities; i++){
            for (int j = 0; j < numOfCities; j++){
                distances[i][j] = (int) Math.sqrt(Math.pow((cords[i][0]-cords[j][0]),2)+ Math.pow((cords[i][1]-cords[j][1]),2));
            }
        }

        Matrix matrix = new Matrix(distances);
//        matrix.printMatrix();
        TabuSearch tabuSearch = new TabuSearch(matrix,distances);
        Timer timer = new Timer();
        tabuSearch.run();
        System.out.println("Time= "+timer.getTime()+"sec");
    }
}
