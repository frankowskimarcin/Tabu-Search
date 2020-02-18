package tabu;

import java.util.Random;

public class TabuSearch {
    private Matrix matrix;
    private TabuList tabuList;

    int numOfIterations;
    int size;
    private int[][] distances;

    private int[] path;
    private int[] bestPath;
    private int bestCost;
    private int lossCounter = 0;

    public TabuSearch(Matrix matrix, int[][] distances){
        this.matrix = matrix;
        this.distances = distances;
        numOfIterations = 1000;
        size = matrix.getEdges();
        tabuList = new TabuList(size);
        bestPath = new int[size+1];

        greedy();
        System.arraycopy(path,0, bestPath,0,bestPath.length);
        bestCost = matrix.pathCost(bestPath);
//        printBestPath();
        System.out.println("Greedy cost: "+getBestCost());
    }

    public void run(){
        int iterator = 0;
        long endTime = 180000000000L; //180s
        Timer timer = new Timer();
        while(timer.getTime() < endTime){
            if(lossCounter > 10000){
                createRandomPath();
                lossCounter = 0;
            }

            int localCost = matrix.pathCost(path);
            int city1 = 0, city2 = 0;
            if(localCost < bestCost){
                System.arraycopy(path,0, bestPath,0,bestPath.length);
                bestCost = localCost;
            }

            for (int i = 1; i < path.length-1 ; i++){
                for (int j = 2; j < path.length-1 ; j++){
                    if(i != j){
                        swap(i,j);
                        int cost = matrix.pathCost(path);
                        if(cost < localCost){
                            localCost = cost;
                            if (cost < bestCost && tabuList.tabuList[i][j] == 0 || cost < 0.95 * bestCost){
                                city1=i; city2=j;
                                System.arraycopy(path,0, bestPath,0,bestPath.length);
                                bestCost = cost;
                                System.out.println("New solution with iteration number: "+iterator+"\nTime: "+(float)timer.getTime()/600000000L+" Cost: "+bestCost);
//                                printBestPath();
                                System.out.println();
                            }
                        }
                        else{
                            lossCounter++;
                            swap(j,i);
                        }
                    }
                }
            }
            if(city1 != 0){
                tabuList.decrementTabu();
                tabuList.addTabu(city1, city2, size/2);
//                System.out.println();
//                System.out.println(city1+" "+city2);
//                tabuList.printTabu();
//                System.out.println();
            }
            iterator++;
        }
//        printBestPath();
        System.out.println("Best solution");
        System.out.println("Iteration number: "+iterator+"\nCost: "+getBestCost()+"\nPath: ");
    }

    public void swap(int a, int b){
        int temp = path[a];
        path[a] = path[b];
        path[b] = temp;
    }

    public void createRandomPath(){
        path = new int[size+1];
        for(int i = 0; i < size; i++) path[i] = i;
        path[size] = 0;
        Random random = new Random();
        int temp, rand;
        for(int num = size-1; num >1 ; num--){
            rand = random.nextInt(num);
            temp = path[num];
            path[num] = path[rand];
            path[rand] = temp;
        }
        for(int i = 0; i < size; i++){
            if(path[i]==0) path[i] = path[0];
        }
        path[0] = 0;
        path[size] = 0;
    }

    public void greedy(){
        int now = 0, next = 0;
        path = new int[size+1];
        boolean[] visited = new boolean[size];
        visited[0] = true;
        for(int i = 0; i < size; i++){
            int minDistance=999999;
            for(int j = 0; j < size; j++){
                if(visited[j]) continue;
                if(distances[now][j]<=minDistance){
                    minDistance = distances[now][j];
                    next=j;
                }
            }
            path[i]=now;
            now = next;
            visited[next] = true;
        }
        path[size]=0;
    }

    public void printBestPath() {
        for(int i = 0; i < size+1; i++){
            System.out.println(bestPath[i]);
        }
    }

    public int getBestCost() {
        return bestCost;
    }
}