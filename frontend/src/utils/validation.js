function validateEmail(email) {
  const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(String(email).toLowerCase());
}
function validatePwd(pwd) {
  const re = /^[A-Za-z0-9+]{6,12}$/;
  return re.test(pwd);
}
function validationPhoneNumber(phoneNumber) {
  phoneNumber = phoneNumber.replace(/[^0-9]/g, '');
  if (phoneNumber.length < 3) {
    return phoneNumber;
  } else {
    if (phoneNumber.substr(0, 2) == '02') {
      if (phoneNumber.length <= 5) {
        //02-123-5678
        return phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 3);
      } else if (phoneNumber.length > 5 && phoneNumber.length <= 9) {
        //02-123-5678
        return phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 3) + '-' + phoneNumber.substr(5);
      } else if (phoneNumber.length > 9) {
        //02-1234-5678
        return phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 4) + '-' + phoneNumber.substr(6);
      }
    } else {
      if (phoneNumber.length < 8) {
        return phoneNumber;
      } else if (phoneNumber.length === 8) {
        return phoneNumber.substr(0, 4) + '-' + phoneNumber.substr(4);
      } else if (phoneNumber.length === 9) {
        return phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 3) + '-' + phoneNumber.substr(6);
      } else if (phoneNumber.length === 10) {
        return phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 3) + '-' + phoneNumber.substr(6);
      } else if (phoneNumber.length > 10) {
        //010-1234-5678
        return phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 4) + '-' + phoneNumber.substr(7, 4);
      }
    }
  }
}
function validationComResNum(comResNum) {
  comResNum = comResNum.replace(/[^0-9]/g, '');
  console.log('comResNum', comResNum);
  if (comResNum.length < 4) {
    return comResNum;
  } else {
    if (comResNum.length == 4) {
      return comResNum.substr(0, 3) + '-' + comResNum.substr(3, 4);
    } else if (comResNum.length === 5) {
      return comResNum.substr(0, 3) + '-' + comResNum.substr(3);
    } else if (comResNum.length === 6) {
      return comResNum.substr(0, 3) + '-' + comResNum.substr(3, 2) + '-' + comResNum.substr(5);
    } else if (comResNum.length >= 7) {
      return comResNum.substr(0, 3) + '-' + comResNum.substr(3, 2) + '-' + comResNum.substr(5, 5);
    }
  }
}

export { validateEmail, validatePwd, validationPhoneNumber, validationComResNum };
