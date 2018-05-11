package org.seckill.entity;

public class Box {
    private int boxId;
    private String boxName;

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    @Override
    public String toString() {
        return "Box{" +
                "boxId=" + boxId +
                ", boxName='" + boxName + '\'' +
                '}';
    }
}
