package MutualExclusive;

/**
 * Created by RayZK on 17/01/16.
 */
public class BakeryLock implements Lock {

    int N;
    private volatile boolean[] choosing;
    private volatile int[] number;

    // Constructor
    public BakeryLock (int numProc){
        N = numProc;
        choosing = new boolean[N];
        number = new int[N];
        for (int j=0; j<N; j++){
            choosing[j] = false;
            number[j] = 0;
        }
    }

    // Helper method
    private boolean smaller (int i, int numi, int j, int numj){
        if (numi < numj) return true;
        else if (numi == numj)
            if (i < j) return true;
            else return false;
        return false;
    }

    public void RequestCS (int pid){
        // choose a number
        choosing[pid] = true;
        for (int j=0; j<N; j++){
            if (number[j] > number[pid])
                number[pid] = number[j];
        }
        number[pid]++;
        choosing[pid] = false;

        // check
        for (int j=0; j<N; j++){
            while (choosing[j]);
            while (number[j] != 0 && smaller(j, number[j], pid, number[pid]));
        }
    }

    public void ReleaseCS (int pid){
        number[pid] = 0;
    }
}