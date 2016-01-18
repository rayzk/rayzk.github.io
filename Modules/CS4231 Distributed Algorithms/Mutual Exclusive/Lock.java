package MutualExclusive;

/**
 * Created by RayZK on 17/01/16.
 */
public interface Lock {

    public void RequestCS (int pid);
    public void ReleaseCS (int pid);

}
