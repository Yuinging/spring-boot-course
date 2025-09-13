package top.yyyin.boot.config.enums;

import lombok.Getter;

@Getter
public enum ExpressStatus {
    CREATED("已揽收"),TRANSIT("在途中"),SUCCESS("签收");
    private final String label;

    ExpressStatus(String label){
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

}
