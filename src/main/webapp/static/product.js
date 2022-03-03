
function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

function isEmpty(json){

	var datas={};
	datas["barcode"]=JSON.parse(json)["barcode"];
	datas["name"]=JSON.parse(json)["name"];
	datas["mrp"]=JSON.parse(json)["mrp"];
	
	if(JSON.parse(json)["brand"]==null){
		datas["brand"]="";
	}
	else{
		datas["brand"]=JSON.parse(json)["brand"]
	}
	if(JSON.parse(json)["category"]==null){
		datas["category"]="";
	}
	else{
		datas["category"]=JSON.parse(json)["category"];
	}
	datas=JSON.stringify(datas);
	datas["mrp"]=Number(datas["mrp"]).toFixed(2);
	datas["mrp"]=datas["mrp"]+"";
	return(datas);
}

//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update
	var $form = $("#product-form");
	var json = toJson($form);
	var url = getProductUrl();
	json=isEmpty(json);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
			var responseMessage="Product added successfully";
			errorDisplay('success',responseMessage);
	   		getProductList();  
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});

	return false;
}

function updateProduct(event){
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();	
	var url = getProductUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);
	json=isEmpty(json);
	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
			var responseMessage="Product updated successfully";
			errorDisplay('success',responseMessage);
	   		getProductList();   
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});

	return false;
}


function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProductList(data);  
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});
}

function deleteProduct(id){
	var url = getProductUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getProductList();  
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	json=isEmpty(json);
	var url = getProductUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		uploadRows();  
			getProductList();
			var responseMessage="All Product added successfully";
			errorDisplay('success',responseMessage);
	   },
	   error: function(response){
		row.error=JSON.parse(response.responseText).message
		errorData.push(row);
		uploadRows();
		getProductList();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

function getBrandList(){
	var url = $("meta[name=baseUrl]").attr("content")+"/api/brandList";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandList(data);  
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});
}
function displayBrandList(data){
	var $dropdown=$(".brands")
	$dropdown.empty()
	var row='<option selected="true" disabled="disabled" value="select">--Select--</option>';
	$dropdown.append(row);
	for(var i in data){
		var e=data[i];
		var row='<option value='+e+'>'+e+'</option>';
		$dropdown.append(row);
	}
}



function getCategoryList(brand,change){
	var url = $("meta[name=baseUrl]").attr("content")+"/api/category/"+brand;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayCategoryList(data,change);  
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});
}
function displayCategoryList(data,change){
	var $dropdown=$(change);
	$dropdown.empty();
	if(change=="#inputCategory"){
		var row='<option selected="true" disabled="disabled" value="select">--Select--</option>';
		$dropdown.append(row);
	}
	for(var i in data){
		var e=data[i];
		var row='<option value='+e+'>'+e+'</option>';
		$dropdown.append(row);
	}
}


//UI DISPLAY METHODS

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	var c=1;
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button class="btn btn-primary edit_btn" onclick="displayEditProduct(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td class="coloumn">' + e.id + '</td>'
		+ '<td>' + c + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.mrp.toFixed(2) + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
		c++;
	}
}

function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);   
	   },
	   error: function(response){
		// $('#edit-product-modal').modal('toggle');
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
		// $('#edit-product-modal').modal('toggle');
	}
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#productFile');
	var fileName = $file.val();
	fileName=fileName.replace("C:\\fakepath\\", "");
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-product-modal').modal('toggle');
}

function displayProduct(data){
	$("#product-edit-form input[name=barcode]").val(data.barcode);	
	$("#inputEditBrand option[value="+data.brand+"]").prop("selected",true);
	var brand = $("#inputEditBrand").val(); 
	getCategoryList(brand,"#inputEditCategory");
	$("#inputEditCategory").val(data.category); 
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=mrp]").val(data.mrp);	
	$("#product-edit-form input[name=id]").val(data.id);	
	$('#edit-product-modal').modal('toggle');
}

//INITIALIZATION CODE
function init(){
	$('#add-product').click(addProduct);
	$('#update-product').click(updateProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getProductList);
$(document).ready(getBrandList);
$("#inputBrand").change(function() {
	var brand = $(this).val(); // get selected options value.
	getCategoryList(brand,"#inputCategory");    
});
$("#inputEditBrand").change(function() {
	var brand = $(this).val(); // get selected options value.
	getCategoryList(brand,"#inputEditCategory");    
});

