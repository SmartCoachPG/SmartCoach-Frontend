package model.Admi;

public class GimnasioItem {

    private int gimnasioid;
    private int itemid;
    private int cantidad;

    public GimnasioItem(int gimnasioid, int itemid, int cantidad) {
        this.gimnasioid = gimnasioid;
        this.itemid = itemid;
        this.cantidad = cantidad;
    }

    public GimnasioItem() {}

    public int getGimnasioid() {
        return gimnasioid;
    }

    public void setGimnasioid(int gimnasioid) {
        this.gimnasioid = gimnasioid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "GimnasioItem{" +
                "gimnasioid=" + gimnasioid +
                ", itemid=" + itemid +
                ", cantidad=" + cantidad +
                '}';
    }
}
