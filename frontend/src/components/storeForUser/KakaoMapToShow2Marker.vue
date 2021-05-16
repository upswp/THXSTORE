<template>
  <div class="map-container">
    <div id="map"></div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex';
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
      imageSrc: '',
      geocoder: new kakao.maps.services.Geocoder(),
    };
  },
  computed: {
    ...mapGetters(['getWatchedStore']),
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
    // this.marker.setMap(this.map);
  },

  methods: {
    searchStore(addr) {
      this.geocoder.addressSearch(addr, (result, status) => {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          // 로고 유무에 따라 마커이미지 판별
          if (this.getWatchedStore.sideInfo.logo) {
            this.imageSrc = this.getWatchedStore.sideInfo.logo;
          } else {
            this.imageSrc = 'https://cdn.pixabay.com/photo/2016/03/31/17/53/communication-1293975_960_720.png';
          }
          var imageSrc = this.imageSrc,
            imageSize = new kakao.maps.Size(42, 49), // 마커이미지의 크기입니다
            imageOption = { offset: new kakao.maps.Point(15, 15) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
          // 마커이미지 생성
          var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
            markerPosition = coords;
          this.storeAddr.marker = new kakao.maps.Marker({
            map: this.map,
            position: markerPosition,
            image: markerImage,
          });
          // 마커이미지를 맵에 보여준다.
          this.storeAddr.marker.setMap(this.map);

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          var infowindow = new kakao.maps.InfoWindow({
            content: `<div  style="color: #0074CC; font-size:14px;width:100%;text-align:center;padding:6px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap; vertical-align: top;">${addr}</div>`,
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
          console.log('유저쿨드', coords);
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
