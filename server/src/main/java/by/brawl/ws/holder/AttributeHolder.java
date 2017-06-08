package by.brawl.ws.holder;

public class AttributeHolder {
    private Integer max;
    private Integer current;

    public AttributeHolder(Integer bothValues) {
        max = bothValues;
        current = bothValues;
    }

    public AttributeHolder(Integer max, Integer current) {
        this.max = max;
        this.current = current;
    }

    public void incrementCurrent(Integer value) {
        current += value;
        if (current > max) {
            current = max;
        }
    }

    public void decrementCurrent(Integer value) {
        current -= value;
        if (current < 0) {
            current = 0;
        }
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }
}
