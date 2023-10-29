package model.Admi;

import java.io.Serializable;

public class UbicacionxItem {

    private long id;
    private int mapaid;
    private int coordenadaX;
    private int coordenadaY;
    private int itemid;
    private int gimnasioid;

    public UbicacionxItem(long id, int mapaid, int coordenadaX, int coordenadaY, int itemid, int gimnasioid) {
        this.id = id;
        this.mapaid = mapaid;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.itemid = itemid;
        this.gimnasioid = gimnasioid;
    }

    public UbicacionxItem()
    {

    }

    public int getMapaid() {
        return mapaid;
    }

    public void setMapaid(int mapaid) {
        this.mapaid = mapaid;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getGimnasioid() {
        return gimnasioid;
    }

    public void setGimnasioid(int gimnasioid) {
        this.gimnasioid = gimnasioid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UbicacionxItem{" +
                "id="+id+
                ", mapaid=" + mapaid +
                ", coordenadaX=" + coordenadaX +
                ", coordenadaY=" + coordenadaY +
                ", itemid=" + itemid +
                ", gimnasioid=" + gimnasioid +
                '}';
    }
}
