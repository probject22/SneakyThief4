package core.actions;

/**
 *
 * If an agent consciously decides to do nothing for a period of duration, he should incorporate
 * the wait ActionElement into his Action.
 *
 * Created by Stan on 11/04/15.
 */
public class Wait implements ActionElement {
    private double duration; //in seconds

    public Wait(double duration){
        this.duration = duration;
    }

    public double duration(){
        return duration;
    }
}
