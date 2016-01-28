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
                updateActionForm();
            });
        });
    });
}

function getPlanetAtStarshipPosition(starShip, planets) {
    for (var i = 0; i < planets.length; i++) {
        var planet = planets[i];
        if (planet.xPos == starShip.xPos && planet.yPos == starShip.yPos) {
            return planet;
        }
    }
    return null;
}

function getStarshipAtStarshipPosition(starShip, planets) {
    var ret = [];
    for (var i = 0; i < listStarships.length; i++) {
        var otherStarShip = listStarships[i];
        if (otherStarShip.xPos == starShip.xPos && otherStarShip.yPos == starShip.yPos) {
            ret.push(otherStarShip);
        }
    }
    return otherStarShip;
}

function updateViews() {
    init();
    updateActionForm();
    showStarshipInformations();
}
function updateActionForm() {
    var $action = $("#action");
    $action.html("");
    if (starShip && planets && listStarships) {
        if (getPlanetAtStarshipPosition(starShip, planets)) {
            $action.append("<button>Harvest</button>");
        }
        var starship = getStarshipAtStarshipPosition(starShip, listStarships);
        if (starship && starship.length > 0) {
            $action.append("<button>Attack !</button>");
        }
    }

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
                    table += "<td onclick='move("+x+","+y+")' class='planets' style='color:#ae3414'>p</td>";
                }else if (starshipFound != -1) {
                    table += "<td onclick='move("+x+","+y+")' class='planets' style='color:#a7ff48'>X</td>";
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
        document.getElementById('showStarshipPosition').innerHTML = "Position ["+data.xPos+","+data.yPos+"]<br/>Monster crew: "+data.monsterCount+"<br/>Action points: "+data.actionPoint;
    });
}

function moveDir(dir) {
    if (dir){
        return $.get( "../rest/game/move/" + dir, function( starShip ) {
            updateViews();
            $("#showMap").focus();
        });
    }
}

function move(x,y) {
    var dir = computeMove(x,y);
    console.log(dir+", starship ["+starShip.xPos+","+starShip.yPos+"]");
    return moveDir(dir);
}

function computeMove(x, y) {
    if (starShip) {
        var dir = null;
        if (y < 9 && x == 9 && starShip.yPos > 0) {
            dir = "DOWN";
        }
        else if (y > 9 && x == 9 && starShip.yPos < starShip.game.height) {
            dir = "UP";
        }
        else if (x < 9 && y == 9 && starShip.xPos > 0) {
            dir = "LEFT";
        }
        else if (x > 9 && y == 9 && starShip.xPos < starShip.game.width) {
            dir = "RIGHT";
        }
        else {
            // not allowed
        }

        return dir;
    }
}

function setupKeyBinding() {
    $(document).keydown(function (event) {
        if (event.which == 40) {
            moveDir("UP");
            return false;
        } else if (event.which == 39) {
            moveDir("RIGHT");
            return false;
        } else if (event.which == 37) {
            moveDir("LEFT");
            return false;
        } else if (event.which == 38) {
            moveDir("DOWN");
            return false;
        }
    });
}

$(function () {
    $("#showMap").focus();
    setupKeyBinding();
    updateViews();
});

