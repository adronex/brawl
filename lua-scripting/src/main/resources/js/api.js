var commands = {
    get: "GET",
    buy: "BUY",
    sow: "SOW",
    reap: "REAP",
    sell: "SELL"
};

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