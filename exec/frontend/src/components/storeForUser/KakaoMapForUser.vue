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
      map: '',
      geocoder: new kakao.maps.services.Geocoder(),
    };
  },
  created() {
    this.searchAddress(this.location.store);
    this.searchAddress(this.location.user);
  },
  mounted() {
    const mapContainer = document.getElementById('map');
    const mapOption = {
      center: new kakao.maps.LatLng(33.450701, 126.570667),
      level: 8,
    };
    this.map = new kakao.maps.Map(mapContainer, mapOption);
  },

  methods: {
    searchAddress(addr) {
      this.geocoder.addressSearch(addr, (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          const markerPosition = new kakao.maps.LatLng(result[0].y, result[0].x);
          const marker = new kakao.maps.Marker({
            map: this.map,
            position: markerPosition,
          });

          // 마커이미지를 맵에 보여준다.
          marker.setMap(this.map);

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          var infowindow = new kakao.maps.InfoWindow({
            content: `<div style="color: #0074CC; font-size:14px;width:100%;text-align:center;padding:6px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap; vertical-align: top;">${addr}</div>`,
          });
          infowindow.open(this.map, marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          this.map.setCenter(markerPosition);
        } else {
          alert('해당 위치는 지도에 표시되지 않는 지역입니다');
        }
      });
    },
    searchUser(addr) {
      this.geocoder.addressSearch(addr, (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          //   this.marker.setMap(null);
          // 결과값으로 받은 위치를 마커로 표시합니다
          // this.userAddr.marker = new kakao.maps.Marker({
          //   map: this.map,
          //   position: coords,
          // });

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          // var infowindow = new kakao.maps.InfoWindow({
          //   content: `<div  style="font-size:14px;width:100%;text-align:center;padding:6px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${addr}</div>`,
          // });
          // infowindow.open(this.map, this.userAddr.marker);

          var content =
            '<div class ="label"><span class="left"></span><span class="center">현위치</span><span class="right"></span></div>';
          var customOverlay = new kakao.maps.CustomOverlay({
            position: coords,
            content: content,
          });
          customOverlay.setMap(this.map);
          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          // this.map.setCenter(coords);
        } else {
          alert('해당 위치는 지도에 표시되지 않는 지역입니다');
        }
      });
    },
  },
};
</script>

<style lang="scss">
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
.label {
  margin-bottom: 96px;
  color: $blue600;
}
.label * {
  display: inline-block;
  vertical-align: top;
}
.label .left {
  background: url('https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_l.png') no-repeat;
  display: inline-block;
  height: 24px;
  overflow: hidden;
  vertical-align: top;
  width: 7px;
}
.label .center {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_bg.png) repeat-x;
  display: inline-block;
  height: 24px;
  font-size: 12px;
  line-height: 24px;
}
.label .right {
  background: url('https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_r.png') -1px 0 no-repeat;
  display: inline-block;
  height: 24px;
  overflow: hidden;
  width: 6px;
}
</style>
