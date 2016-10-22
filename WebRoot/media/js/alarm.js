function chart_history_temp() {
	var d1 = [];
	for (var i = 0; i <= 10; i += 1)
		d1.push([ i, parseInt(Math.random() * 30) ]);

	var d2 = [];
	for (var i = 0; i <= 10; i += 1)
		d2.push([ i, parseInt(Math.random() * 30) ]);

	var d3 = [];
	for (var i = 0; i <= 10; i += 1)
		d3.push([ i, parseInt(Math.random() * 30) ]);

	var stack = 0, bars = true, lines = false, steps = false;

	function plotWithOptions() {
		$.plot($("#chart_alarm_temp"), [ d1, d2, d3 ], {
			series : {
				stack : stack,
				lines : {
					show : lines,
					fill : true,
					steps : steps
				},
				bars : {
					show : bars,
					barWidth : 0.6
				}
			}
		});
	}

	$(".stackControls input").click(function(e) {
		e.preventDefault();
		stack = $(this).val() == "With stacking" ? true : null;
		plotWithOptions();
	});
	$(".graphControls input").click(function(e) {
		e.preventDefault();
		bars = $(this).val().indexOf("Bars") != -1;
		lines = $(this).val().indexOf("Lines") != -1;
		steps = $(this).val().indexOf("steps") != -1;
		plotWithOptions();
	});

	plotWithOptions();
}

function chart_history_humidity() {
	var d1 = [];
	for (var i = 0; i <= 10; i += 1)
		d1.push([ i, parseInt(Math.random() * 30) ]);

	var d2 = [];
	for (var i = 0; i <= 10; i += 1)
		d2.push([ i, parseInt(Math.random() * 30) ]);

	var d3 = [];
	for (var i = 0; i <= 10; i += 1)
		d3.push([ i, parseInt(Math.random() * 30) ]);

	var stack = 0, bars = true, lines = false, steps = false;

	function plotWithOptions() {
		$.plot($("#chart_alarm_humidity"), [ d1, d2, d3 ], {
			series : {
				stack : stack,
				lines : {
					show : lines,
					fill : true,
					steps : steps
				},
				bars : {
					show : bars,
					barWidth : 0.6
				}
			}
		});
	}

	$(".stackControls input").click(function(e) {
		e.preventDefault();
		stack = $(this).val() == "With stacking" ? true : null;
		plotWithOptions();
	});
	$(".graphControls input").click(function(e) {
		e.preventDefault();
		bars = $(this).val().indexOf("Bars") != -1;
		lines = $(this).val().indexOf("Lines") != -1;
		steps = $(this).val().indexOf("steps") != -1;
		plotWithOptions();
	});

	plotWithOptions();
}

//var ul = document.getElementById('room');
//var lis = ul.getElementsByTagName('li');
//var c_room = document.getElementById('current-room');
//var rNum = 1;
//for (var i = 0; i < lis.length; i++) {
//	lis[i].onclick = function() {
//		c_room.innerText = this.innerText;
//		rNum = c_room.innerText.split('-')[1].trim();
//		htmlobj = $.ajax({
//			url : "Alarm",
//			type : 'GET',
//			data : {
//				room : rNum,
//			},
//			success : function(data) {
//
//			}
//		});
//	};
//}


