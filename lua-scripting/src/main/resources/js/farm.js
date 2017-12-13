var farm = [];
var FIELD_HEIGHT = 10;
var FIELD_WIDTH = 10;
for (var i = 0; i < FIELD_HEIGHT; i++) {
    farm.push([]);
    for (var j = 0; j < FIELD_WIDTH; j++) {
        farm[i].push(staticData.getBuildings().ground);
    }
}

function getData() {
    updateTimersDeltas();
    return JSON.stringify({bag: bag.getCopyOfAllItems(), farm: farm, shop: shop.getCopyOfAllItems()});
}

function updateTimersDeltas() {
    for (var x = 0; x < farm.length; x++) {
        for (var y = 0; y < farm[x].length; y++) {
            if (!farm[x][y]) {
                continue;
            }
            var delta = farm[x][y].endTime - new Date().getTime();
            if (delta > 0) {
                farm[x][y].currentProductionTimeLeft = delta;
            } else {
                farm[x][y].currentProductionTimeLeft = 0;
            }
        }
    }
}

function applyHandToCell(x, y, hand) {

}

function buyField(x, y) {
    if (farm[x][y]) {
        throw "Not allowed, cell is not empty, there is a " + JSON.stringify(farm[x][y]) + " here";
    }
    var field = buildings.field;
    bag.decreaseCount(staticData.getItems().softMoney.id, field.buyPrice);
    farm[x][y] = JSON.parse(JSON.stringify(field));
    return getData();
}

function sowField(x, y, seed){
    var field = farm[x][y];
    if (!field || field.type !== staticData.getBuildings().field.type || field.id !== staticData.getBuildings().field.id) {
        throw "Can't sow: selected cell is not a field";
    }
    // if (seed.type !== itemTypes.seed) {
    //     throw "Object is not a seed";
    // }
    if (field.queue.length > 0) {
        throw "Already sowed with " + JSON.stringify(field.queue);
    }
    bag.decreaseCount(staticData.getItems().wheat.id, 1);
    seed = staticData.getItems().wheat;
    field.queue.push(seed);
    field.endTime = new Date().getTime() + seed.preparationTime;
    return getData();
}

function reapField(x, y) {
    var field = farm[x][y];
    if (!field || field.type !== staticData.getBuildings().field.type || field.id !== staticData.getBuildings().field.id) {
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
    field.currentProductionTimeLeft = undefined;
    bag.increaseCount(staticData.getItems().wheat.id, reaped.harvestValue);
    return getData();
}