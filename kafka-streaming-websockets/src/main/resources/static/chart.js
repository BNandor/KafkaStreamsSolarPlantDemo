var maxLabels = 60;

var charConfigEnergyTrafficAverageProduction = {
    type: 'line',
    data: {
        labels: [],
        datasets: [{
            label: "EN",
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            fill: false,
            data: []
        }, {
            label: "RO",
            backgroundColor: window.chartColors.blue,
            borderColor: window.chartColors.blue,
            fill: false,
            data: []
        }, {
            label: "DE",
            backgroundColor: window.chartColors.green,
            borderColor: window.chartColors.green,
            fill: false,
            data: []
        }]
    },
    options: {
        responsive: true,
        title:{
            display:true,
            text:"Average energy production by country for the last 5 seconds."
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: false,
                    labelString: 'Timestamp'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: ' kWatt'
                }
            }]
        }
    }
};

var charConfigEnergyTrafficAverageConsumption = {
    type: 'line',
    data: {
        labels: [],
        datasets: [{
            label: "EN",
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            fill: false,
            data: []
        }, {
            label: "RO",
            backgroundColor: window.chartColors.blue,
            borderColor: window.chartColors.blue,
            fill: false,
            data: []
        }, {
            label: "DE",
            backgroundColor: window.chartColors.green,
            borderColor: window.chartColors.green,
            fill: false,
            data: []
        }]
    },
    options: {
        responsive: true,
        title:{
            display:true,
            text:"Average energy consumption by country for the last 5 seconds."
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: false,
                    labelString: 'Timestamp'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: ' kWatt'
                }
            }]
        }
    }
};


var charConfigWeatherInfoHumidity = {
    type: 'line',
    data: {
        labels: [],
        datasets: [{
            label: "Auxin",
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            fill: false,
            data: []
        }, {
            label: "Certainteed",
            backgroundColor: window.chartColors.blue,
            borderColor: window.chartColors.blue,
            fill: false,
            data: []
        }]
    },
    options: {
        responsive: true,
        title:{
            display:true,
            text:"Maximum humidity for the last 5 seconds by solar plant company "
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: false,
                    labelString: 'Timestamp'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: ' gramm/cubic meter'
                }
            }]
        }
    }
};

var charConfigWeatherInfoHumidityMin = {
    type: 'line',
    data: {
        labels: [],
        datasets: [{
            label: "Auxin",
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            fill: false,
            data: []
        }, {
            label: "Certainteed",
            backgroundColor: window.chartColors.blue,
            borderColor: window.chartColors.blue,
            fill: false,
            data: []
        }]
    },
    options: {
        responsive: true,
        title:{
            display:true,
            text:"Minimum humidity for the last 5 seconds by solar plant company "
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: false,
                    labelString: 'Timestamp'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: ' gramm/cubic meter'
                }
            }]
        }
    }
};

var charConfigWeatherInfoHumidityAverage = {
    type: 'line',
    data: {
        labels: [],
        datasets: [{
            label: "Auxin",
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            fill: false,
            data: []
        }, {
            label: "Certainteed",
            backgroundColor: window.chartColors.blue,
            borderColor: window.chartColors.blue,
            fill: false,
            data: []
        }]
    },
    options: {
        responsive: true,
        title:{
            display:true,
            text:"Average humidity for the last 5 seconds by solar plant company "
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: false,
                    labelString: 'Timestamp'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: ' gramm/cubic meter'
                }
            }]
        }
    }
};

window.onload = function() {
    var ctxprod = document.getElementById("energy-production-canvas").getContext("2d");
    var ctxcons = document.getElementById("energy-consumption-canvas").getContext("2d");
    var maxhumcanv = document.getElementById("max-humidity-canvas").getContext("2d");
    var minhumcanv = document.getElementById("min-humidity-canvas").getContext("2d");
    var averagehumcanv = document.getElementById("average-humidity-canvas").getContext("2d");

    window.myLine = new Chart(ctxprod, charConfigEnergyTrafficAverageProduction);
    window.myLine2 = new Chart(ctxcons, charConfigEnergyTrafficAverageConsumption);
    window.myLine3 = new Chart(maxhumcanv, charConfigWeatherInfoHumidity);
    window.myLine4 = new Chart(minhumcanv, charConfigWeatherInfoHumidityMin);
    window.myLine5 = new Chart(averagehumcanv, charConfigWeatherInfoHumidityAverage);
};


// Here I assume we receive messages in correct order
function addDataToChart(message){

    var splitMessage = message.split(" ");

    if (splitMessage.length == 3){
        var clusterName = splitMessage[0].toUpperCase();
        var timestamp = splitMessage[1];
//        var count = splitMessage[2];
        var avg = splitMessage[2];

        // Add point if new
        if (charConfigEnergyTrafficAverageProduction.data.labels.indexOf(timestamp) < 0)
            addOne(timestamp,charConfigEnergyTrafficAverageProduction,window.myLine);

        // Remove one point if already too many
        if (charConfigEnergyTrafficAverageProduction.data.labels.length > maxLabels)
            removeOne(charConfigEnergyTrafficAverageProduction,window.myLine);

        // Update point
        charConfigEnergyTrafficAverageProduction.data.datasets.forEach(function(dataset, datasetIndex) {
            if (dataset.label == clusterName){
                dataset.data.forEach(function(point, pointIdx) {
                    if (point.x == timestamp) point.y = avg;
                });
            }
        });

    } else {
        console.log("could not split message "+splitMessage.length)
        console.log(message);
    }
        window.myLine.update();
}


