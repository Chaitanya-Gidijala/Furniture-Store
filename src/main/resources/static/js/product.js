function toggleText() {
	var moreText = document.getElementById("moreText");
	var btnText = document.getElementById("btnText");

	if (moreText.style.display === "none") {
		moreText.style.display = "inline";
		btnText.innerHTML = "Read Less";
	} else {
		moreText.style.display = "none";
		btnText.innerHTML = "Read More";
	}
}

document.addEventListener("DOMContentLoaded", function() {
	var cardText = document.querySelector(".card-text");
	var lineHeight = parseInt(window.getComputedStyle(cardText).lineHeight);
	var maxHeight = 2 * lineHeight;

	if (cardText.offsetHeight > maxHeight) {
		var readMoreLink = document.createElement("span");
		readMoreLink.className = "read-more";
		readMoreLink.innerHTML = "Read More";
		readMoreLink.onclick = toggleText;
		cardText.parentNode.appendChild(readMoreLink);
	}
});





