function StaticData() {
    var itemTypes = {
        currency: "currency",
        foundation: "foundation",
        building: "building",
        tool: "tool",
        seed: "seed"
    };

    var buildings = {
        ground: {
            id: "ground",
            type: itemTypes.foundation
        },
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

    this.getItemTypes = function () {
        return JSON.parse(JSON.stringify(itemTypes));
    };

    this.getBuildings = function () {
        return JSON.parse(JSON.stringify(buildings));
    };

    this.getItems = function () {
        return JSON.parse(JSON.stringify(items));
    };
}

var staticData = new StaticData();