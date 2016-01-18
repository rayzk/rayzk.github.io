package MutualExclusive;

/**
 * Created by RayZK on 17/01/16.
 */
public class MultiPetersonLock implements Lock {

    private int numberOfTheads;
    volatile static private Lock[] locks;
    volatile private static boolean releasing = false;


    public static final int thisThread = 0;
    public static final int otherThreads = 1;

    public MultiPetersonLock(int numberOfThreads) {
        this.numberOfTheads = numberOfThreads;
        locks = new Lock[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            locks[i] = new PetersonLock();
        }
    }

    @Override
    public void RequestCS(int pid) {
        boolean reachHere = false;
        for (int i = 0; i < numberOfTheads; i++) {
            if (i != pid) {
                locks[i].RequestCS(otherThreads);
                while (releasing)
                    reachHere = true;
                if (reachHere)
                    locks[i].RequestCS(otherThreads);
            } else {
                locks[pid].RequestCS(thisThread);
            }
        }
    }

    @Override
    public void ReleaseCS(int pid) {
        releasing = true;
        for (int i = 0; i < numberOfTheads; i++) {
            if (i != pid) {
                locks[i].ReleaseCS(otherThreads);
            }else locks[pid].ReleaseCS(thisThread);
        }
        releasing = false;
    }

}

