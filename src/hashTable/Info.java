package hashTable;

public class Info {
    private int key;
    private int value;

    public Info(int k, int v) {
        key = k;
        value = v;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int k) {
        key = k;
    }
    
    public int getValue() {
        return this.value;
    }

    public void setValue(int v) {
        value = v;
    }
}
