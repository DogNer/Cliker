package com.example.clickertwo.CardView;

public class MeModelCard {
    String point;
    String manyClick;
    Boolean special_fich;
    String diffLevel;

    public MeModelCard(String point, String manyClick, Boolean special_fich, String diffLevel) {
        this.point = point;
        this.manyClick = manyClick;
        this.special_fich = special_fich;
        this.diffLevel = diffLevel;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getManyClick() {
        return manyClick;
    }

    public void setManyClick(String manyClick) {
        this.manyClick = manyClick;
    }

    public Boolean getSpecial_fich() {
        return special_fich;
    }

    public void setSpecial_fich(Boolean special_fich) {
        this.special_fich = special_fich;
    }

    public String getDiffLevel() {
        return diffLevel;
    }

    public void setDiffLevel(String diffLevel) {
        this.diffLevel = diffLevel;
    }
}
