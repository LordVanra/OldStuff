window.onscroll = function() {scroll();};
function scroll() 
{
    var link=document.getElementsByTagName("HEADER")[0].getElementsByTagName("NAV")[0].getElementsByTagName("A");
    var x=0;
	if (document.body.scrollTop > 40 || document.documentElement.scrollTop > 40) {
		document.getElementById("header").style.height = "275px";
		document.getElementById("img").style.height = "150px";
		document.getElementById("title").style.fontSize = "30px";
		for(x=0;x<link.length;x++){
			link[x].style.fontSize="13px";
        }
	}
	else {
		document.getElementById("header").style.height = "325px";
		document.getElementById("img").style.height = "200px";
		document.getElementById("title").style.fontSize = "40px";
		for(x=0;x<link.length;x++){
			link[x].style.fontSize="18px";
        }
	}
}