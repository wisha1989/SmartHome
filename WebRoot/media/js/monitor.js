var data = [];
var totalPoints = 250;

function getRandomData() {
	if (data.length > 0)
		data = data.slice(1);
	// do a random walk
	while (data.length < totalPoints) {
		var prev = data.length > 0 ? data[data.length - 1] : 50;
		var y = 0;
		data.push(y);
	}

	// zip the generated y values with the x values
	var res = [];
	for (var i = 0; i < data.length; ++i)
		res.push([ i, data[i] ]);
	return res;
}

function chart_dynamic_temp() {
	//server load
	var options = {
		series : {
			shadowSize : 1
		},
		lines : {
			show : true,
			lineWidth : 0.5,
			fill : true,
			fillColor : {
				colors : [ {
					opacity : 0.1
				}, {
					opacity : 1
				} ]
			}
		},
		yaxis : {
			min : 15,
			max : 50,
			tickFormatter : function(v) {
				return v ;
			}
		},
		xaxis : {
			show : false
		},
		colors : [ "#6ef146" ],
		grid : {
			tickColor : "#a8a3a3",
			borderWidth : 0
		}
	};

	var updateInterval = 200;
	var plot = $.plot($("#chart_dynamic_temp"), [getRandomData()], options);

	function mergeData(data) {
		arrData = data.split(',');
		var res = [];
		for (var i = 0; i < data.length; ++i)
			res.push([ i, arrData[i] ]);
		return res;
	}
	function update() {
		htmlobj = $.ajax({
			url : "Monitor",
			type : 'GET',
			data : {
				room : rNum,
				type : "T"
			},
			success : function(data) {
				plot.setData([mergeData(data) ]);
				plot.draw();
				setTimeout(update, updateInterval);
			}
		});
		
	}
	update();
}



function chart_dynamic_humidity() {
	//server load
	var options = {
		series : {
			shadowSize : 1
		},
		lines : {
			show : true,
			lineWidth : 0.5,
			fill : true,
			fillColor : {
				colors : [ {
					opacity : 0.1
				}, {
					opacity : 1
				} ]
			}
		},
		yaxis : {
			min : 0,
			max : 100,
			tickFormatter : function(v) {
				return v + "%";
			}
		},
		xaxis : {
			show : false
		},
		colors : [ "#6ef146" ],
		grid : {
			tickColor : "#a8a3a3",
			borderWidth : 1
		}
	};

	var updateInterval = 200;
	var plot = $.plot($("#chart_dynamic_humidity"), [getRandomData()], options);

	function mergeData(data) {
		arrData = data.split(',');
		var res = [];
		for (var i = 0; i < data.length; ++i)
			res.push([ i, arrData[i] ]);
		return res;
	}
	function update() {
		htmlobj = $.ajax({
			url : "Monitor",
			type : 'GET',
			data : {
				room : rNum,
				type : "H"
			},
			success : function(data) {
				plot.setData([mergeData(data) ]);
				plot.draw();
				setTimeout(update, updateInterval);
			}
		});
		
	}
	update();
}

var ul = document.getElementById('room');
var lis = ul.getElementsByTagName('li');
var c_room = document.getElementById('current-room');
var rNum = 1;
for (var i = 0; i < lis.length; i++) {
	lis[i].onclick = function() {
		c_room.innerText = this.innerText;
		rNum = c_room.innerText.split('-')[1].trim();
		
	};
}

