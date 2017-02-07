package models.HouseworkTurntable;

/**
 * Created by zhangbojin on 3/02/17.
 */

public class TurntableCountItem {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHouseworkId() {
        return houseworkId;
    }

    public void setHouseworkId(int houseworkId) {
        this.houseworkId = houseworkId;
    }

    public String getHouseworkName() {
        return houseworkName;
    }

    public void setHouseworkName(String houseworkName) {
        this.houseworkName = houseworkName;
    }

    public int getCountNum() {
        return CountNum;
    }

    public void setCountNum(int countNum) {
        CountNum = countNum;
    }

    private int houseworkId;
    private String houseworkName;
    private int CountNum;
}
