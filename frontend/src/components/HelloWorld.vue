<template>
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
                      선물 주면 <br />
                      착한 사람<br />
                      ^-^
                    </p>
                  </div>
                  <div class="info-wrap">
                    <a class="info-name" href="#">김싸피</a>
                    <p class="text-email">ssafykim@ssafy.com</p>
                    <button>
                      <img src="https://i.imgur.com/nupfePY.png" 
                      @mouseover="gift_mouse" @mouseleave="gift_leave">
                    </button>
                    <div id="gift-msg"></div>
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
      <aside>
        <button class="button-activate">친구추천</button><button>팬</button>
        <div class="recommend-img-wrap">
          <img
            src=""
            alt=""
          />
          <img
            src=""
            alt=""
          />
          <img
            src=""
            alt=""
          />
          <img
            src=""
            alt=""
          />
        </div>
        <div class="aside-table">
          <table>
            <tr>
              <td>댓글 <span>0</span></td>
              <td>스크랩 <span>331</span></td>
            </tr>
            <tr>
              <td>앱스 <span>551</span></td>
              <td>소망상자 <span>10</span></td>
            </tr>
          </table>
        </div>
        <p class="recommend-item"><span>추천아이템</span>추천BGM</p>
      </aside>
    </div>
</template>

<script>
  export default {
    name: 'HelloWorld',

    // data: () => ({
    //   ecosystem: [
    //     {
    //       text: 'vuetify-loader',
    //       href: 'https://github.com/vuetifyjs/vuetify-loader',
    //     },
    //     {
    //       text: 'github',
    //       href: 'https://github.com/vuetifyjs/vuetify',
    //     },
    //     {
    //       text: 'awesome-vuetify',
    //       href: 'https://github.com/vuetifyjs/awesome-vuetify',
    //     },
    //   ],
    //   importantLinks: [
    //     {
    //       text: 'Documentation',
    //       href: 'https://vuetifyjs.com',
    //     },
    //     {
    //       text: 'Chat',
    //       href: 'https://community.vuetifyjs.com',
    //     },
    //     {
    //       text: 'Made with Vuetify',
    //       href: 'https://madewithvuejs.com/vuetify',
    //     },
    //     {
    //       text: 'Twitter',
    //       href: 'https://twitter.com/vuetifyjs',
    //     },
    //     {
    //       text: 'Articles',
    //       href: 'https://medium.com/vuetify',
    //     },
    //   ],
    //   whatsNext: [
    //     {
    //       text: 'Explore components',
    //       href: 'https://vuetifyjs.com/components/api-explorer',
    //     },
    //     {
    //       text: 'Select a layout',
    //       href: 'https://vuetifyjs.com/getting-started/pre-made-layouts',
    //     },
    //     {
    //       text: 'Frequently Asked Questions',
    //       href: 'https://vuetifyjs.com/getting-started/frequently-asked-questions',
    //     },
    //   ],
    // }),
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
  }
</script>
<style lang="css">
@import url("@/assets/css/reset.css");
@import url("@/assets/css/style.css");
  
</style>