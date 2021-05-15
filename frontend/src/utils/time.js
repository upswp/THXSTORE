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
  const time = that.$refs.time;
  const showRemaining = () => {
    let now = new Date();
    let distDt = end - now;
    if (distDt < 0) {
      clearInterval(timer);
      time.textContent = '해당 이벤트가 종료 되었습니다!';
      return;
    }
    let hours = Math.floor(distDt / _hour);
    let minutes = Math.floor((distDt % _hour) / _minute);
    let seconds = Math.floor((distDt % _minute) / _second);
    //
    time.textContent = hours + ' : ' + minutes + ' : ' + seconds;
  };
  timer = setInterval(showRemaining, 1000);
  return timer;
};
export { endTime, countDownTimer };
