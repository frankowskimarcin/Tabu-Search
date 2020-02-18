package tabu;

import java.util.stream.IntStream;

public class Matrix {
    private int [][] matrix;
    private int edgesNum;

    public Matrix(int[][] matrix){
        edgesNum = matrix.length;
        this.matrix=matrix;

    }

    public int getEdges(){
        return edgesNum;
    }

    public void printMatrix(){
        for(int i=0;i<getEdges();i++){
            for(int j=0; j<getEdges();j++) System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    public int pathCost(int[] solution){
        return IntStream.range(0, (solution.length - 1)).map(i -> matrix[solution[i]][solution[i + 1]]).sum();
    }
}
