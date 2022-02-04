
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report";
}

//BUTTON ACTIONS
// function addBrand(event){
// 	//Set the values to update
// 	var $form = $("#brand-form");
// 	var json = toJson($form);
// 	var url = getBrandUrl();

// 	$.ajax({
// 	   url: url,
// 	   type: 'POST',
// 	   data: json,
// 	   headers: {
//        	'Content-Type': 'application/json'
//        },	   
// 	   success: function(response) {
// 	   		getBrandList();  
// 	   },
// 	   error: handleAjaxError
// 	});

// 	return false;
// }

// function updateBrand(event){
// 	$('#edit-brand-modal').modal('toggle');
// 	//Get the ID
// 	var id = $("#brand-edit-form input[name=id]").val();	
// 	var url = getBrandUrl() + "/" + id;

// 	//Set the values to update
// 	var $form = $("#brand-edit-form");
// 	var json = toJson($form);

// 	$.ajax({
// 	   url: url,
// 	   type: 'PUT',
// 	   data: json,
// 	   headers: {
//        	'Content-Type': 'application/json'
//        },	   
// 	   success: function(response) {
// 	   		getBrandList();   
// 	   },
// 	   error: handleAjaxError
// 	});

// 	return false;
// }


// function getBrandList(){
// 	var url = getBrandUrl();
// 	$.ajax({
// 	   url: url,
// 	   type: 'GET',
// 	   success: function(data) {
// 	   		displayBrandList(data);  
// 	   },
// 	   error: handleAjaxError
// 	});
// }

function getBrand(){
	var url = getBrandUrl() + "/brand";
	// console.log("inside brand");

	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		downloadBrand(data);
		console.log(data);
	   },
	   error: handleAjaxError
	});
}

function getProduct(){
	var url = getBrandUrl() + "/product";
	// console.log("inside product");

	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		downloadProduct(data);
		console.log(data);	   		  
	   },
	   error: handleAjaxError
	});
}

function getInventory(){
	var url = getBrandUrl() + "/inventory";
	// console.log("inside inventory");

	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log(data);
			downloadInventory(data);
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
// var fileData = [];
// var errorData = [];
// var processCount = 0;


// function processData(){
// 	var file = $('#brandFile')[0].files[0];
// 	readFileData(file, readFileDataCallback);
// }

// function readFileDataCallback(results){
// 	fileData = results.data;
// 	uploadRows();
// }

// function uploadRows(){
// 	//Update progress
// 	updateUploadDialog();
// 	//If everything processed then return
// 	if(processCount==fileData.length){
// 		return;
// 	}
	
// 	//Process next row
// 	var row = fileData[processCount];
// 	processCount++;
	
// 	var json = JSON.stringify(row);
// 	var url = getBrandUrl();

// 	//Make ajax call
// 	$.ajax({
// 	   url: url,
// 	   type: 'POST',
// 	   data: json,
// 	   headers: {
//        	'Content-Type': 'application/json'
//        },	   
// 	   success: function(response) {
// 	   		uploadRows();  
// 	   },
// 	   error: function(response){
// 	   		row.error=response.responseText
// 	   		errorData.push(row);
// 	   		uploadRows();
// 	   }
// 	});

// }

function downloadBrand(data){
	var temp=[];
	for(var i=0;i<data.length;i++){
		var d={}
		d["Brand name"]=data[i].brand;
		d["Category"]=data[i].category;
		temp.push(d);
	}
	writeFile(temp,'Brand-Report');
}

function downloadProduct(data){
	var temp=[];
	for(var i=0;i<data.length;i++){
		var d={}
		d["Product name"]=data[i].name;
		d["Barcode"]=data[i].barcode;
		d["Brand"]=data[i].brand;
		d["Category"]=data[i].category;
		d["MRP"]=data[i].mrp;
		temp.push(d);
	}
	writeFile(temp,'Product-Report');
}

function downloadInventory(data){
	var temp=[];
	for(var i=0;i<data.length;i++){
		var d={}
		d["Product Name"]=data[i].name;
		d["Quantity"]=data[i].quantity;
		temp.push(d);
	}
	writeFile(temp,'Inventory-Report');
}

function writeFile(arr,name){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};
	var fileName=name+'.tsv';
	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, fileName);
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', fileName);
    tempLink.click(); 
}

// //UI DISPLAY METHODS

// function displayBrandList(data){
// 	var $tbody = $('#brand-table').find('tbody');
// 	$tbody.empty();
// 	for(var i in data){
// 		var e = data[i];
// 		var buttonHtml = '<button onclick="deleteBrand(' + e.id + ')">delete</button>'
// 		buttonHtml += ' <button onclick="displayEditBrand(' + e.id + ')">edit</button>'
// 		var row = '<tr>'
// 		+ '<td>' + e.id + '</td>'
// 		+ '<td>' + e.brand + '</td>'
// 		+ '<td>'  + e.category + '</td>'
// 		+ '<td>' + buttonHtml + '</td>'
// 		+ '</tr>';
//         $tbody.append(row);
// 	}
// }

// function displayEditBrand(id){
// 	var url = getBrandUrl() + "/" + id;
// 	$.ajax({
// 	   url: url,
// 	   type: 'GET',
// 	   success: function(data) {
// 	   		displayBrand(data);   
// 	   },
// 	   error: handleAjaxError
// 	});	
// }

// function resetUploadDialog(){
// 	//Reset file name
// 	var $file = $('#brandFile');
// 	$file.val('');
// 	$('#brandFileName').html("Choose File");
// 	//Reset various counts
// 	processCount = 0;
// 	fileData = [];
// 	errorData = [];
// 	//Update counts	
// 	updateUploadDialog();
// }

// function updateUploadDialog(){
// 	$('#rowCount').html("" + fileData.length);
// 	$('#processCount').html("" + processCount);
// 	$('#errorCount').html("" + errorData.length);
// }

// function updateFileName(){
// 	var $file = $('#brandFile');
// 	var fileName = $file.val();
// 	$('#brandFileName').html(fileName);
// }

// function displayUploadData(){
//  	resetUploadDialog(); 	
// 	$('#upload-brand-modal').modal('toggle');
// }

// function displayBrand(data){
// 	$("#brand-edit-form input[name=brand]").val(data.brand);	
// 	$("#brand-edit-form input[name=category]").val(data.category);	
// 	$("#brand-edit-form input[name=id]").val(data.id);	
// 	$('#edit-brand-modal').modal('toggle');
// }


//INITIALIZATION CODE
function init(){
	$('#get-brand').click(getBrand);
	$('#get-product').click(getProduct);
	$('#get-inventory').click(getInventory);
	// $('#upload-data').click(displayUploadData);
	// $('#process-data').click(processData);
	// $('#download-errors').click(downloadErrors);
    // $('#brandFile').on('change', updateFileName)
}


$(document).ready(init);
// $(document).ready(getBrandList);

