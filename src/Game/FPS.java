package Game;

public class FPS {
    public static long timePassed;
    public static double fps = 60;
    public static double fpsFactor;
    public void updateTime(long timePassed){
        if(timePassed != 0) {
            this.timePassed = timePassed;
            this.fps = (this.fps*0.9) +(1000/(double)timePassed*0.1);
            this.fpsFactor = timePassed / (double)1000;
        }
    }
}
