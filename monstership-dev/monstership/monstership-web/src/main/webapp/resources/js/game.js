var size = 20;
var table = "<table class='map'>";
for(var y=0; y < size; y++) {
    table += "<tr>";
    for(var x=0; x < size; x++) {
        table += "<td x='"+x+"' y='"+y+"'> </td>";
    }
    table += "</tr>";
}
table += "</table>";
document.getElementById('showMap').innerHTML = table;
