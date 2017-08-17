package me.huaqianlee.forme;

public  class SelectedNavItem {
    private static int slectedNavItem ;
    private static int TODO = 0;


    public static int getSlectedNavItem() {
        return slectedNavItem;
    }

    public static void setSlectedNavItem(int slectedNavItem) {
        SelectedNavItem.slectedNavItem = slectedNavItem;
    }
}
