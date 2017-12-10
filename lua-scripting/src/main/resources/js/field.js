var softMoney = 10;
var objects = {
    field: {
        name: "thisIsTheField",
        buyPrice: 4
    }
};
var commands = {
    get: "GET",
    buy: "BUY"
};

var farmField = [];
var FIELD_HEIGHT = 10;
var FIELD_WIDTH = 10;
for (var i = 0; i < FIELD_HEIGHT; i++) {
    farmField.push([]);
    for (var j = 0; j < FIELD_WIDTH; j++) {
        farmField[i].push({});
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
    throw "Request body was not parsed successfully: " + JSON.stringify(requestObject);
}

function getField() {
    return JSON.stringify(farmField);
}

function buyField(x, y) {
    if (farmField[x][y].length > 0) {
        throw "Not allowed, cell is not empty, there is a " + farmField[x][y] + " here";
    }
    softMoney -= objects.field.buyPrice;
    farmField[x][y] = objects.field;
    return getField();
}