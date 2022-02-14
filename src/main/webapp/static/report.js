
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report";
}

function getToday(){
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; 
	var yyyy = today.getFullYear();
	if(dd<10) 
	{
		dd='0'+dd;
	} 
	if(mm<10) 
	{
		mm='0'+mm;
	} 
	today = yyyy+'-'+mm+'-'+dd;
	$("input[type='date']").prop('max',today);
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
	var $dropdown=$("#brandName")
	$dropdown.empty()
	var row='<option selected="true" value="">--Select--</option>';
	$dropdown.append(row);
	for(var i in data){
		var e=data[i];
		var row='<option value='+e+'>'+e+'</option>';
		$dropdown.append(row);
	}
}

function getCategoryList(){
	var url = $("meta[name=baseUrl]").attr("content")+"/api/category";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayCategoryList(data);  
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});
}
function displayCategoryList(data){
	var $dropdown=$("#brandCategory");
	$dropdown.empty();
	var row='<option selected="true" value="">--Select--</option>';
	$dropdown.append(row);
	for(var i in data){
		var e=data[i];
		var row='<option value='+e+'>'+e+'</option>';
		$dropdown.append(row);
	}
}


function getOrderItemList(){
	var url = getBrandUrl();
	var $form = $("#report-form");
	var input={}
	var json = toJson($form);
	input["startDate"]=JSON.parse(json).startDate+" 00:00:00";
	input["endDate"]=JSON.parse(json).endDate+" 23:59:59";
	if(JSON.parse(json)["brand"]==null || JSON.parse(json)["brand"]=="")
	{
		input["brand"]="%";
	}
	else{
		input["brand"]=JSON.parse(json).brand;
	}
	if(JSON.parse(json)["category"]==null || JSON.parse(json)["category"]=="")
	{
		input["category"]="%";
	}
	else{
		input["category"]=JSON.parse(json).category;
	}
	pass=input;
	input=JSON.stringify(input);
	$.ajax({
		url: url,
		type: 'POST',
		data: input,
		headers: {
		'Content-Type': 'application/json'
		},	   
		success: function(response) {
			displayItemList(response,pass);
			// writeFile(response,"Order-Item-List");  
		},
		error: function(response){
			var responseMessage=JSON.parse(response.responseText).message;
			errorDisplay('danger',responseMessage);
		}
	});
		
			return false;

}


function getBrand(){
	var url = getBrandUrl() + "/brand";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		downloadBrand(data);
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});
}

function getProduct(){
	var url = getBrandUrl() + "/product";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		downloadProduct(data);	  
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});
}

function getInventory(){
	var url = getBrandUrl() + "/inventory";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
			downloadInventory(data);
	   },
	   error: function(response){
		var responseMessage=JSON.parse(response.responseText).message;
		errorDisplay('danger',responseMessage);
	}
	});
}



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
		d["MRP"]=data[i].mrp.toFixed(2);
		temp.push(d);
	}
	writeFile(temp,'Product-Report');
}

