<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="page">
	<div class="form">
		<div class="container">
			<table id="users-list-table-id" class="responsive-table">
				<caption>Users List:</caption>
				<thead>
					<tr>
						<th scope="row">Login</th>
						<th scope="row">Email</th>
						<th scope="row">Enabled</th>
						<th scope="row">Roles</th>
						<th scope="row">User Control</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
				</tfoot>
			</table>
		</div>
	</div>
</div>
<script>

	var token;
	var header;

	var pageSize = "${pageSize}";
	var numScrolls = 0;
	var pageScroll = true;

	var setCursorWait = function() {
		// switch to cursor wait for the current element over
		var elements = $(':hover');
		if (elements.length) {
			// get the last element which is the one on top
			elements.last().addClass('cursor-wait');
		}
		var html = $('html');
		// use .off() and a unique event name to avoid duplicates
		$('html').off('mouseover.cursorwait').on('mouseover.cursorwait', function(e) {
			// switch to cursor wait for all elements you'll be over
			$(e.target).addClass('cursor-wait');
		});
	}

	var removeCursorWait = function()	{
		$('html').off('mouseover.cursorwait'); 			// remove event handler
		$('.cursor-wait').removeClass('cursor-wait'); 	// get back to default
	}

	var addPageToTable = function(usersList) {
		for(i = 0; i < usersList.length; i++) {
			var user = usersList[i];
			var tableRow = '<tr>';
			tableRow += '<th class="width-30p width-100p" scope="row">' + user.login + '</th>';
			tableRow += '<td class="width-30p width-100p" data-title="Login">' + user.email + '</td>';
			tableRow += '<td class="width-5p width-100p"  data-title="Enabled">' + user.enabled + '</td>';
			tableRow += '<td class="width-20p width-100p"  data-title="Roles">';
			for(j = 0; j < user.userRoles.length; j++) {
				tableRow += '<div>' + user.userRoles[j] + '</div>';
			}
			tableRow += '</td>';
			tableRow += '<td class="width-15p width-100p" data-title="User Control">';
			tableRow += '<div><a href="' + '<c:url value="/users/"/>' + user.login + '/info.htm"' + 'rel="external">User Info</a></div>';
			tableRow += '<div><a href="' + '<c:url value="/users/"/>' + user.login + '/edit.htm"' + 'rel="external">Edit User Info</a></div>';
			tableRow += '<div><a href="' + '<c:url value="/users/"/>' + user.login + '/delete.htm"' + 'rel="external">Delete User</a></div>';
			tableRow += '</td>';
			tableRow += '</tr>';
			$("#users-list-table-id > tbody").append(tableRow);
		}
	}

	var loadPage = function(pageNumber) {
		setCursorWait();
		var pnJson = {"pageNumber":pageNumber, "pageSize":pageSize};
		var pnJsonStr = JSON.stringify(pnJson);
		jQuery.ajax(
			{	'type': 'POST',
				'url': '<c:url value="/users/list.json"/>',
				'contentType': 'application/json',
				'dataType': 'json',
				'data': pnJsonStr,
				'beforeSend': function(request) {
					request.setRequestHeader(header, token);
				},
				'success': function(data, textStatus, xhr) {
					if(data.length == 0) {
						pageScroll = false;
					} else {
						addPageToTable(data);
					}
					removeCursorWait();
				},
				'error': function(data, textStatus, xhr) {
					removeCursorWait();
				}
			});
	}

	$(document).ready(function() {
		token = $("meta[name='_csrf']").attr("content");
		header = $("meta[name='_csrf_header']").attr("content");
		loadPage(0);
	});

	$(window).scroll(function(){
		var docHeight = $(document).height();
		var winHeight = $(window).height();
		if  ($(window).scrollTop() == docHeight - winHeight){
			numScrolls++;
			if(pageScroll) {
				loadPage(numScrolls);
			}
		}
	});

</script>
