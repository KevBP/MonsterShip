    var starShip;
    var planets;
    var listStarships;
    function init() {
        $.get( "../rest/game/starship", function( data ) {
            starShip = data;
            $.getJSON( "../rest/game/planets", {"x":starShip.xPos-9, "y":starShip.yPos-9}, function( data ) {
                planets = data;
                $.getJSON( "../rest/game/starships", {"x":starShip.xPos-9, "y":starShip.yPos-9}, function( data ) {
                    listStarships = data;
                    showMap(starShip, planets, listStarships);
                });
            });
        });
    }
    function showMap(starShip, planets, listStarships) {
        var size = 19;
        var table = "<table class='map'>";
        for(var y=0; y < size; y++) {
            table += "<tr>";
            for(var x=0; x < size; x++) {
                if(x == Math.floor(size/2) && y == x) {
                    table += "<td onclick='move("+x+","+y+")'>O</td>";
                }
                else if(planets && starShip){
                    var xPosStarShip = starShip.xPos - 9;
                    var yPosStarShip = starShip.yPos - 9;
                    var planetFound = -1;
                    var starshipFound = -1;
                    for (var i = 0; i < planets.length; i++) {
                        if (planets[i].xPos == x+xPosStarShip && planets[i].yPos == y+yPosStarShip) {
                            planetFound = i;
                        }
                    }
                    for (var i = 0; i < listStarships.length; i++) {
                        if (listStarships[i].xPos == x+xPosStarShip && listStarships[i].yPos == y+yPosStarShip) {
                            starshipFound = i;
                        }
                    }
                    if (planetFound != -1) {
                        table += "<td onclick='move("+x+","+y+")' class='planets' style='color:#00F'></td>";
                    }else if (starshipFound != -1) {
                        table += "<td onclick='move("+x+","+y+")' class='planets' style='color:#00F'>X</td>";
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
                showStarshipInformations();
                init();
            });
        }
    }
    showStarshipInformations();
    init();