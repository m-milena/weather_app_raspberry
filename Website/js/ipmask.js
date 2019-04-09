var options =  { 
            onKeyPress: function(cep, event, currentField, options){
//            console.log('An key was pressed!:', cep, ' event: ', event,'currentField: ', currentField, ' options: ', options);
                if(cep){
                  var ipArray = cep.split(".");
                  var lastValue = ipArray[ipArray.length-1];
                  if(lastValue !== "" && parseInt(lastValue) > 255){
                      ipArray[ipArray.length-1] =  '255';
                      var resultingValue = ipArray.join(".");
                      currentField.attr('value',resultingValue);
                  }
            }             
        }};

$('.ip_address').mask("000.000.000.000", options);