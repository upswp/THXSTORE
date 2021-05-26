function getPhoneNumber(val) {
  let res = this.validationPhoneNumber(val);
  console.log('서버넘어가는값', res);
  this.phoneNum = res;
}

function validationPhoneNumber(phoneNumber) {
  if (!phoneNumber) return phoneNumber;
  phoneNumber = phoneNumber.replace(/[^0-9]/g, '');

  let res = '';
  if (phoneNumber.length < 3) {
    res = phoneNumber;
  } else {
    if (phoneNumber.substr(0, 2) == '02') {
      if (phoneNumber.length <= 5) {
        //02-123-5678
        res = phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 3);
      } else if (phoneNumber.length > 5 && phoneNumber.length <= 9) {
        //02-123-5678
        res = phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 3) + '-' + phoneNumber.substr(5);
      } else if (phoneNumber.length > 9) {
        //02-1234-5678
        res = phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 4) + '-' + phoneNumber.substr(6);
      }
    } else {
      if (phoneNumber.length < 8) {
        res = phoneNumber;
      } else if (phoneNumber.length == 8) {
        res = phoneNumber.substr(0, 4) + '-' + phoneNumber.substr(4);
      } else if (phoneNumber.length == 9) {
        res = phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 3) + '-' + phoneNumber.substr(6);
      } else if (phoneNumber.length == 10) {
        res = phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 3) + '-' + phoneNumber.substr(6);
      } else if (phoneNumber.length > 10) {
        //010-1234-5678
        res = phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 4) + '-' + phoneNumber.substr(7, 4);
      }
    }
  }

  return res;
}

export { getPhoneNumber, validationPhoneNumber };
