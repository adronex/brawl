function Bag() {
    var tools = {wateringCan: 0};

    var bagItems = [
        {
            itemId: items.softMoney.id,
            count: 10
        },
        {
            itemId: items.wheat.id,
            count: 2
        }
    ];

    var getOrCreate = function (itemId) {
        var found = utils.findInArray(bagItems, function (it) {
            return it.itemId === itemId;
        });
        if (!found) {
            found = {itemId: itemId, count: 0};
            bagItems.push(found);
        }
        return found;
    };

    this.getCount = function (itemId) {
        return getOrCreate(itemId).count;
    };

    this.decreaseCount = function (itemId, count) {
        var found = getOrCreate(itemId);
        if (found.count < count) {
            throw "Not enough '" + itemId + "', need: " + count + ", available: " + found.count;
        }
        found.count -= count;
    };

    this.increaseCount = function (itemId, count) {
        getOrCreate(itemId).count += count;
    };
}

var bag = new Bag();


