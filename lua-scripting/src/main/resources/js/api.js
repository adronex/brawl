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
        shop.buy(requestObject.hand.id);
        return getData();
    }
    if (requestObject.command === commands.sell) {
        shop.sell(requestObject.hand.id);
        return getData();
    }
    if (requestObject.command === commands.apply) {
        applyHandToCell(requestObject.hand, requestObject.target);
        return getData();
    }
    throw "API didn't recognised request: " + JSON.stringify(requestObject);
}

function Utils() {

    // ES6 Array.find substitution
    this.findInArray = function (array, fun) {
        for (var i = 0; i < array.length; i++) {
            if (fun(array[i])) {
                return array[i];
            }
        }
        return undefined;
    };

    // ES6 Array.map substitution
    this.mapArray = function (source, fun) {
        var target = [];
        for (var i = 0; i < source.length; i++) {
            target.push(fun(source[i]));
        }
        return target;
    };

    // ES6 Object.assign substitution
    this.copy = function (obj) {
        if (null === obj || "object" !== typeof obj) return obj;
        var out, v, key;
        out = Array.isArray(obj) ? [] : {};
        for (key in obj) {
            v = obj[key];
            out[key] = (typeof v === "object") ? this.copy(v) : v;
        }
        return out;
    };
}

var utils = new Utils();