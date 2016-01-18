package MutualExclusive;

/**
 * Created by RayZK on 18/01/16.
 *
 * Mutual Exclusion Algorithm for N process using Peterson's ALgo as black box.
 * This binary tree implementation works.
 */
public class MultiPetersonTreeLock implements Lock {


    private int numberOfProcess;
    private volatile Lock[] locks;

    public MultiPetersonTreeLock(int numberOfProcess) {
        this.numberOfProcess = numberOfProcess;
        this.locks = new Lock[numberOfProcess];
        for (int i = 1; i < numberOfProcess; i++) {
            locks[i] = new PetersonLock();
        }
    }

    @Override
    public void RequestCS(int pid) {
        if (pid < numberOfProcess)
            pid += numberOfProcess;
        request(pid);
    }
    private void request(int pid) {
        int lockIndex = getParent(pid);
        if (lockIndex == 0)
            return;
        locks[lockIndex].RequestCS((isLeftChild(lockIndex, pid)) ? 0 : 1);
        request(lockIndex);
    }

    @Override
    public void ReleaseCS(int pid){
        if (pid < numberOfProcess)
            pid += numberOfProcess;
        release(pid);
    }

    private void release(int pid) {
        int lockIndex = getParent(pid);
        if (lockIndex == 0)
            return;
        release(lockIndex);
        locks[lockIndex].ReleaseCS((isLeftChild(lockIndex, pid)) ? 0 : 1);
    }

    // Helper Methods
    private int getParent(int index) {
        return index / 2;
    }

    private boolean isLeftChild(int parentIndex, int childIndex) {
        if ((childIndex / 2) != parentIndex) return false;
        else if ((childIndex % 2) != 0) return false;
        return true;
    }


}
