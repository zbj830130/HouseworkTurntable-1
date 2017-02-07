package models.HouseworkTurntable;

/**
 * Created by zhangbojin on 1/02/17.
 */

/**
 * Created by zhangbojin on 31/01/17.
 */

public class HouseworkItem {
    private int mId;
    private String mName;
    private boolean mIsSelected;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isSelected(){
        return mIsSelected;
    }

    public void setSelected(boolean selected){
        mIsSelected = selected;
    }
}
