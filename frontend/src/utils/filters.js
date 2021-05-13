function formatNumber(value) {
  let res = Number(value);
  let units = ['k', 'm'];
  let iter = units[Symbol.iterator]();
  let unit = '';
  while (res > 1000) {
    res /= 1000;
    res = res.toFixed(1);
    unit = iter.next().value;
  }
  return res + unit;
}
function timeStrConvert(num, unit) {
  if (num === 0) return '00';
  const str = `${num * unit}`;
  if (str.length < 2) return '0' + str;
  else return str;
}
function meridiemConvert(value) {
  const num = parseInt(value);
  if (num === 0) return [false, '12'];
  if (num < 12) return [false, timeStrConvert(num, 1)];
  if (num === 12) return [true, '12'];
  if (num < 24) return [true, timeStrConvert(num - 12, 1)];
}

export { formatNumber, timeStrConvert, meridiemConvert };
