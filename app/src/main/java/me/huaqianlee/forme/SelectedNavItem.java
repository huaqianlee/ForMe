package me.huaqianlee.forme;

public  class SelectedNavItem {
    private static int slectedNavItem ;
    public final static int TODO = 0;
    public final static int DATEEVENT = 1;


    public static int getSlectedNavItem() {
        return slectedNavItem;
    }

    public static void setSlectedNavItem(int slectedNavItem) {
        SelectedNavItem.slectedNavItem = slectedNavItem;
    }
}
