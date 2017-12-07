var softMoney = 10;
var objects = {
    FIELD: "thisIsTheField"
};

var farmField = [];

function buyField(x, y) {
    if (!farmField[x]) {
        farmField[x] = [];
    }
    if (!farmField[x][y]) {
        farmField[x][y] = [];
    }
    if (farmField[x][y].length > 0) {
        throw "Not allowed, cell is not empty, there is a " + farmField[x][y] + " here";
    }
    farmField[x][y] = objects.FIELD;
    return JSON.stringify(farmField);
}