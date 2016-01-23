function showMap() {
    var size = 19;
    var table = "<table class='map'>";
    //get starship coordinates
    var xPosStarShip;
    var yPosStarShip;
    $.get( "../rest/game/starship", function( data ) {
        xPosStarShip = data.xPos - 9;
        yPosStarShip= data.yPos - 9;
    });
    //get list of planets to display with case en haut a gauche (je sais pas le dire en anglais et la flemme de chercher)
    var coordX = [];
    var coordY = [];
    $.getJSON( "../rest/game/planets", function( data ) {
        for (var i = 0; i < data.length; i++) {
            coordX.push(data[i].xPos);
            coordY.push(data[i].yPos);
        }
    });
    
    alert(coordX.length);
    alert(coordX.length);
    
    for(var y=0; y < size; y++) {
        table += "<tr>";
        for(var x=0; x < size; x++) {
            if(x == Math.floor(size/2) && y == x) {
                table += "<td onclick='move("+x+","+y+")'>O</td>";
            }
            else {
                var planetFound = 0;
                for (var i = 0; i < coordX.length; i++) {
                    if (coordX[i] == x+xPosStarShip && coordY[i] == y+yPosStarShip) {
                        planetFound = 1;
                    }
                }
                if (planetFound == 1) {
                    table += "<td onclick='move("+x+","+y+")'>P</td>";
                }else {
                    table += "<td onclick='move("+x+","+y+")'></td>";
                }
            }
        }
        table += "</tr>";
    }
    table += "</table>";
    document.getElementById('showMap').innerHTML = table;
}

function showStarshipInformations() {
    $.getJSON( "../rest/game/starship", function( data ) {
        document.getElementById('showStarshipPosition').innerHTML = "Position ["+data.xPos+","+data.yPos+"]<br/>Action points: "+data.actionPoint;
    });
}

function move(x, y) {
    var dir = null;
    if (y < 9 && x == 9) {
        dir = "DOWN";
    }
    else if (y > 9 && x == 9) {
        dir = "UP";
    }
    else if (x < 9 && y == 9) {
        dir = "LEFT";
    }
    else if (x > 9 && y == 9) {
        dir = "RIGHT";
    }
    else {
        // not allowed
    }

    if (dir != null) {
        $.get( "../rest/game/move/" + dir, function( data ) {
        });
        showStarshipInformations();
        showMap();
    }
}
showStarshipInformations();
showMap();