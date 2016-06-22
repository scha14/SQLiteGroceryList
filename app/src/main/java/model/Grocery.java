package model;

/**
 * Created by Sukriti on 6/16/16.
 */
public class Grocery {

    private long id;
    private String item;
    private int checked;
    private boolean isVisited;


    public boolean getIsVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public long getId() {return id;}

    public void setId(long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }



    public Grocery() {}

    public Grocery(long id, String item, int checked){
        setId(id);
        setItem(item);
        setChecked(checked);
    }


}
