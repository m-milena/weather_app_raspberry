var ip_adress;
var url;


$(function(){
$("#refresh").click(function(){
	location.reload(true);
});
});

$(function(){
$("#button_connect").click(function(){
	ip_adress=	document.getElementById("input_ip").value;
	if(ValidateIPaddress(ip_adress) ==true){
			localStorage.setItem("ip_adress", ip_adress);
	}	
});
});

var input =document.querySelectorAll("input");

for(var i=0; i<input.length; i++){
input[i].addEventListener("input",function(){
	var red= document.getElementById("red").value,
		green=document.getElementById("green").value,
		blue=document.getElementById("blue").value;
		document.getElementById("dis_red").innerHTML="R: "+red;
		document.getElementById("dis_green").innerHTML="G: "+green;
		document.getElementById("dis_blue").innerHTML="B: "+blue;
	var display_color=document.getElementById("display_color");
	display_color.style.background="rgb("+red+", "+green+", "+blue+")";
});

}

document.getElementById("display_ip").innerHTML = localStorage.getItem("ip_adress");
url="http://" + localStorage.getItem("ip_adress") + "/Serwer/api/v1/diody.php";


$.fn.textWidth = function(text, font) {
    
    if (!$.fn.textWidth.fakeEl) $.fn.textWidth.fakeEl = $('<span>').hide().appendTo(document.body);
    
    $.fn.textWidth.fakeEl.text(text || this.val() || this.text() || this.attr('placeholder')).css('font', font || this.css('font'));
    
    return $.fn.textWidth.fakeEl.width();
};

$('.width-dynamic').on('input', function() {
    var inputWidth = $(this).textWidth();
    $(this).css({
        width: inputWidth
    })
}).trigger('input');


function inputWidth(elem, minW, maxW) {
    elem = $(this);
    console.log(elem)
}

var targetElem = $('.width-dynamic');

inputWidth(targetElem);


	// Shorthand for $( document ).ready()
	
	function GET()
	{
		var text_display = document.getElementById('text_to_display').value;

		
		var text_r = document.getElementById('red').value;
		var text_b = document.getElementById('blue').value;
		var text_g = document.getElementById('green').value;
		window.alert("Text to display: " + text_display + "\n" + "Color: " + "\n"
		+"R: " + text_r+ "\n" + "G: " + text_g+ "\n"
		+ "B: " + text_b);
		
		$.get(url + "/?text=" + text_display  + "&R=" + text_r + "&G=" + text_g + "&B=" + text_b, function(data)
		{
			console.log(data);	
		});
		
	};
	
