<template>
  <div class="map-container">
    <div id="map"></div>
  </div>
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
  data() {
    return {
      map: '',
      marker: '',
      geocoder: new kakao.maps.services.Geocoder(),
      lat: 33.450701,
      lon: 126.570667,
    };
  },
  watch: {
    location: {
      immediate: true,
      handler(newValue) {
        this.search(newValue);
      },
    },
    lat(newValue) {
      this.$emit('lat-update', newValue);
    },
    lon(newValue) {
      this.$emit('lon-update', newValue);
    },
  },
  mounted() {
    let initPosition = new kakao.maps.LatLng(33.450701, 126.570667);

    let mapContainer = document.getElementById('map');
    let mapOption = {
      center: initPosition,
      level: 4,
    };
    this.map = new kakao.maps.Map(mapContainer, mapOption);

    // 마커 생성
    this.marker = new kakao.maps.Marker({
      position: initPosition,
    });

    // 마커가 지도 위에 표시되도록 설정
    this.marker.setMap(this.map);
  },
  methods: {
    search(addr) {
      this.geocoder.addressSearch(addr, (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          this.lat = coords.Ma;
          this.lon = coords.La;
          this.marker.setMap(null);
          // 결과값으로 받은 위치를 마커로 표시합니다
          this.marker = new kakao.maps.Marker({
            map: this.map,
            position: coords,
          });

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          var infowindow = new kakao.maps.InfoWindow({
            content: `<div  style="font-size:14px;width:100%;text-align:center;padding:6px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${addr}</div>`,
          });
          infowindow.open(this.map, this.marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          this.map.setCenter(coords);
        } else {
          alert('해당 위치는 지도에 표시되지 않는 지역입니다');
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.map-container {
  @include flexbox;
  @include justify-content(center);
  width: 100%;
}
#map {
  width: 100%;
  height: 500px;
  @include mobile() {
    height: 300px;
  }
  @include xs-mobile() {
    height: 200px;
  }
}
.info-window-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
