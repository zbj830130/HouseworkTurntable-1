package models.HouseworkTurntable;

import java.io.Serializable;

/**
 * Created by zhangbojin on 1/02/17.
 */

public class HouseworkItem implements Serializable{
    private int mId;
    private String mName;
    private boolean mIsSelected;

    public HouseworkItem(){

    }

    public HouseworkItem(String name, boolean isSelected){
        mName = name;
        mIsSelected = isSelected;
    }

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
