<template>
  <div id="map" style="width: 100%; height: 400px"></div>
</template>

<script>
export default {
  props: {
    location: {
      type: String,
      require: true,
      default: '',
    },
  },
  mounted() {
    let mapContainer = document.getElementById('map'),
      mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 4,
      };
    let map = new kakao.maps.Map(mapContainer, mapOption);

    // 마커 표시 위치
    var markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);

    // 마커 생성
    var marker = new kakao.maps.Marker({
      position: markerPosition,
    });

    // 마커가 지도 위에 표시되도록 설정
    marker.setMap(map);
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/sample';
</style>
