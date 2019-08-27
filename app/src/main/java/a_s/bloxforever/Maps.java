package a_s.bloxforever;

/**
 * Created by Ameer on 5/5/2015.
 */
public class Maps {
    private String map;
    private int num;
    private boolean Canplay;
    public Maps(String map,int num,boolean Canplay){
        setNum(num);
        setMap(map);
        setCanplay(Canplay);
    }

    public boolean Canplay() {
        return Canplay;
    }

    public void setCanplay(boolean played) {
        this.Canplay = played;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMap() {
        return map;
    }
}
