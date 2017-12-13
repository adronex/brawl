function StaticData() {
    var itemTypes = {
        currency: "currency",
        foundation: "foundation",
        building: "building",
        tool: "tool",
        seed: "seed"
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
        sickle: {
            id: "sickle",
            type: itemTypes.tool,
            use: function (target) {
                if (!target || target.id !== items.field.id) {
                    var targetString = target ? JSON.stringify(target) : undefined;
                    throw "Can't apply 'sickle', invalid target: " + targetString;
                }
                if (target.queue.length === 0) {
                    throw "Production queue is empty";
                }
                if (new Date().getTime() < target.endTime) {
                    throw "Field is not ready yet. It will be ready after " + (target.endTime - new Date().getTime()) + " milliseconds";
                }
                var reaped = target.queue.shift();
                target.endTime = undefined;
                target.currentProductionTimeLeft = undefined;
                bag.increaseCount(reaped.id, reaped.harvestValue);
                return utils.copy(target);
            }
        },
        wheat: {
            id: "wheat",
            type: itemTypes.seed,
            preparationTime: 10000,
            harvestValue: 3,
            use: function (target) {
                if (!target || target.id !== items.field.id) {
                    var targetString = target ? JSON.stringify(target) : undefined;
                    throw "Can't apply 'wheat', invalid target: " + targetString;
                }
                if (target.queue.length > 0) {
                    throw "Already sowed with " + JSON.stringify(target.queue);
                }
                target.queue.push(utils.copy(items.wheat));
                target.endTime = new Date().getTime() + items.wheat.preparationTime;
                return utils.copy(target);
            }
        },
        carrot: {
            id: "carrot",
            type: itemTypes.seed,
            preparationTime: 50000,
            harvestValue: 3
        },
        ground: {
            id: "ground",
            type: itemTypes.foundation
        },
        field: {
            id: "field",
            type: itemTypes.building,
            queue: [],
            use: function (target) {
                if (!target || target.id !== items.ground.id) {
                    var targetString = target ? JSON.stringify(target) : undefined;
                    throw "Can't apply 'field', invalid target: " + targetString;
                }
                return utils.copy(items.field);
            }
        },
        well: {
            id: "well",
            type: itemTypes.building
        }
    };

    this.getItemTypes = function () {
        return utils.copy(itemTypes);
    };

    this.getItems = function () {
        return utils.copy(items);
    };
}

var staticData = new StaticData();