<template>
  <div class="admin-container">
    <main-header></main-header>
    <main-aside></main-aside>
    <div class="admin-header">
      <h1>관리자 페이지</h1>
      <div class="select-container">
        <select id="" name="manager-status">
          <option value="신청목록">신청 목록</option>
          <option value="수정목록">수정 목록</option>
        </select>
      </div>
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
  .select-container {
    width: 95%;
    max-width: 1080px;
    margin: 1px auto;
    padding: 1% 2%;
    @include flexbox;
    justify-content: flex-end;
    select {
      width: 15%;
      height: 30px;
      padding: 2 4px;
      border: 0.5px solid grey;
      @include shadow4;
      @include xs-mobile {
        width: 25%;
        font-size: 0.7rem;
        height: 20px;
        padding: 1 4px;
      }
      @include mobile {
        width: 20%;
        height: 25px;
        padding: 1 4px;
      }
    }
  }
}
</style>
