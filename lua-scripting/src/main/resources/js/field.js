var softMoney = 10;
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
        buyPrice: 4
    }
};

var seeds = {
    wheat: {
        type: objectTypes.seed,
        name: "wheat",
        buyPrice: 1
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
        return getField();
    }
    if (requestObject.command === commands.buy) {
        return buyField(requestObject.x, requestObject.y);
    }
    if (requestObject.command === commands.sow) {
        return sowField(requestObject.x, requestObject.y, requestObject.seed);
    }
    throw "Request body was not parsed successfully: " + JSON.stringify(requestObject);
}

function getField() {
    return JSON.stringify(farmField);
}

function buyField(x, y) {
    if (farmField[x][y]) {
        throw "Not allowed, cell is not empty, there is a " + JSON.stringify(farmField[x][y]) + " here";
    }
    var field = buildings.field;
    if (softMoney < field.buyPrice) {
        throw "Not enough money to buy " + field.name + ", you have " + softMoney + " and you need " + field.buyPrice;
    }
    softMoney -= field.buyPrice;
    farmField[x][y] = field;
    return getField();
}

function sowField(x, y, seed){
    var field = farmField[x][y];
    if (field.type !== objectTypes.building || field.name !== buildings.field.name) {
        throw "Can't sow: selected cell is not a field";
    }
    if (seed.type !== objectTypes.seed) {
        throw "Object is not a seed";
    }
    if (field.plant) {
        throw "Already sowed with " + JSON.stringify(field.plant);
    }
    field.plant = seed.name;
}