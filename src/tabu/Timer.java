package tabu;

public class Timer {
    private Long startTime;

    public Timer(){
        startTime = System.nanoTime();
    }

    public Long getTime(){
        return System.nanoTime() - startTime;
    }
}
