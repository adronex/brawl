var itemTypes = {
    currency: "currency",
    building: "building",
    tool: "tool",
    seed: "seed"
};

var buildings = {
    field: {
        id: "field",
        type: itemTypes.building,
        buyPrice: 5,
        sellPrice: 2,
        currentProductionTimeLeft: 0,
        queue: []
    },
    well: {
        id: "well",
        type: itemTypes.building,
        buyPrice: 0,
        sellPrice: 0
    }
};

var items = {
    softMoney: {
        id: "softMoney",
        type: itemTypes.currency
    },
    wateringCan: {
        id: "wateringCan",
        type: itemTypes.tool
    },
    wheat: {
        id: "wheat",
        type: itemTypes.seed,
        preparationTime: 10000,
        harvestValue: 3
    },
    carrot: {
        id: "carrot",
        type: itemTypes.seed,
        preparationTime: 50000,
        harvestValue: 3
    }
};