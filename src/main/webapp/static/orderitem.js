
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}

//variable to store all order item
var item=[];
var dict={};
var count=0;
//BUTTON ACTIONS
function addOrderItem(event){
	//Set the values to update
	var $form = $("#orderitem-form");
	var json = toJson($form);
	
	var url = getOrderItemUrl()+"/check";
	//console.log("barcode "+JSON.parse(json).barcode);
	var i=dict[JSON.parse(json).barcode];
	console.log("item "+JSON.stringify(item));
	console.log("dict "+JSON.stringify(dict));
	if(i!=null)
	{
		var q=parseInt(JSON.parse(json).quantity);
		if(item[i].inventory<item[i].quantity+q)
		{
			alert("Inventory has only "+item[i].inventory+" item for product: "+item[i].name);
		}
		else{
			item[i].quantity+=q;
			displayOrderItemList(item);
		}
		
		//console.log("Inside if");
	}
	else{
		$.ajax({
			url: url,
			type: 'POST',
			data: json,
			headers: {
				'Content-Type': 'application/json'
			},	   
			success: function(response) {
					dict[response.barcode]=count;
					item.push(response);
					count++;
					displayOrderItemList(item);  
			},
			error: handleAjaxError
		 });
	}
	return false;
}

function updateOrderItem(event){
	$('#edit-orderitem-modal').modal('toggle');
	//Get the Barcode
	var barcode = $("#orderitem-edit-form input[name=barcode]").val();
	var quantity= $("#orderitem-edit-form input[name=quantity]").val();
	var n=dict[barcode];	
	if(item[n].inventory<quantity)
		{	
			$('#edit-orderitem-modal').modal('toggle');
			alert("Inventory has only "+item[n].inventory+" item for product: "+item[n].name);
		}
		else{
			item[n].quantity=quantity;
			displayOrderItemList(item);
		}
}


function getOrderItemList(){
	
	   		displayOrderItemList(item);  
	  
}

function deleteOrderItem(n){
	delete dict[item[n].barcode]
	item.splice(n,1);
	count--;
	var temp={};
	for(var i=0;i<item.length;i++)
	{
    	temp[item[i].barcode]=i;
	}
	dict=temp;
	console.log("Delete ----");
	console.log("item "+JSON.stringify(item));
	console.log("dict "+JSON.stringify(dict));
	console.log("       ----");
	displayOrderItemList(item);
}


//UI DISPLAY METHODS

function displayOrderItemList(data){
	var $tbody = $('#orderitem-table').find('tbody');
	$tbody.empty();
	var amt=0;
	for(var i in data){
		var e = data[i];
		var number=dict[e.barcode];
		amt+=(e.mrp*e.quantity)
		// console.log("list "+number);
		var buttonHtml = '<button onclick="deleteOrderItem('+number+')">delete</button>'
		buttonHtml += ' <button onclick="displayEditOrderItem('+number+')">edit</button>'
		var row = '<tr>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + (e.mrp*e.quantity) + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	var row = '<tr>'
		+ '<td></td>'
		+ '<td></td>'
		+ '<td></td>'
		+ '<td>Total Amount : </td>'
		+ '<td> ' + amt + ' </td>'
		+ '<td></td>'
		+ '</tr>';
	$tbody.append(row);
}

function displayEditOrderItem(n){
	
	displayOrderItem(item[n]);
}



function displayOrderItem(data){
	$("#orderitem-edit-form input[name=quantity]").val(data.quantity);	
	$("#orderitem-edit-form input[name=barcode]").val(data.barcode);	
	// $("#orderitem-edit-form input[name=id]").val(data.id);	
	$('#edit-orderitem-modal').modal('toggle');
}

function cancelOrder(event){
	item=[];
	dict={};
	count=0;
	displayOrderItemList(item);
}


function submitOrder(event){
	var url = getOrderItemUrl();
	console.log("inside suborderitem");
	console.log(JSON.stringify(item));
	var temp={};
	temp["rety"]=item;
	console.log(JSON.stringify(temp));
	item=JSON.stringify(item);
    
	$.ajax({
		url: url,
		type: 'POST',
		data: item,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function() {
			alert('Order placed successfully');
			$("#download-order").css("display","visible");
			
			item=[];
			dict={};
			count=0;
			displayOrderItemList(item);

		},
		error: handleAjaxError
	  });

	  return false;
	}

//INITIALIZATION CODE
function init(){

	$('#add-orderitem').click(addOrderItem);
	$('#update-orderitem').click(updateOrderItem);
	$('#refresh-data').click(getOrderItemList);
	$('#cancel-order').click(cancelOrder);
	$('#submit-order').click(submitOrder);

	// if(item.length==0)
	// {
	// 	$('#cancel-order').prop('disabled',true);
	// 	$('#submit-order').prop('disabled',true);
	// }
	// else if(item.length!=0){
	// 	$('#cancel-order').prop('disabled',false);
	// 	$('#submit-order').prop('disabled',false);
	// }

}

$(document).ready(init);
$(document).ready(getOrderItemList);

