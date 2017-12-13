function Shop() {
    var shopItems = [
        {
            itemId: staticData.getBuildings().field.id,
            buyPrice: 4,
            sellPrice: 1
        },
        {
            itemId: staticData.getBuildings().well.id,
            buyPrice: 0,
            sellPrice: 0
        },
        {
            itemId: staticData.getItems().wateringCan.id,
            buyPrice: 0,
            sellPrice: 0
        },
        {
            itemId: staticData.getItems().wheat.id,
            buyPrice: 3,
            sellPrice: 1
        },
        {
            itemId: staticData.getItems().carrot.id,
            buyPrice: 15,
            sellPrice: 3
        }
    ];
    var findShopItem = function (itemId) {
        return utils.findInArray(shopItems, function (it) {
            return it.itemId === itemId;
        });
    };

    this.getCopy = function (itemId) {
        var item = findShopItem(itemId);
        if (!item) {
            return undefined;
        }
        return JSON.parse(JSON.stringify(item));
    };

    this.getCopyOfAllItems = function() {
        return JSON.parse(JSON.stringify(shopItems));
    };
    
    this.buy = function (itemId) {
        var item = findShopItem(itemId);
        if (!item) {
            throw "There is no shop item with id " + itemId;
        }
        // reduce count in shop if needed
        bag.decreaseCount(staticData.getItems().softMoney.id, item.buyPrice);
        bag.increaseCount(item.itemId, 1);
    };

    this.sell = function (itemId) {
        var count = bag.getCount(itemId);
        if (count === 0) {
            throw "Bag doesn't contain enough items with id " + itemId;
        }
        var item = findShopItem(itemId);
        if (!item) {
            throw "You can't sell item with id " + itemId + "because shop doesn't contain it";
        }
        // reduce count in shop if needed
        bag.decreaseCount(item.itemId, 1);
        bag.increaseCount(staticData.getItems().softMoney.id, item.sellPrice);
    };
}

var shop = new Shop();