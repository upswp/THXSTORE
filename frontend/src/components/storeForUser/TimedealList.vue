<template>
  <div class="timedeal-container">
    <header><h3>타임딜 남은 시간 : 1시간</h3></header>
    <br />
    <br />
    <div class="timedeal-item">
      <div class="item-thumbnail">
        <img
          src="https://d20aeo683mqd6t.cloudfront.net/ko/articles/title_images/000/039/143/original/IMG_5649%E3%81%AE%E3%82%B3%E3%83%92%E3%82%9A%E3%83%BC.jpg?2019&d=750x400"
          alt=""
        />
      </div>
      <div class="item-info">
        <div class="reserved-stock">50개 남음</div>
        <div class="info-name">다꼬야끼세트</div>
        <div class="info-introduce">
          <p>
            가쓰오부시, 데리야끼 소스, 머스터드 등을 뿌려주면 더욱 짭조름하고 맛있게 즐길 수 있답니다. 한입에 쏙
            들어가는 앙증맞은 크기 속 진득한 감칠맛을 만나보세요.
          </p>
        </div>

        <div class="info-footer">
          <div class="info-timedeal-price">
            <div class="info-origin-price"><s>8000원</s></div>
            <!-- <div class="price-label">타임딜가</div> -->
            <div class="info-sale-price"><b>4,000</b>원</div>
          </div>
          <div class="order-number-container">
            <!-- <label for="order-number">수량 </label> -->
            <input id="order-number" type="number" class="order-number" placeholder="숫자 " min="1" :max="100" />
          </div>
        </div>
      </div>
    </div>
    <footer class="timedeal-footer-group">
      <div class="timedeal-sum">총합계는 요</div>
      <button class="timedeal-sum">예약번호 받기</button>
    </footer>
  </div>
</template>

<script>
export default {};
</script>

<style scoped lang="scss">
@import '@/assets/scss/sample2';
</style>
