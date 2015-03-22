function html_encode(value) {
	return $("<span/>").text(value).html();
}

function trim(str) {
	return str.replace(/^\s+|\s+$/g, "");
}

function addTagRemove() {
	$('.tag a[href=#]').click(function(e) {
		e.preventDefault();
		$(this).parent().remove();
	});
}

$('#newTagBox').keypress(function(e) {
	if (e.keyCode == 13) {
		if ($(this).val() != "") {
			var htmlStr = "";
			var tagTitle = $(this).val();
			htmlStr += "<span class='tag'>";
	    	htmlStr += "<input type='hidden' name='tags' value='" + html_encode(tagTitle) + "' />";
	    	htmlStr += html_encode(tagTitle);
	    	htmlStr += "  <a href='#'>&times;</a>";
	    	htmlStr += "</span>";
			$('#tags').append(htmlStr);
			addTagRemove();
			$(this).val("");
		}
		
		e.preventDefault();
	}
});

addTagRemove();