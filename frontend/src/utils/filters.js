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
export { formatNumber };
