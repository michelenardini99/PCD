package pcd.lab01.ex01;

import java.util.Arrays;

public class SortThread extends Thread{

    private int[] v;
    private final int start;
    private final int end;

    public SortThread(String myName, int[] v, int start, int end){
        super(myName);
        this.v = v;
        this.start = start;
        this.end = end;
    }

    public void run(){
        log("Start to sort thread array");
        Arrays.sort(v, start, end);
        log("Start to sort thread array");
    }

    private void log(String msg) {
        System.out.println("[ " + System.currentTimeMillis() +   " ][ " + getName()+ " ] " + msg);
    }

}
