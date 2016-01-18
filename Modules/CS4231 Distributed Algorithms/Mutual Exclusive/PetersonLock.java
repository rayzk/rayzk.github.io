package MutualExclusive;

/**
 * Created by RayZK on 17/01/16.
 *
 * Peterson's Algorithm
 */
public class PetersonLock implements Lock {

    private volatile boolean wantCS[] = {false, false};
    private volatile int turn = 1;

    // only accept pid = 1 or 0
    public void RequestCS (int pid){
        int j = 1 - pid;
        wantCS[pid] = true;
        turn = j;

        while (turn == j && wantCS[j] == true);
    }

    public void ReleaseCS (int pid){
        wantCS[pid] = false;
    }
}