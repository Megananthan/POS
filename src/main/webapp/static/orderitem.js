
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
	var i=dict[JSON.parse(json).barcode];
	if(i!=null)
	{
		var q=parseInt(JSON.parse(json).quantity);
		if(item[i].inventory<item[i].quantity+q)
		{
			var responseMessage="Inventory has only "+item[i].inventory+" item for product: "+item[i].name;
		    errorDisplay('danger',responseMessage);
		}
		else{
			item[i].quantity+=q;
			displayOrderItemList(item);
		}
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
			error: function(response){
				var responseMessage=JSON.parse(response.responseText).message;
				errorDisplay('danger',responseMessage);
			}
		 });
	}
	return false;
}

function updateOrderItem(event){
	$('#edit-orderitem-modal').modal('toggle');
	//Get the Barcode
	var barcode = $("#orderitem-edit-form input[name=barcode]").val();
	var quantity= $("#orderitem-edit-form input[name=quantity]").val();
	if(barcode=="")
	{
		var responseMessage="Barcode cannot be empty";
		errorDisplay('danger',responseMessage);
		return false;
	}
	if(quantity<1){
		var responseMessage="Quantity should be greater than 0";
		errorDisplay('danger',responseMessage);
		return false;
	}
	var n=dict[barcode];	
	if(item[n].inventory<quantity)
		{	
			$('#edit-orderitem-modal').modal('toggle');
			var responseMessage="Inventory has only "+item[n].inventory+" item for product: "+item[n].name;
		    errorDisplay('danger',responseMessage);
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
	displayOrderItemList(item);
}


//UI DISPLAY METHODS

function displayOrderItemList(data){
	var $tbody = $('#orderitem-table').find('tbody');
	$tbody.empty();
	var amt=0;
	var c=1;
	for(var i in data){
		var e = data[i];
		var number=dict[e.barcode];
		amt+=(e.mrp*e.quantity);
		var buttonHtml = '<button class="btn btn-primary delete_btn" onclick="deleteOrderItem('+number+')">delete</button>'
		buttonHtml += ' <button class="btn btn-primary edit_btn" onclick="displayEditOrderItem('+number+')">edit</button>'
		var row = '<tr>'
		+ '<td>' + c + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + (e.mrp*e.quantity) + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
		c++;
	}
	var row = '<tr>'
		+ '<td></td>'
		+ '<td></td>'
		+ '<td></td>'
		+ '<td></td>'
		+ '<td style="font-weight:bold;">Total Amount : </td>'
		+ '<td style="font-weight:bold;"> ' + amt + ' </td>'
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
	$('#edit-orderitem-modal').modal('toggle');
}

function cancelOrder(event){
	item=[];
	dict={};
	count=0;
	displayOrderItemList(item);
}


function submitOrder(event){
	var url = getOrderItemUrl()+'/order';	
	item=JSON.stringify(item);
    
	$.ajax({
		url: url,
		type: 'POST',
		data: item,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function() {
			var responseMessage="Order placed successfully";
		    errorDisplay('success',responseMessage);
			item=[];
			dict={};
			count=0;
			displayOrderItemList(item);

		},
		error: function(response){
			var responseMessage=JSON.parse(response.responseText).message;
			errorDisplay('danger',responseMessage);
		}
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
}

$(document).ready(init);
$(document).ready(getOrderItemList);

