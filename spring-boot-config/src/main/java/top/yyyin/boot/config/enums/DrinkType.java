package top.yyyin.boot.config.enums;

public enum DrinkType {
    COFFEE("咖啡", 12.0), TEA("奶茶", 10.0), JUICE("果汁", 8.0);

    private final String label;
    private final double price;

    DrinkType(String label, double price) {
        this.label = label;
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public double getPrice() {
        return price;
    }
}