package com.example.factoryback;

import java.util.List;
import java.util.UUID;

public class StockOption {
    private String id;

    private StockOptions type;

    private List<StockOption> components;

    StockOption(StockOptions type) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
    }

    StockOption(StockOptions type, List<StockOption> components) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.components = components;
    }

}
