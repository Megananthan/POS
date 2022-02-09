
//HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}

function errorDisplay(template,message){
    var $errorbar=$('#status-bar');
    var text='Success! ';
    if(template=='danger'){
        text='Failed! ';
    }
    $errorbar.empty();
    var str='<div class="alert alert-'+template + ' alert-dismissable">'+
        '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+
        '<strong>'+ text + '</strong>' + message +
        '</div>';
    $errorbar.append(str);
}

function handleAjaxError(response){
	var response = JSON.parse(response.responseText);
	alert(response.message);
}

function readFileData(file, callback){
	var config = {
		header: true,
		delimiter: "\t",
		skipEmptyLines: "greedy",
		complete: function(results) {
			callback(results);
	  	}	
	}
	Papa.parse(file, config);
}


function writeFileData(arr){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};
	
	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download_error.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download_error.tsv');
    tempLink.click(); 
}

