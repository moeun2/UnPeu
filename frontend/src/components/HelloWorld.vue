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
                    TODAY IS .. <i>⛱</i><span> 그리움</span>
                  </p>
                  <img
                    class="profile-img"
                    src=""
                    alt="profile"
                  />
                  <div class="desc-wrap">
                    <p class="text-desc">
                      사람좋은 사람들 <br />
                      싸이월드<br />
                      ^-^
                    </p>
                    <a class="link-history" href="#">HISTORY</a>
                  </div>
                  <div class="info-wrap">
                    <a class="info-name" href="#">임한민</a>
                    <p class="text-email">gksals6464@naver.com</p>
                    <img src="" alt="b+" />
                    <img src="" alt="dotol" />
                  </div>
                </div>
                <div class="holder hd1"></div>
                <div class="holder hd2"></div>
                <div class="holder hd3"></div>
                <div class="holder hd4"></div>
              </div>
            </div>
          </div>
        </section>
        <section class="main-section">
          <div class="main-dot">
            <div class="main-paper">
              <div class="main-wrap">
                <div class="title-wrap">
                  <p class="title"><a href="#">사이좋은 사람들, 싸이월드</a></p>
                  <div class="link-wrap">
                    <a href="#"><span>일촌맺기</span></a>
                    <a href="#"><span>팬되기</span></a>
                    <p><a href="#">https://www.cyowrld.com/happy</a></p>
                  </div>
                </div>
                <div class="main">
                  <div class="post-wrap">
                    <div class="recent-post">
                      <h2>최근 게시물</h2>
                      <p class="text-video">
                        맘에드는 아이템은 소망상자에 넣어보세요
                      </p>
                      <p class="text-board">내 개성은 미니미로</p>
                      <p class="text-gallery">스킨, 메뉴효과 예약기능 좋아</p>
                      <p class="text-photo">더욱 편리해진 사용중 아이템</p>
                    </div>
                    <div class="menu-table">
                      <table>
                        <tr>
                          <td>쥬크박스<a href="#">0/12</a></td>
                          <td class="new-post">사진첩<a href="#">10/881</a></td>
                        </tr>
                        <tr>
                          <td class="new-post">방명록<a href="#">22/233</a></td>
                          <td>즐겨찾기<a href="#">0/3</a></td>
                        </tr>
                        <tr>
                          <td></td>
                          <td></td>
                        </tr>
                      </table>
                    </div>
                  </div>
                  <div class="mini-room-wrap">
                    <div class="nav-room-wrap">
                      <div class="nav-room">
                        <p>미니라이프</p>
                        <p class="checked">미니룸</p>
                        <p>미니라이프</p>
                      </div>
                      <p>싸이월드</p>
                    </div>
                    <img
                      class="img-miniroom"
                      src=""
                      alt="mini-room"
                    />
                    <div class="info-miniroom-wrap">
                      <div class="info-wrap-1">
                        <p>미니룸답글달기[15]</p>
                        <p>함께사진찍기</p>
                      </div>
                      <div class="info-wrap-2">
                        <p>미니룸수[16]</p>
                        <p>깜짝링크[0]</p>
                      </div>
                    </div>
                  </div>
                  <h2>일촌평</h2>
                  <div class="friends-say-section">
                    <label for="friends-say">friends say</label>
                    <input
                      type="text"
                      placeholder="일촌과 나누고 싶은 이야기를 남겨보세요~!"
                    />
                    <button>확인</button>
                  </div>
                  <div class="friends-say-list">
                    <p>안녕하세요? (친구 <span>노제</span>)</p>
                    <p>일촌평 입니다. (학교친구 <span>한상준</span>)</p>
                    <p>ㅎㅇㅎㅇㅎㅇㅎㅇ (동네친구 <span>우승윤</span>)</p>
                    <p>ㅋ (절교함 <span>김호준</span>)</p>
                    <p>안녕 (엄마 <span>김영화</span>)</p>
                  </div>
                </div>
              </div>
              <div class="menu-item mi-1 menu-checked">홈</div>
              <div class="menu-item mi-2">프로필</div>
              <div class="menu-item mi-3">다이어리</div>
              <div class="menu-item mi-4">쥬크박스</div>
              <div class="menu-item mi-5">사진첩</div>
              <div class="menu-item mi-6">게시판</div>
              <div class="menu-item mi-7">방명록</div>
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

    data: () => ({
      ecosystem: [
        {
          text: 'vuetify-loader',
          href: 'https://github.com/vuetifyjs/vuetify-loader',
        },
        {
          text: 'github',
          href: 'https://github.com/vuetifyjs/vuetify',
        },
        {
          text: 'awesome-vuetify',
          href: 'https://github.com/vuetifyjs/awesome-vuetify',
        },
      ],
      importantLinks: [
        {
          text: 'Documentation',
          href: 'https://vuetifyjs.com',
        },
        {
          text: 'Chat',
          href: 'https://community.vuetifyjs.com',
        },
        {
          text: 'Made with Vuetify',
          href: 'https://madewithvuejs.com/vuetify',
        },
        {
          text: 'Twitter',
          href: 'https://twitter.com/vuetifyjs',
        },
        {
          text: 'Articles',
          href: 'https://medium.com/vuetify',
        },
      ],
      whatsNext: [
        {
          text: 'Explore components',
          href: 'https://vuetifyjs.com/components/api-explorer',
        },
        {
          text: 'Select a layout',
          href: 'https://vuetifyjs.com/getting-started/pre-made-layouts',
        },
        {
          text: 'Frequently Asked Questions',
          href: 'https://vuetifyjs.com/getting-started/frequently-asked-questions',
        },
      ],
    }),
  }
</script>
<style lang="css">
@import url("@/assets/css/reset.css");
@import url("@/assets/css/style.css");
  
</style>