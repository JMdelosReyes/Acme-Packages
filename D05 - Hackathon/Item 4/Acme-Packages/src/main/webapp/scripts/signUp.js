window.onload = function() {
	show(getRadioButtonSelectedValue(document.signUp.actorType));
};

function show(aval) {
	if (aval == "carrier") {
		hiddenDiv.style.display = 'block';
		hiddenDiv2.style.display = 'none';

		Form.fileURL.focus();
	} else if (aval == "sponsor") {
		hiddenDiv.style.display = 'none';
		hiddenDiv2.style.display = 'block';

		Form.fileURL.focus();
	} else {
		hiddenDiv.style.display = 'none';
		hiddenDiv2.style.display = 'none';

		Form.fileURL.focus();
	}
}

function validatePhone(id) {
	var re = /^[+][0-9]{3}[(][0-9]{3}[)][0-9]{4,9}$/;

	var re2 = /^[+][0-9]{3}[0-9]{4,9}$/;

	var re3 = /^[0-9]{4,9}$/;

	var phone = document.getElementById(id).value;
	if (re.test(phone)) {
		return !re.test(phone);
	} else if (re2.test(phone)) {
		return !re2.test(phone);
	} else {
		return !re3.test(phone);
	}

}

function getRadioButtonSelectedValue(ctrl) {
	for (i = 0; i < ctrl.length; i++)
		if (ctrl[i].checked)
			return ctrl[i].value;
}
