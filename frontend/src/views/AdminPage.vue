<template>
  <div class="admin-container">
    <main-header></main-header>
    <main-aside></main-aside>
    <div class="admin-header">
      <h1>관리자 페이지</h1>
      <select id="" name="manager-status">
        <option value="신청목록">신청 목록</option>
        <option value="수정목록">수정 목록</option>
      </select>
    </div>
    <manager-list></manager-list>
    <br />
    <br />
  </div>
</template>

<script>
import MainHeader from '@/components/common/MainHeader';
import MainAside from '@/components/common/MainAside';
import ManagerList from '@/components/admin/ManagerList.vue';
export default {
  components: {
    MainHeader,
    MainAside,
    ManagerList,
  },
};
</script>

<style lang="scss" scoped>
// @import '@/assets/sample2.scss';
.admin-container {
  min-height: 100vh;
  background-color: $gray100;
  .admin-header {
    width: 100%;
    padding-top: 50px;
    margin-bottom: 10px;
    text-align: end;
  }
  h1 {
    width: 100%;
    text-align: center;
    margin-top: 30px;
    @include xs-mobile {
      font-size: 1.2rem;
      margin-top: 10px;
    }
  }
  select {
    text-align: left;
    margin-right: 5%;
    @include shadow4;
    @include xs-mobile {
      font-size: 0.7rem;
    }
  }
}
</style>
