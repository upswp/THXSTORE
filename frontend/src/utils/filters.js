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
function oneTrans(num) {
  return num.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',');
}
function dateTrans(dateStr) {
  const date = new Date(dateStr);
  return `${date.getFullYear()}/${date.getMonth()}/${date.getDate()} ${date.getHours()}:${date.getMinutes()}`;
}

const categoryStrConvert = {
  DEFAULT: '미지정',
  KR_FOOD: '한식',
  CN_FOOD: '중식',
  JP_FOOD: '일식',
  CHICKEN: '치킨',
  PIZZA: '피자',
  SCHOOL_FOOD: '분식',
  PIG_HOCK: '족발',
  FASTFOOD: '패스트푸드',
  STEAM_SOUP: '찜/탕',
  CAFE: '카페',
};
const categoryClassConvert = {
  DEFAULT: 'category-default',
  KR_FOOD: 'category-kr',
  CN_FOOD: 'category-cn',
  JP_FOOD: 'category-jp',
  CHICKEN: 'category-chicken',
  PIZZA: 'category-pizza',
  SCHOOL_FOOD: 'category-shcool-food',
  PIG_HOCK: 'category-pig-hock',
  FASTFOOD: 'category-fastfood',
  STEAM_SOUP: 'category-steam-soup',
  CAFE: 'category-cafe',
};
export { formatNumber, timeStrConvert, meridiemConvert, oneTrans, dateTrans, categoryStrConvert, categoryClassConvert };
