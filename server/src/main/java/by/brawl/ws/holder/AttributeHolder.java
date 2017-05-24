package by.brawl.ws.holder;

public class AttributeHolder {
    private Integer maxValue;
    private Integer currentValue;

    public AttributeHolder(Integer bothValues) {
        maxValue = bothValues;
        currentValue = bothValues;
    }

    public AttributeHolder(Integer maxValue, Integer currentValue) {
        this.maxValue = maxValue;
        this.currentValue = currentValue;
    }

    public void incrementCurrent(Integer value) {
        currentValue += value;
        if (currentValue > maxValue) {
            currentValue = maxValue;
        }
    }

    public void decrementCurrent(Integer value) {
        currentValue -= value;
        if (currentValue < 0) {
            currentValue = 0;
        }
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }
}
