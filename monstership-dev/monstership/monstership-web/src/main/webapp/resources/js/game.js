function showMap() {
    var size = 19;
    var table = "<table class='map'>";
    for(var y=0; y < size; y++) {
        table += "<tr>";
        for(var x=0; x < size; x++) {
            if(x == Math.floor(size/2) && y == x) {
                table += "<td onclick='move("+x+","+y+")'>O</td>";
            }
            else {
                table += "<td onclick='move("+x+","+y+")'></td>";
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
        dir = "UP";
    }
    else if (y > 9 && x == 9) {
        dir = "DOWN";
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