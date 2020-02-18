package tabu;

public class TabuList {
    int [][] tabuList;

    public  TabuList(int numOfCities){
        tabuList = new int[numOfCities][numOfCities];
    }

    public void addTabu(int x, int y, int term){
        tabuList[x][y] += term;
        tabuList[y][x] += term;
    }

    public void printTabu(){
        for (int[] ints : tabuList) {
            for (int j = 0; j < tabuList.length; j++) System.out.print(ints[j] + " ");
            System.out.println();
        }
    }

    public void decrementTabu(){
        for (int i = 0; i < tabuList.length; i++) {
            for (int j = 0; j < tabuList.length; j++) {
                if(tabuList[i][j]>0) tabuList[i][j]--;
            }
        }
    }
}
