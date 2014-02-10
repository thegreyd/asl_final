	function load_canv_1(){
		var canvas = document.getElementById("_canv_1");

		if (canvas && canvas.getContext){
			var ctx = canvas.getContext("2d");
			ctx.fillStyle = "rgba(255,255,255,1)";
			ctx.beginPath();
			ctx.moveTo(0,0);
			ctx.lineTo(793.33,0);
			ctx.lineTo(793.33,1122.67);
			ctx.lineTo(0,1122.67);
			ctx.closePath();
			ctx.fill();
		}
	}
	
	function load_canv_2(){
		var canvas = document.getElementById("_canv_2");

		if (canvas && canvas.getContext){
			var ctx = canvas.getContext("2d");
			ctx.fillStyle = "rgba(255,255,255,1)";
			ctx.beginPath();
			ctx.moveTo(0,0);
			ctx.lineTo(816,0);
			ctx.lineTo(816,1056);
			ctx.lineTo(0,1056);
			ctx.closePath();
			ctx.fill();
		}
	}
	
function DrawPage1(){
	load_canv_1();
}
function DrawPage2(){
	load_canv_2();
}
