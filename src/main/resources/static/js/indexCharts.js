//line
var ctxL = document.getElementById("lineChart").getContext('2d');
var TWO_HOUR_TIMESTAMP = 7200000;


var un_count = [0, 0, 0, 0, 0, 0];
var aut_count = [0, 0, 0, 0, 0, 0];

for(var i = 0 ; i<list_un.length; i++){
    if (list_un[i] <= timescale && list_un[i] > timescale - TWO_HOUR_TIMESTAMP )
        un_count[5]++;
    else
        if(list_un[i] <= timescale - TWO_HOUR_TIMESTAMP && list_un[i] > timescale - (TWO_HOUR_TIMESTAMP*2) )
            un_count[4]++;
        else
            if (list_un[i] <= timescale - (TWO_HOUR_TIMESTAMP*2) && list_un[i] > timescale - (TWO_HOUR_TIMESTAMP*3))
                un_count[3]++;
            else
                if (list_un[i] <= timescale - (TWO_HOUR_TIMESTAMP*3) && list_un[i] > timescale - (TWO_HOUR_TIMESTAMP*4))
                    un_count[2]++;
                else
                    if (list_un[i] <= timescale - (TWO_HOUR_TIMESTAMP*4) && list_un[i] > timescale - (TWO_HOUR_TIMESTAMP*5))
                        un_count[1]++;
                        else
                            un_count[0]++;
}

for(var i = 0 ; i<list_aut.length; i++){
    if (list_aut[i] <= timescale && list_aut[i] > timescale - TWO_HOUR_TIMESTAMP )
        aut_count[5]++;
    else
    if(list_aut[i] <= timescale - TWO_HOUR_TIMESTAMP && list_aut[i] > timescale - (TWO_HOUR_TIMESTAMP*2) )
        aut_count[4]++;
    else
    if (list_aut[i] <= timescale - (TWO_HOUR_TIMESTAMP*2) && list_aut[i] > timescale - (TWO_HOUR_TIMESTAMP*3))
        aut_count[3]++;
    else
    if (list_aut[i] <= timescale - (TWO_HOUR_TIMESTAMP*3) && list_aut[i] > timescale - (TWO_HOUR_TIMESTAMP*4))
        aut_count[2]++;
    else
    if (list_aut[i] <= timescale - (TWO_HOUR_TIMESTAMP*4) && list_aut[i] > timescale - (TWO_HOUR_TIMESTAMP*5))
        aut_count[1]++;
    else
        aut_count[0]++;
}

console.log(un_count);

var myLineChart = new Chart(ctxL, {
    type: 'line',
    data: {
        labels: ["-12/-10", "-10/-8", "-8/-6", "-6/-4", "-4/-2", "-2/0"],
        datasets: [{
            label: "Unauthorized",
            data: un_count,
            backgroundColor: [
                'rgb(198,82,72,.2)',
            ],
            borderColor: [
                'rgb(234,10,10,.7)',
            ],
            borderWidth: 2
        },
            {
                label: "Authorized",
                data: aut_count,
                backgroundColor: [
                    'rgb(44,81,7,.2)',
                ],
                borderColor: [
                    'rgb(100,229,20,.7)',
                ],
                borderWidth: 2
            }
        ]
    },
    options: {
        responsive: true
    }
});