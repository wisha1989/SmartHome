
function mergeData(data,k) {
	pdata = data.split('\t')[k];
	arrData = pdata.split(',');
	var res = [];
	for (var i = 0; i < arrData.length; ++i)
		res.push([ i, arrData[i] ]);
	return res;
}
//Interactive Chart

function chart_history_temp() {
	
	function update() {
		htmlobj = $.ajax({
			url : "Listen",
			type : 'GET',
			data : {
				room : rNum,
				type : "0"
			},
			success : function(data) {
				
				 $.plot($("#chart_history_temp"), [ {
						data : mergeData(data, 0),
						label : "Device-1"
					}, {
						data : mergeData(data, 1),
						label : "Device-2"
					}
					,{
						data : mergeData(data, 2),
						label : "Device-3"
					}, {
						data : mergeData(data, 3),
						label : "Device-4"
					}
					],
					
					{
						series : {
							lines : {
								show : true,
								lineWidth : 2,
								fill : true,
								fillColor : {
									colors : [ {
										opacity : 0.05
									}, {
										opacity : 0.01
									}]
								}
							},
							points : {
								show : true
							},
							shadowSize : 2
						},
						grid : {
							hoverable : true,
							clickable : true,
							tickColor : "#eee",
							borderWidth : 0
						},
						colors : [ "#d12610", "#37b7f3", "#52e136", "#d2e116" ],
						xaxis : {
							ticks : 11,
							tickDecimals : 0
						},
						yaxis : {
							ticks : 11,
							tickDecimals : 0
						}
					});
			
			}
		});
		
	}
	
	update();
	
	function showTooltip(x, y, contents) {
		$('<div id="tooltip">' + contents + '</div>').css({
			position : 'absolute',
			display : 'none',
			top : y + 5,
			left : x + 15,
			border : '1px solid #333',
			padding : '4px',
			color : '#fff',
			'border-radius' : '3px',
			'background-color' : '#333',
			opacity : 0.80
		}).appendTo("body").fadeIn(200);
	}

	var previousPoint = null;
	$("#chart_history_temp")
			.bind(
					"plothover",
					function(event, pos, item) {
						$("#x").text(pos.x.toFixed(2));
						$("#y").text(pos.y.toFixed(2));

						if (item) {
							if (previousPoint != item.dataIndex) {
								previousPoint = item.dataIndex;

								$("#tooltip").remove();
								var x = item.datapoint[0].toFixed(2), y = item.datapoint[1]
										.toFixed(2);

								showTooltip(item.pageX, item.pageY,
										item.series.label + " of " + x + " = "
												+ y);
							}
						} else {
							$("#tooltip").remove();
							previousPoint = null;
						}
					});
}



////////////////////////////////
function chart_history_humidity() {
	function update() {
		htmlobj = $.ajax({
			url : "Listen",
			type : 'GET',
			data : {
				room : rNum,
				type : "1"
			},
			success : function(data) {
				
				 $.plot($("#chart_history_humidity"), [ {
						data : mergeData(data, 0),
						label : "Device-1"
					}, {
						data : mergeData(data, 1),
						label : "Device-2"
					}
					,{
						data : mergeData(data, 2),
						label : "Device-3"
					}, {
						data : mergeData(data, 3),
						label : "Device-4"
					}
					],
					
					{
						series : {
							lines : {
								show : true,
								lineWidth : 2,
								fill : true,
								fillColor : {
									colors : [ {
										opacity : 0.05
									}, {
										opacity : 0.01
									}]
								}
							},
							points : {
								show : true
							},
							shadowSize : 2
						},
						grid : {
							hoverable : true,
							clickable : true,
							tickColor : "#eee",
							borderWidth : 0
						},
						colors : [ "#d12610", "#37b7f3", "#52e136", "#d2e116" ],
						xaxis : {
							ticks : 11,
							tickDecimals : 0
						},
						yaxis : {
							ticks : 11,
							tickDecimals : 0
						}
					});
			
			}
		});
		
	}
	
	update();


	function showTooltip(x, y, contents) {
		$('<div id="tooltip">' + contents + '</div>').css({
			position : 'absolute',
			display : 'none',
			top : y + 5,
			left : x + 15,
			border : '1px solid #333',
			padding : '4px',
			color : '#fff',
			'border-radius' : '3px',
			'background-color' : '#333',
			opacity : 0.80
		}).appendTo("body").fadeIn(200);
	}

	var previousPoint = null;
	$("#chart_history_humidity")
			.bind(
					"plothover",
					function(event, pos, item) {
						$("#x").text(pos.x.toFixed(2));
						$("#y").text(pos.y.toFixed(2));

						if (item) {
							if (previousPoint != item.dataIndex) {
								previousPoint = item.dataIndex;

								$("#tooltip").remove();
								var x = item.datapoint[0].toFixed(2), y = item.datapoint[1]
										.toFixed(2);

								showTooltip(item.pageX, item.pageY,
										item.series.label + " of " + x + " = "
												+ y);
							}
						} else {
							$("#tooltip").remove();
							previousPoint = null;
						}
					});
}

var ul = document.getElementById('room');
var lis = ul.getElementsByTagName('li');
var c_room = document.getElementById('current-room');
var rNum = 1;
for (var i = 0; i < lis.length; i++) {
	lis[i].onclick = function() {
		c_room.innerText = this.innerText;
		rNum = c_room.innerText.split('-')[1].trim();
		htmlobj = $.ajax({
			url : "History",
			type : 'GET',
			data : {
				room : rNum,
			},
			success : function(data) {
				 
			}
		});
	};
}


