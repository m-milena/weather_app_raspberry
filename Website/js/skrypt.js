var ip_adress;
var url;
var ip;

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
			window.location.href="data_page.html";
	}	
});
});



function ValidateIPaddress(ipaddress) {  
  if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(ipaddress)) {  
    return (true)  
  }  
  alert("You have entered an invalid IP address!")  
  return (false)  
}  

/////////////////////////////////////////////////


	
document.getElementById("ip_adress_display").innerHTML = localStorage.getItem("ip_adress");

//apiURL="http://" + localStorage.getItem("ip_adress") + "/api/v1/czujniki.php?idName=Temperatura";
apiURL="http://" + localStorage.getItem("ip_adress") + "/Serwer/api/v1/czujniki.php";
document.getElementById("url").innerHTML = "Data from: "+ apiURL;
ip=localStorage.getItem("ip_adress");


function readData() {
    $.ajax({
        url : apiURL ,
        dataType : 'json'
    })
    .done(ret => {
        //console.log(ret);
        $.each(ret,function(key,item)
        {
           console.log(key +' '+item.wartosc); 
           
           switch(key)
           {
               case 'Temperatura':
                   document.getElementById("temp").innerHTML =(item.wartosc).toPrecision(4)+" °C";
                   break;
               case 'Cisnienie':
                   document.getElementById("pressure").innerHTML =(item.wartosc).toPrecision(5) +" hPa";
                   break;
                case 'Wilgotnosc':
                   document.getElementById("humidity").innerHTML =(item.wartosc).toPrecision(4) +" %";
                   break;
				case 'Zyroskop':
                   document.getElementById("gyroscope").innerHTML ="Gyroscope -> r: " +(item.wartosc.r).toPrecision(5) +" p: "+ (item.wartosc.p).toPrecision(5)+ " y: "+(item.wartosc.y).toPrecision(5);
				   if((item.wartosc.r>80 && item.wartosc.r<280)|| (item.wartosc.p>80 && item.wartosc.p<280)){
				   warningColor();
				   }
				   else{
				   normalColor();
				   }
		
                   break;
        }//switch
       
        }); //each
   
    }); //done
}
setInterval(function() {
  readData();
}, 1000);


function warningColor(){
document.getElementById("gyroscope").style.color="red";
}

function normalColor(){
document.getElementById("gyroscope").style.color="white";
}




