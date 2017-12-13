var commands = {
    get: "GET",
    buy: "BUY",
    sell: "SELL",
    apply: "APPLY"
};

function commandHandler(requestString) {
    var requestObject = JSON.parse(requestString);
    if (requestObject.command === commands.get) {
        return getData();
    }
    if (requestObject.command === commands.buy) {
        shop.buy(requestObject.hand.itemId);
        return getData();
    }
    if (requestObject.command === commands.sell) {
        shop.sell(requestObject.hand.itemId);
        return getData();
    }
    if (requestObject.command === commands.apply) {

    }
    // if (requestObject.command === commands.sow) {
    //     return sowField(requestObject.body.x, requestObject.body.y, requestObject.seed);
    // }
    // if (requestObject.command === commands.reap) {
    //     return reapField(requestObject.body.x, requestObject.body.y);
    // }
    throw "API didn't recognised request: " + JSON.stringify(requestObject);
}

function Utils() {

    this.findInArray = function (array, fun) {
        for (var i = 0; i < array.length; i++) {
            if (fun(array[i])) {
                return array[i];
            }
        }
        return undefined;
    }
}

var utils = new Utils();