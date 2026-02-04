window.onscroll = function() {scroll();};
function scroll() 
{
    var link=document.getElementsByTagName("HEADER")[0].getElementsByTagName("NAV")[0].getElementsByTagName("A");
    var x=0;
	if (document.body.scrollTop > 75 || document.documentElement.scrollTop > 75) {
		document.getElementById("header").style.height = "280px";
		document.getElementById("img").style.height = "150px";
		document.getElementById("title1").style.fontSize = "24px";
		document.getElementById("title2").style.fontSize = "18px";
		for(x=0;x<link.length;x++){
			link[x].style.fontSize="13px";
        }
	}
	else {
		document.getElementById("header").style.height = "360px";
		document.getElementById("img").style.height = "200px";
		document.getElementById("title1").style.fontSize = "32px";
		document.getElementById("title2").style.fontSize = "24px";
		for(x=0;x<link.length;x++){
			link[x].style.fontSize="18px";
        }
	}
}