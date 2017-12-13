var farm = [];
var FIELD_HEIGHT = 10;
var FIELD_WIDTH = 10;
for (var i = 0; i < FIELD_HEIGHT; i++) {
    farm.push([]);
    for (var j = 0; j < FIELD_WIDTH; j++) {
        farm[i].push(staticData.getItems().ground);
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

function applyHandToCell(hand, target) {
    if (!hand || !hand.id) {
        if (!hand) hand = {};
        throw "Invalid object in hand: " + JSON.stringify(hand);
    }
    if (!target || target.x === undefined || target.y === undefined) {
        if (!target) target = {};
        throw "Invalid target object: " + JSON.stringify(target);
    }
    if (bag.getOrCreate(hand.id).count <= 0) {
        throw "Not enough '" + hand.id + "' items to apply them on '" + farm[target.x][target.y].itemId +"'";
    }
    var item = staticData.getItems()[hand.id];
    farm[target.x][target.y] = item.use(farm[target.x][target.y]);
    bag.decreaseCount(hand.id, 1);
}