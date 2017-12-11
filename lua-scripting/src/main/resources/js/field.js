var commands = {
    get: "GET",
    buy: "BUY",
    sow: "SOW",
    reap: "REAP"
};

var objectTypes = {
    building: "building",
    seed: "seed"
};

var buildings = {
    field: {
        type: objectTypes.building,
        name: "field",
        buyPrice: 4,
        currentProductionTimeLeft: 0,
        queue: []
    }
};

var seeds = {
    wheat: {
        type: objectTypes.seed,
        name: "wheat",
        preparationTime: 10000,
        buyPrice: 1,
        harvestValue: 3
    }
};

var bag = {
    softMoney: {
        value: 10
    },
    seeds: {
        wheat: 2
    }
};

var farmField = [];
var FIELD_HEIGHT = 10;
var FIELD_WIDTH = 10;
for (var i = 0; i < FIELD_HEIGHT; i++) {
    farmField.push([]);
    for (var j = 0; j < FIELD_WIDTH; j++) {
        farmField[i].push(undefined);
    }
}

function commandHandler(requestString) {
    var requestObject = JSON.parse(requestString);
    if (requestObject.command === commands.get) {
        return getData();
    }
    if (requestObject.command === commands.buy) {
        return buyField(requestObject.x, requestObject.y);
    }
    if (requestObject.command === commands.sow) {
        return sowField(requestObject.x, requestObject.y, requestObject.seed);
    }
    if (requestObject.command === commands.reap) {
        return reapField(requestObject.x, requestObject.y);
    }
    throw "Request body was not parsed successfully: " + JSON.stringify(requestObject);
}

function getData() {
    updateTimersDeltas();
    return JSON.stringify({bag: bag, field: farmField});
}

function updateTimersDeltas() {
    for (var x = 0; x < farmField.length; x++) {
        for (var y = 0; y < farmField[x].length; y++) {
            if (!farmField[x][y]) {
                continue;
            }
            var delta = farmField[x][y].endTime - new Date().getTime();
            if (delta > 0) {
                farmField[x][y].currentProductionTimeLeft = delta;
            } else {
                farmField[x][y].currentProductionTimeLeft = 0;
            }
        }
    }
}

function buyField(x, y) {
    if (farmField[x][y]) {
        throw "Not allowed, cell is not empty, there is a " + JSON.stringify(farmField[x][y]) + " here";
    }
    var field = buildings.field;
    if (bag.softMoney.value < field.buyPrice) {
        throw "Not enough money to buy " + field.name + ", you have " + bag.softMoney.value + " and you need " + field.buyPrice;
    }
    bag.softMoney.value -= field.buyPrice;
    farmField[x][y] = JSON.parse(JSON.stringify(field));
    return getData();
}

function sowField(x, y, seed){
    var field = farmField[x][y];
    if (!field || field.type !== objectTypes.building || field.name !== buildings.field.name) {
        throw "Can't sow: selected cell is not a field";
    }
    // if (seed.type !== objectTypes.seed) {
    //     throw "Object is not a seed";
    // }
    if (field.queue.length > 0) {
        throw "Already sowed with " + JSON.stringify(field.queue);
    }
    if (bag.seeds.wheat <= 0) {
        throw "There is no available seeds in bag";
    }
    bag.seeds.wheat--;
    seed = seeds.wheat;
    field.queue.push(seed);
    field.endTime = new Date().getTime() + seed.preparationTime;
    return getData();
}

function reapField(x, y) {
    var field = farmField[x][y];
    if (!field || field.type !== objectTypes.building || field.name !== buildings.field.name) {
        throw "Can't sow: selected cell is not a field";
    }
    if (field.queue.length === 0) {
        throw "Production queue is empty";
    }
    if (new Date().getTime() < field.endTime) {
        throw "Field is not ready yet. It will be ready after " + (field.endTime - new Date().getTime()) + " milliseconds";
    }
    var reaped = field.queue.shift();
    field.endTime = undefined;
    bag.seeds.wheat += reaped.harvestValue;
    return getData();
}