<template>
  <div class="map-container">
    <div id="map"></div>
  </div>
</template>

<script>
export default {
  props: {
    location: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      storeAddr: {
        marker: '',
      },
      userAddr: {
        marker: '',
      },
      map: '',
      marker: '',
      geocoder: new kakao.maps.services.Geocoder(),
    };
  },
  watch: {
    location: {
      immediate: true,
      handler(newValue) {
        //   상세주소
        const storeAddr = newValue[0]['storeAddr'];
        const userAddr = newValue[1]['userAddr'];
        this.searchStore(storeAddr);
        this.searchUser(userAddr);
      },
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
    searchStore(addr) {
      this.geocoder.addressSearch(addr, (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          this.marker.setMap(null);
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          // 결과값으로 받은 위치를 마커로 표시합니다
          var imageSrc =
              'https://www.flaticon.com/svg/vstatic/svg/4236/4236484.svg?token=exp=1620976536~hmac=7cdf69d931e3560912455780b8172fa9',
            imageSize = new kakao.maps.Size(25, 29),
            imageOption = { offset: new kakao.maps.Point(15, 69) };
          var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

          this.storeAddr.marker = new kakao.maps.Marker({
            map: this.map,
            position: coords,
            image: markerImage,
          });

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          var infowindow = new kakao.maps.InfoWindow({
            content: `<div  style="font-size:14px;width:100%;text-align:center;padding:6px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${addr}</div>`,
          });
          infowindow.open(this.map, this.storeAddr.marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          this.map.setCenter(coords);
        } else {
          alert('해당 위치는 지도에 표시되지 않는 지역입니다');
        }
      });
    },
    searchUser(addr) {
      this.geocoder.addressSearch(addr, (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          console.log('user:', addr);
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          //   this.marker.setMap(null);
          // 결과값으로 받은 위치를 마커로 표시합니다
          this.userAddr.marker = new kakao.maps.Marker({
            map: this.map,
            position: coords,
          });

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          //   var infowindow = new kakao.maps.InfoWindow({
          //     content: `<div  style="font-size:14px;width:100%;text-align:center;padding:6px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${addr}</div>`,
          //   });
          //   infowindow.open(this.map, this.userAddr.marker);

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
