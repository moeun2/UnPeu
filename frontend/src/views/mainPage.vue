<template>
  <v-app>
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
                      <button>
                        <img src="https://i.imgur.com/nupfePY.png" 
                        @mouseover="gift_mouse" @mouseleave="gift_leave">
                      </button>
                      <div id="gift-msg"></div>
                      <button>
                        <img src="https://i.imgur.com/Fqfvown.png" 
                        @mouseover="gift_mouse" @mouseleave="gift_leave">
                      </button>
                      <div id="gift-msg"></div>
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
                      <li class="menu-item mi-1 menu-checked">홈</li>
                      <li class="menu-item mi-3">다이어리</li>
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
  </v-app>
</template>

<script>
export default {
  name: 'App',

  components: {
  },
  methods: {
      gift_mouse() {
        document.getElementById("gift-msg").innerHTML =
        '<div class="gift-msg"> 받고 싶은 선물 등록!</div>' 
      },
      gift_leave() {
        document.getElementById("gift-msg").innerHTML =
        '' 
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

body {
  background-image: url("https://i.imgur.com/EI9xcZH.png");
  background-size: cover;
}
</style>