function downloadInventory(data){
	var temp=[];
	for(var i=0;i<data.length;i++){
		var d={}
		d["Barcode"]=data[i].barcode;
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

function displayItemList(data,input){
	var $tbody = $('#report-table').find('tbody');
	var $thead = $('#report-table').find('thead');
	var $tfoot = $('#report-table').find('tfoot');
	$thead.empty();
	$tbody.empty();
	$tfoot.empty();
	var foot='<tr style="text-align: center;">' 
	+'<td style="text-align: center;"colspan="5">No Record Found</td>'                             
    +'</tr>';
	$tfoot.append(foot);
	var head=' <tr>'
	+'<th class="brand_col" scope="col">Brand</th>'
	+'<th class="category_col" scope="col">Category</th>'
	+'<th class="product_col" scope="col">Product</th>'
	+'<th scope="col">Quantity</th>'
	+'<th scope="col">Revenue</th>'
    +'</tr>';
	$thead.append(head);
	$("#norecord").css("visibility","visible");
	var check={}
	var q={}
	if(input["brand"]!="%" && input["category"]!="%")
	{	
		$(".product_col").css("display","visible");
		for(var i in data){
			var e = data[i];
			if(e.brand==input["brand"] && e.category==input["category"] )
			{
					if(check[e.name]==null)
				{
					check[e.name]=e.quantity*e.mrp;
					q[e.name]=e.quantity;
				}
				else{
					check[e.name]+=e.quantity*e.mrp;
					q[e.name]+=e.quantity;
				}
			}
		}
	
		for(var i in check){
			var row = '<tr>'
			+ '<td  class="brand_col"></td>'
			+ '<td class="category_col"></td>'
			+ '<td class="product_col">' + i + '</td>'
			+ '<td>'  + q[i] + '</td>'
			+ '<td>'  + check[i].toFixed(2) + '</td>'
			+ '</tr>';
			$tbody.append(row);
		}
		$(".category_col").css("display","none");
		$(".brand_col").css("display","none");
		$(".product_col").css("display","visible");

		
	}
	else if(input["brand"]=="%" && input["category"]!="%")
	{
		$(".brand_col").css("display","visible");
		for(var i in data){
			var e = data[i];
			if(e.category==input["category"])
			{
					if(check[e.brand]==null)
				{
					check[e.brand]=e.quantity*e.mrp;
					q[e.brand]=e.quantity;
				}
				else{
					check[e.brand]+=e.quantity*e.mrp;
					q[e.brand]+=e.quantity;
				}
			}
		}
		for(var i in check){
			var row = '<tr>'
			+ '<td  class="brand_col">' + i + '</td>'
			+ '<td class="category_col"></td>'
			+ '<td class="product_col"></td>'
			+ '<td>'  + q[i] + '</td>'
			+ '<td>'  + check[i].toFixed(2) + '</td>'
			+ '</tr>';
			$tbody.append(row);
		}
		$(".category_col").css("display","none");
		$(".product_col").css("display","none");
		$(".brand_col").css("display","visible");

		
	}
	else if(input["brand"]!="%" && input["category"]=="%")
	{
		$(".category_col").css("display","visible");
		for(var i in data){
			var e = data[i];
			if(e.brand==input["brand"])
			{
					if(check[e.category]==null)
				{
					check[e.category]=e.quantity*e.mrp;
					q[e.category]=e.quantity;
				}
				else{
					check[e.category]+=e.quantity*e.mrp;
					q[e.category]+=e.quantity;
				}
			}
		}
		
		for(var i in check){
			var row = '<tr>'
			+ '<td  class="brand_col"></td>'
			+ '<td class="category_col">' + i + '</td>'
			+ '<td class="product_col"></td>'
			+ '<td>'  + q[i] + '</td>'
			+ '<td>'  + check[i].toFixed(2) + '</td>'
			+ '</tr>';
			$tbody.append(row);
		}
		$(".brand_col").css("display","none");
		$(".product_col").css("display","none");
		$(".category_col").css("display","visible");

		
	}
	else
	{
		$(".product_col").css("display","visible");
		b={}
		c={}
		for(var i in data){
			var e = data[i];
			if(check[e.name]==null)
			{
				check[e.name]=e.quantity*e.mrp;
				b[e.name]=e.brand;
				c[e.name]=e.category;
				q[e.name]=e.quantity;
			}
			else{
				check[e.name]+=e.quantity*e.mrp;
				q[e.name]+=e.quantity;
			}
		}
		for(var i in check){
			var row = '<tr>'
			+ '<td  class="brand_col">' + b[i] + '</td>'
			+ '<td class="category_col">' + c[i] + '</td>'
			+ '<td class="product_col">' + i + '</td>'
			+ '<td>'  + q[i] + '</td>'
			+ '<td>'  + check[i].toFixed(2) + '</td>'
			+ '</tr>';
			$tbody.append(row);
		}
		$(".category_col").css("display","visible");
		$(".brand_col").css("display","visible");
		$(".product_col").css("display","visible");
		
	}
	if(Object.keys(check).length!=0){
		$("#norecord").css("visibility","hidden");
	}

}



//INITIALIZATION CODE
function init(){
	$('#get-brand').click(getBrand);
	$('#get-product').click(getProduct);
	$('#get-inventory').click(getInventory);
	$('#search').click(getOrderItemList);
	
}



$(document).ready(init);
$(document).ready(getBrandList);
$(document).ready(getCategoryList);
$(document).ready(getToday);
