import { timeStrConvert } from '@/utils/filters';
const endTime = startTime => {
  const afterTime = new Date(startTime);
  afterTime.setHours(afterTime.getHours() + 2);
  return afterTime;
};
const countDownTimer = (end, that) => {
  let _second = 1000;
  let _minute = _second * 60;
  let _hour = _minute * 60;
  let _day = _hour * 24;
  let timer;
  const showRemaining = () => {
    let now = new Date();
    let distDt = end - now;
    if (distDt < 0) {
      clearInterval(timer);

      that.timerDone = true;
      return;
    }
    let hours = timeStrConvert(Math.floor(distDt / _hour), 1);
    let minutes = timeStrConvert(Math.floor((distDt % _hour) / _minute), 1);
    let seconds = timeStrConvert(Math.floor((distDt % _minute) / _second), 1);
    //
    that.countdown = hours + ' : ' + minutes + ' : ' + seconds;
  };
  timer = setInterval(showRemaining, 1000);
  return timer;
};
export { endTime, countDownTimer };