function addDataToChartConsumtion(message) {

    var splitMessage = message.split(" ");

    if (splitMessage.length == 3){
        var clusterName = splitMessage[0].toUpperCase();
        var timestamp = splitMessage[1];
//        var count = splitMessage[2];
        var avg = splitMessage[2];

        // Add point if new
        if (charConfigEnergyTrafficAverageConsumption.data.labels.indexOf(timestamp) < 0)
            addOne(timestamp,charConfigEnergyTrafficAverageConsumption,window.myLine2);

        // Remove one point if already too many
        if (charConfigEnergyTrafficAverageConsumption.data.labels.length > maxLabels)
            removeOne(charConfigEnergyTrafficAverageConsumption,window.myLine2);

        // Update point
        charConfigEnergyTrafficAverageConsumption.data.datasets.forEach(function(dataset, datasetIndex) {
            if (dataset.label == clusterName){
                dataset.data.forEach(function(point, pointIdx) {
                    if (point.x == timestamp) point.y = avg;
                });
            }
        });

    } else {
        console.log("could not split message "+splitMessage.length)
        console.log(message);
    }
        window.myLine2.update();
}


function addDataToChartHumidity(message) {

    var splitMessage = message.split(" ");

    if (splitMessage.length == 3){
        var clusterName = splitMessage[0];
        var timestamp = splitMessage[1];
//        var count = splitMessage[2];
        var avg = splitMessage[2];

        // Add point if new
        if (charConfigWeatherInfoHumidity.data.labels.indexOf(timestamp) < 0)
            addOne(timestamp,charConfigWeatherInfoHumidity,window.myLine3);

        // Remove one point if already too many
        if (charConfigWeatherInfoHumidity.data.labels.length > maxLabels)
            removeOne(charConfigWeatherInfoHumidity,window.myLine3);

        // Update point
        charConfigWeatherInfoHumidity.data.datasets.forEach(function(dataset, datasetIndex) {
            if (dataset.label == clusterName){
                dataset.data.forEach(function(point, pointIdx) {
                    if (point.x == timestamp) point.y = avg;
                });
            }
        });

    } else {
        console.log("could not split message "+splitMessage.length)
        console.log(message);
    }
        window.myLine3.update();
}


function addDataToChartHumidityMin(message) {

    var splitMessage = message.split(" ");

    if (splitMessage.length == 3) {
        var clusterName = splitMessage[0];
        var timestamp = splitMessage[1];
//        var count = splitMessage[2];
        var avg = splitMessage[2];

        // Add point if new
        if (charConfigWeatherInfoHumidityMin.data.labels.indexOf(timestamp) < 0)
            addOne(timestamp,charConfigWeatherInfoHumidityMin,window.myLine4);

        // Remove one point if already too many
        if (charConfigWeatherInfoHumidityMin.data.labels.length > maxLabels)
            removeOne(charConfigWeatherInfoHumidityMin,window.myLine4);

        // Update point
        charConfigWeatherInfoHumidityMin.data.datasets.forEach(function(dataset, datasetIndex) {
            if (dataset.label == clusterName){
                dataset.data.forEach(function(point, pointIdx) {
                    if (point.x == timestamp) point.y = avg;
                });
            }
        });

    } else {
        console.log("could not split message "+splitMessage.length)
        console.log(message);
    }
        window.myLine4.update();
}

function addDataToChartHumidityAverage(message) {

    var splitMessage = message.split(" ");

    if (splitMessage.length == 3) {
        var clusterName = splitMessage[0];
        var timestamp = splitMessage[1];
//        var count = splitMessage[2];
        var avg = splitMessage[2];

        // Add point if new
        if (charConfigWeatherInfoHumidityAverage.data.labels.indexOf(timestamp) < 0)
            addOne(timestamp,charConfigWeatherInfoHumidityAverage,window.myLine5);

        // Remove one point if already too many
        if (charConfigWeatherInfoHumidityAverage.data.labels.length > maxLabels)
            removeOne(charConfigWeatherInfoHumidityAverage,window.myLine5);

        // Update point
        charConfigWeatherInfoHumidityAverage.data.datasets.forEach(function(dataset, datasetIndex) {
            if (dataset.label == clusterName) {
                dataset.data.forEach(function(point, pointIdx) {
                    if (point.x == timestamp) point.y = avg;
                });
            }
        });

    } else {
        console.log("could not split message "+splitMessage.length)
        console.log(message);
    }
        window.myLine5.update();
}

function addOne(timestamp,chartconfig,line) {

    chartconfig.data.labels.push(timestamp);
    chartconfig.data.datasets.forEach(function(dataset, datasetIndex) {
        dataset.data.push({x: timestamp, y: undefined});
    });

    line.update();
}

function removeOne(chartconfig) {

    chartconfig.data.labels.shift();
    chartconfig.data.datasets.forEach(function(dataset, datasetIndex) {
        dataset.data.shift();
    });

    line.update();
}