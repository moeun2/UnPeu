<template>
    <div class="view">
      <div class="background">
        <main>
          <section class="profile-section">
            <div class="profile-dot">
              <div class="profile-paper">
                <div class="profile-wrap">
                  <div class="visitor-counter">
                    <p class="text-today">103</p>
                    <p class="text-total">13042</p>
                  </div>
                  <div class="profile">
                    <p class="text-today-is">
                      BGM IS .. <span> Y - 프리스타일</span>
                    </p>
                    <img
                      class="profile-img"
                      src="https://i.imgur.com/nupfePY.png"
                      alt="profile"
                    />
                    <div class="desc-wrap">
                      <p class="text-desc">
                        선물 주는 バr람 <br />
                        착ㅎŁ バr람 <br />
                        ^-^
                      </p>
                    </div>
                    <div class="info-wrap">
                      <a class="info-name" href="#">김싸피</a>
                      <p class="text-email">ssafykim@ssafy.com</p>
                      <div style="display: flex">
                      <router-link :to="{ name: 'PresentManage' }">
                      <button class="item">
                        <img src="https://i.imgur.com/nupfePY.png" >
                        <p class="arrow_box">받고 싶은 선물 등록!</p>
                      </button>
                      </router-link>
                      <button class="item">
                        <img src="https://i.imgur.com/Fqfvown.png" >
                        <p class="arrow_box">로그인/로그아웃</p>
                      </button>
                      </div>
                    </div>
                  </div>
                  <div>
                    <div class="holder hd1"></div>
                    <div class="holder hd2"></div>
                    <div class="holder hd3"></div>
                    <div class="holder hd4"></div>
                  </div>
                </div>
              </div>
            </div>
          </section>
          <section class="main-section">
            <div class="main-dot">
              <div class="main-paper">
                <div class="main-wrap">
                  <div class="nav">
                    <ul>
                      <router-link :to="{ name: 'eventRoom' }"><li :class="[activeCheckClass]" @click="checkHome()">홈</li></router-link>
                      <router-link :to="{ name: 'Diary' }"><li :class="[activeClass]" @click="checkDiary()">다이어리</li></router-link>
                    </ul>
                  </div>
                  <div class="title-wrap">
                    <p class="title"><a href="#">오늘은 어른이날, 선물사주라주</a></p>
                  </div>
                  <div class="main">
                    <router-view />
                  </div>
                </div>
              </div>
            </div>
          </section>
        </main>
      </div>
    </div>
</template>

<script>
export default {
  name: 'App',
  data() {
      return {
          activeCheckClass: 'menu-item mi-1 menu-checked',
          activeClass: 'menu-item mi-3',
      }
  },
  components: {
  },
  methods: {
      checkHome() {
          if(this.activeClass === 'menu-item mi-1 menu-checked') {
              this.activeClass = 'menu-item mi-3';
          }
          if(this.activeCheckClass === 'menu-item mi-3') {
              this.activeCheckClass = 'menu-item mi-1 menu-checked';
          }
      },
      checkDiary() {
          if(this.activeClass === 'menu-item mi-3') {
              this.activeClass = 'menu-item mi-1 menu-checked';
          }
          if(this.activeCheckClass === 'menu-item mi-1 menu-checked') {
              this.activeCheckClass = 'menu-item mi-3';
          }
      }
    }
};
</script>

<style>
@import url("@/assets/css/reset.css");
@import url("@/assets/css/style.css");

* {
  font-family: "hiffy" !important;
}
@font-face {
  font-family: "hiffy";
  src: url("@/assets/font/hiffy.ttf") format("truetype");
  font-weight: 400;
}

.view {
  background-image: url("https://i.imgur.com/EI9xcZH.png");
  background-size: cover;
}

.v-application--wrap {
    min-height: 100%;
}
.item {
  position: relative;
  display: inline-block;
}

.arrow_box {
  display: none;
  position: absolute;
  width: 100px;
  padding: 8px;
  left: 0;
  -webkit-border-radius: 8px;
  -moz-border-radius: 8px;  
  border-radius: 8px;
  background: #85b9eaef;
  color: #fff;
  font-weight: bold;
  z-index: 1;
}

img:hover + p.arrow_box {
  display: block;
}
</style>
