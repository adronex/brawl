var objectTypes = {
    building: "building",
    tool: "tool",
    seed: "seed"
};

var itemTypes = {
    seed: "seed",
    wateringCan: "wateringCan",
};

var buildings = {
    field: {
        type: objectTypes.building,
        name: "field",
        buyPrice: 5,
        sellPrice: 2,
        currentProductionTimeLeft: 0,
        queue: []
    },
    well: {
        type: objectTypes.building,
        name: "well",
        buyPrice: 0,
        sellPrice: 0
    }
};

var items = {
    wateringCan: {
        type: objectTypes.tool,
        name: "wateringCan",
        buyPrice: 0,
        sellPrice: 0
    },
    wheat: {
        type: objectTypes.seed,
        name: "wheat",
        preparationTime: 10000,
        buyPrice: 3,
        sellPrice: 1,
        harvestValue: 3
    },
    carrot: {
        type: objectTypes.seed,
        name: "carrot",
        preparationTime: 50000,
        buyPrice: 15,
        sellPrice: 3,
        harvestValue: 3
    }
};